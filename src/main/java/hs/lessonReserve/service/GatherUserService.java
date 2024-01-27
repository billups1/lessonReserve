package hs.lessonReserve.service;

import hs.lessonReserve.config.auth.PrincipalDetails;
import hs.lessonReserve.domain.gather.gatherUser.GatherUser;
import hs.lessonReserve.domain.gather.gatherUser.GatherUserRepository;
import hs.lessonReserve.handler.ex.CustomApiException;
import hs.lessonReserve.handler.ex.CustomException;
import hs.lessonReserve.web.dto.admin.AdminGatherUserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GatherUserService {

    private final GatherUserRepository gatherUserRepository;

    public Page<AdminGatherUserDto> adminGatherUserDtosByGatherId(Long gatherId, Pageable pageable) {
        Page<GatherUser> gatherUsers = gatherUserRepository.findAllByGatherIdOrderByIdDesc(gatherId, pageable);
        Page<AdminGatherUserDto> adminGatherUserDtos = gatherUsers.map((gatherUser) -> {
            return new AdminGatherUserDto(gatherUser);
        });
        return adminGatherUserDtos;
    }

    public void gatherUserWithdraw(long gatherUserId, PrincipalDetails principalDetails) {

        GatherUser gatherUser = gatherUserRepository.findById(gatherUserId).orElseThrow(() -> {
            throw new CustomApiException("없는 GatherUser입니다.");
        });

        GatherUser gatherUserLeader = gatherUser.getGather().getGatherUsers().stream()
                .filter(gu -> gu.getPosition().equals("LEADER")).findFirst().orElseThrow(() -> {
            throw new CustomApiException("LEADER를 찾을 수 없습니다.");
        });

        if (!principalDetails.getUser().getRole().equals("ROLE_ADMIN") || principalDetails.getUser().getId() != gatherUserLeader.getUser().getId()) {
            throw new CustomApiException("모임을 삭제할 권한이 없습니다.");
        }

        gatherUserRepository.delete(gatherUser);

    }
}
