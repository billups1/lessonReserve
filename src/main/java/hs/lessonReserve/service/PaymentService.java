package hs.lessonReserve.service;

import hs.lessonReserve.config.auth.PrincipalDetails;
import hs.lessonReserve.domain.payment.Payment;
import hs.lessonReserve.domain.payment.PaymentRepository;
import hs.lessonReserve.handler.ex.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;

    @Transactional(readOnly = true)
    public String paymentName(PrincipalDetails principalDetails, long paymentId) {
        Payment payment = paymentRepository.findById(paymentId).orElseThrow(() -> {
            throw new CustomException("해당 결제 데이터가 없습니다.");
        });

        if (payment.getUser().getId() != principalDetails.getUser().getId()) {
            throw new CustomException("잘못된 접근입니다.");
        }

        return payment.getMerchantUid();
    }

    @Transactional(readOnly = true)
    public void paymentUserValidate(PrincipalDetails principalDetails, long paymentId) {
        Payment payment = paymentRepository.findById(paymentId).orElseThrow(() -> {
            throw new CustomException("없는 유저입니다.");
        });

        if (principalDetails.getUser().getId() != payment.getUser().getId()) {
            throw new CustomException("잘못된 접근입니다.");
        }
    }
}
