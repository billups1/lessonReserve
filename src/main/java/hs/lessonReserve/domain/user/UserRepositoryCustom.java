package hs.lessonReserve.domain.user;

import hs.lessonReserve.web.dto.admin.AdminGatherDto;
import hs.lessonReserve.web.dto.admin.AdminSearchCondDto;
import hs.lessonReserve.web.dto.admin.AdminUserDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserRepositoryCustom {
    Page<AdminUserDto> adminUserDto(Pageable pageable, AdminSearchCondDto adminSearchCondDto);
}
