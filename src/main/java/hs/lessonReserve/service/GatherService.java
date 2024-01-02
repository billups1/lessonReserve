package hs.lessonReserve.service;

import hs.lessonReserve.domain.gather.GatherRepository;
import hs.lessonReserve.domain.gather.GatherRepositoryImpl;
import hs.lessonReserve.web.dto.gather.GatherListDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GatherService {

    private final GatherRepositoryImpl gatherRepository;

    public List<GatherListDto> gatherList() {
        List<GatherListDto> gatherListDtos = gatherRepository.gatherListDtoList();
        return gatherListDtos;
    }
}
