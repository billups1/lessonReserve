package hs.lessonReserve.service;

import hs.lessonReserve.config.auth.PrincipalDetails;
import hs.lessonReserve.domain.gather.gatherUser.GatherUser;
import hs.lessonReserve.domain.gather.gatherUser.GatherUserRepository;
import hs.lessonReserve.handler.ex.CustomException;
import hs.lessonReserve.web.dto.admin.AdminGatherUserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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
            throw new CustomException("없는 모임신청입니다.");
        });

        gatherUser.getGather().getGatherUsers().

        String role = principalDetails.getUser().getRole();
        if (!role.equals("ROLE_ADMIN") || )


    }
}
