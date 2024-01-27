package hs.lessonReserve.util;

import hs.lessonReserve.web.dto.ex.CMRespDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class AddressCmbBox {

    private final JdbcTemplate jdbcTemplate;

    @GetMapping("/api/address/SigunGu")
    public ResponseEntity SigunGu(String sido) {
        String sql = "select distinct SigunGu from addresslist where Sido = ? and SigunGu != ''";

        List<String> SigunGuList = jdbcTemplate.query(sql,
                new RowMapper<String>() {
                    @Override
                    public String mapRow(ResultSet rs, int rowNum) throws SQLException {
                        return rs.getString("SigunGu");
                    }
                }, sido);
        return new ResponseEntity(new CMRespDto<>(1, "시군구 리스트 불러오기 완료", SigunGuList), HttpStatus.OK);
    }

    @GetMapping("/api/address/eupMeonDong")
    public ResponseEntity eupMeonDong(String sido, String SigunGu) {
        String sql = "select distinct eupMeonDong from addresslist where Sido = ? and SigunGu = ? and eupMeonDong != ''";

        List<String> eupMeonDongList = jdbcTemplate.query(sql,
                new RowMapper<String>() {
                    @Override
                    public String mapRow(ResultSet rs, int rowNum) throws SQLException {
                        return rs.getString("eupMeonDong");
                    }
                }, sido, SigunGu);
        return new ResponseEntity(new CMRespDto<>(1, "읍면동 리스트 불러오기 완료", eupMeonDongList), HttpStatus.OK);
    }


}
