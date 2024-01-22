package hs.lessonReserve.domain.gather;

import hs.lessonReserve.web.dto.admin.AdminGatherDto;
import hs.lessonReserve.web.dto.admin.AdminSearchCondDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface GatherRepositoryCustom {

    Page<AdminGatherDto> adminGatherDtos(Pageable pageable, AdminSearchCondDto adminSearchCondDto);

}
