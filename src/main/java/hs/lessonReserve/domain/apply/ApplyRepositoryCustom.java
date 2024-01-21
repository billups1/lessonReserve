package hs.lessonReserve.domain.apply;

import hs.lessonReserve.web.dto.admin.AdminApplyDto;
import hs.lessonReserve.web.dto.admin.AdminApplySearchCondDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ApplyRepositoryCustom {
    Page<AdminApplyDto> adminApplyDtos(Pageable pageable, AdminApplySearchCondDto adminApplySearchCondDto);
}
