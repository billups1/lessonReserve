package hs.lessonReserve.service;

import com.nimbusds.jose.shaded.gson.Gson;
import com.nimbusds.jose.shaded.gson.JsonObject;
import hs.lessonReserve.config.auth.PrincipalDetails;
import hs.lessonReserve.constant.ApplyStatus;
import hs.lessonReserve.domain.alarm.AlarmRepository;
import hs.lessonReserve.domain.alarm.Alarm_LessonApply;
import hs.lessonReserve.domain.lesson.Lesson;
import hs.lessonReserve.domain.lesson.LessonRepository;
import hs.lessonReserve.domain.apply.Apply;
import hs.lessonReserve.domain.apply.ApplyRepository;
import hs.lessonReserve.domain.lesson.LessonRepositoryImpl;
import hs.lessonReserve.domain.payment.Payment;
import hs.lessonReserve.domain.payment.PaymentRepository;
import hs.lessonReserve.domain.user.Teacher;
import hs.lessonReserve.domain.user.User;
import hs.lessonReserve.handler.ex.CustomApiException;
import hs.lessonReserve.handler.ex.CustomException;
import hs.lessonReserve.web.dto.admin.AdminLessonDto;
import hs.lessonReserve.web.dto.admin.AdminLessonListDto;
import hs.lessonReserve.web.dto.admin.AdminLessonSearchCondDto;
import hs.lessonReserve.web.dto.lesson.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LessonService {

    private final LessonRepository lessonRepository;
    private final LessonRepositoryImpl lessonRepositoryImpl;
    private final ApplyRepository applyRepository;
    private final PaymentRepository paymentRepository;
    private final AlarmRepository alarmRepository;
    private final ApplyService applyService;

    @Value("${payment.iamport.apiKey}")
    private String apiKey;
    @Value("${payment.iamport.secretKey}")
    private String secretKey;

    @Transactional
    public void makeLesson(LessonCreateDto lessonCreateDto, PrincipalDetails principalDetails) {

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
        LocalDateTime lessonStartDate = LocalDateTime.parse(lessonCreateDto.getLessonStartDate() + " 00:00:00.000", dateTimeFormatter);
        LocalDateTime lessonEndDate = LocalDateTime.parse(lessonCreateDto.getLessonEndDate() + " 23:59:59.000", dateTimeFormatter);

        Lesson lesson = Lesson.builder()
                .name(lessonCreateDto.getLessonName())
                .content(lessonCreateDto.getLessonContent())
                .lessonTime(lessonCreateDto.getLessonTime())
                .maximumStudentsNumber(lessonCreateDto.getMaximumStudentsNumber())
                .teacher((Teacher) principalDetails.getUser())
                .price(lessonCreateDto.getPrice())
                .lessonStartDate(lessonStartDate)
                .lessonEndDate(lessonEndDate)
                .roadAddress(lessonCreateDto.getRoadAddress())
                .build();

        lessonRepository.save(lesson);
    }

    @Transactional
    public void deleteLesson(long lessonId, PrincipalDetails principalDetails) {
        List<Apply> applies = applyRepository.findAllByLessonId(lessonId);
        for (Apply apply : applies) {
            apply.setLesson(null);
        }

        long teacherId = principalDetails.getUser().getId();
        System.out.println(lessonId + "/" + teacherId);
        lessonRepository.mDeleteLesson(lessonId, teacherId);
    }

    @Transactional(readOnly = true)
    public Page<HomeLessonListDto> homeLessonList(PrincipalDetails principalDetails, LessonSearchCondDto lessonSearchCondDto, Pageable pageable) {
        List<Lesson> lessons = lessonRepositoryImpl.mHomeLessonList(lessonSearchCondDto);

        List<HomeLessonListDto> homeLessonListDtoArrayList = lessons.stream().map(l -> new HomeLessonListDto(l, principalDetails)).collect(Collectors.toList());

        PageRequest pageRequest = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), pageable.getSort());
        int start = (int) pageRequest.getOffset();
        int end = Math.min((start + pageRequest.getPageSize()), homeLessonListDtoArrayList.size());
        Page<HomeLessonListDto> homeLessonListDtos = new PageImpl<>(homeLessonListDtoArrayList.subList(start, end), pageRequest, homeLessonListDtoArrayList.size());
        return homeLessonListDtos;
    }

    @Transactional(readOnly = true)
    public List<HomeLessonListDto> teacherMyPageList(PrincipalDetails principalDetails) {

        long teacherId = principalDetails.getUser().getId();
        List<Lesson> lessons = lessonRepository.mTeacherMyPageList(teacherId);

        List<HomeLessonListDto> lessonListDto = lessons.stream().map(l -> new HomeLessonListDto(l, null)).collect(Collectors.toList());
        return lessonListDto;

    }

    @Transactional(readOnly = true)
    public ApplyLessonDto applyLessonForm(long lessonId) {

        Lesson lesson = lessonRepository.findById(lessonId).orElseThrow(() -> {
            throw new CustomException("없는 강의입니다.");
        });

        ApplyLessonDto applyLessonDto = new ApplyLessonDto(lesson);

        return applyLessonDto;

    }

    public LessonPaymentFormDto lessonPaymentFormDto(PrincipalDetails principalDetails, long lessonId) {
        User user = principalDetails.getUser();
        Lesson lesson = lessonRepository.findById(lessonId).orElseThrow(() -> {
            throw new CustomApiException("없는 레슨입니다.");
        });

        LessonPaymentFormDto lessonPaymentFormDto = new LessonPaymentFormDto(user, lesson);
        return lessonPaymentFormDto;
    }

    public LessonPaymentCancelFormDto lessonPaymentCancelFormDto(PrincipalDetails principalDetails, long paymentId) {
        Payment payment = paymentRepository.findById(paymentId).orElseThrow(() -> {
            throw new CustomException("없는 Payment 입니다.");
        });
        User user = principalDetails.getUser();

        LessonPaymentCancelFormDto lessonPaymentCancelFormDto = new LessonPaymentCancelFormDto(user, payment);
        return lessonPaymentCancelFormDto;
    }

    @Transactional
    public long paymentValidateAndSave(PrincipalDetails principalDetails, String impUid, String merchantUid, int totalPrice, long lessonId, String pay_method, String pg_provider,
                                       boolean lessonPolicyAgree, boolean pgPolicyAgree) {

        if (lessonPolicyAgree == false || pgPolicyAgree == false) {
            throw new CustomApiException("약관 동의가 필요합니다.");
        }

        Lesson lesson = lessonRepository.findById(lessonId).orElseThrow(() -> {
            throw new CustomApiException("없는 레슨입니다.");
        });

        int serverPrice = lesson.getPrice();
        if (serverPrice != totalPrice) {
            System.out.println("서버 결제 가격과 js 결제 가격이 다름 impUid : " + impUid);
            throw new CustomApiException("결제에 실패했습니다.");
        }

        Payment payment = Payment.builder()
                .lesson(lesson)
                .user(principalDetails.getUser())
                .paymentGateway(pg_provider)
                .paymentMethod(pay_method)
                .impUid(impUid) // 포트원 고유번호
                .merchantUid(merchantUid)
                .lessonPolicyAgree(lessonPolicyAgree)
                .pgPolicyAgree(pgPolicyAgree)
                .build();

        paymentRepository.save(payment);

        Apply apply = Apply.builder()
                .lesson(lesson)
                .student(principalDetails.getUser())
                .applyStatus(ApplyStatus.APPLY)
                .payment(payment)
                .build();

        applyRepository.save(apply);

        Alarm_LessonApply alarm = Alarm_LessonApply.builder()
                .domain("LessonApply")
                .fromUser(principalDetails.getUser())
                .toUser(lesson.getTeacher())
                .lesson(lesson)
                .build();

        alarmRepository.save(alarm);

        return payment.getId();
    }

    @Transactional
    public Payment lessonCancel(PrincipalDetails principalDetails, long paymentId) {

        Payment payment = paymentRepository.findById(paymentId).orElseThrow(() -> {
            throw new CustomApiException("없는 payment 입니다.");
        });

        if (LocalDateTime.now().isAfter(payment.getLesson().getLessonStartDate())) {
            throw new CustomApiException("레슨 시작일이 이미 지나 레슨을 취소할 수 없습니다.");
        }

        String iamportToken;
        try {
            iamportToken = getIamportToken();
        } catch (IOException e) {
            throw new CustomException("토큰 불러오기 실패", e);
        }

        try {
            URL url = new URL("https://api.iamport.kr/payments/cancel");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("POST");

            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Authorization", "Bearer " + iamportToken);

            conn.setDoOutput(true);

            JsonObject json = new JsonObject();
            json.addProperty("imp_uid", payment.getImpUid());

            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
            bw.write(json.toString());
            bw.flush();
            bw.close();

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            Gson gson = new Gson();
            Map responseMap = gson.fromJson(br.readLine(), Map.class);
            Map response = (Map) responseMap.get("response");
            br.close();

            conn.disconnect();
            System.out.println("레슨 결제 취소 완료, 주문번호: " + response.get("merchant_uid"));
        } catch (IOException e) {
            throw new CustomApiException("레슨 결제 취소에 실패하였습니다. 잠시 후 다시 시도해 주세요");
        }

        payment.setStatus("CANCEL");
        payment.setCancelTime(LocalDateTime.now());
        Apply apply = payment.getApply();
        apply.setApplyStatus(ApplyStatus.CANCEL);

        Alarm_LessonApply alarm_lessonPaymentCancel = Alarm_LessonApply.builder()
                .fromUser(payment.getUser())
                .toUser(payment.getLesson().getTeacher())
                .domain("LessonPaymentCancel")
                .lesson(payment.getLesson())
                .build();

        alarmRepository.save(alarm_lessonPaymentCancel);

        applyService.cancelApply(payment.getLesson().getId(), principalDetails);

        return payment;

    }

    public String getIamportToken() throws IOException {

        URL url = new URL("https://api.iamport.kr/users/getToken");
        HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();

        conn.setRequestMethod("POST"); // 대문자로

        conn.setRequestProperty("Content-Type", "application/json"); // Request 데이터 형식
        conn.setRequestProperty("Accept", "application/json"); // Response 데이터 형식

        conn.setDoOutput(true);

        JsonObject json = new JsonObject();
        json.addProperty("imp_key", apiKey);
        json.addProperty("imp_secret", secretKey);

        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
        bw.write(json.toString());
        bw.flush(); // bw 비우기
        bw.close();

        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        Gson gson = new Gson();
        String response = gson.fromJson(br.readLine(), Map.class).get("response").toString();
        String accessToken = gson.fromJson(response, Map.class).get("access_token").toString();
        br.close();

        conn.disconnect();

        System.out.printf("iamport 엑세스토큰 발급 성공" + accessToken);
        return accessToken;
    }

    @Transactional(readOnly = true)
    public Page<AdminLessonListDto> adminLessonListDto(Pageable pageable, AdminLessonSearchCondDto adminLessonSearchCondDto) {
//        Page<Lesson> lessons = lessonRepository.findAllByOrderByIdDesc(pageable);
//        Page<AdminLessonListDto> adminLessonListDtos = lessons.map(lesson -> {
//            return new AdminLessonListDto(lesson);
//        });

        Page<AdminLessonListDto> adminLessonListDtos = lessonRepositoryImpl.mAdminLessonListDto(adminLessonSearchCondDto, pageable);

        return adminLessonListDtos;
    }

    @Transactional(readOnly = true)
    public AdminLessonDto adminLessonDtosByLessonId(long lessonId) {
        Lesson lesson = lessonRepository.findById(lessonId).orElseThrow(() -> {
            throw new CustomException("없는 레슨입니다.");
        });
        AdminLessonDto adminLessonDto = new AdminLessonDto(lesson);
        return adminLessonDto;
    }


    public Page<AdminLessonListDto> adminLessonDtosByTeacherId(Long teacherId, Pageable pageable) {
        Page<Lesson> lessons = lessonRepository.findAllByTeacherIdOrderByIdDesc(teacherId, pageable);
        Page<AdminLessonListDto> adminLessonListDtos = lessons.map(lesson -> {
            return new AdminLessonListDto(lesson);
        });
        return adminLessonListDtos;
    }

    public Page<AdminLessonListDto> adminLessonDtosByStudentId(Long studentId, Pageable pageable) {
        Page<Lesson> lessons = lessonRepository.findAllByStudentIdOrderByIdDesc(studentId, pageable);
        Page<AdminLessonListDto> adminLessonListDtos = lessons.map(lesson -> {
            return new AdminLessonListDto(lesson);
        });
        return adminLessonListDtos;
    }

}
