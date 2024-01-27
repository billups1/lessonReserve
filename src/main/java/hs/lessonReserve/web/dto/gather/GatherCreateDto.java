package hs.lessonReserve.web.dto.gather;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class GatherCreateDto {

    private String name;
    private String content;
    private MultipartFile representativeImageFile;
    private String sidoSelect;
    private String SigunGuSelect;
    private String eupMeonDongSelect;
    private int maximumParticipantNumber;
    private String flexRadioDefault;

}
