package hs.lessonReserve.domain.apply;

import hs.lessonReserve.web.dto.admin.AdminApplyDto;
import hs.lessonReserve.web.dto.admin.AdminSearchCondDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ApplyRepositoryCustom {
    Page<AdminApplyDto> adminApplyDtos(Pageable pageable, AdminSearchCondDto adminSearchCondDto);
}
