package com.example.crewstation.report;

import com.example.crewstation.dto.report.ReportDTO;
import com.example.crewstation.mapper.post.PostMapper;
import com.example.crewstation.mapper.report.ReportMapper;
import lombok.extern.slf4j.Slf4j;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
public class MapperTest {
    @Autowired
    private ReportMapper reportMapper;

    @Test
    public void testInsertReport() {
        ReportDTO reportDTO = new ReportDTO();
        reportDTO.setReportContent("불법 물품 같아요");
        reportDTO.setMemberId(1L);
        reportMapper.insertReport(reportDTO);
        assertNotNull(reportDTO.getId());
        log.info(reportDTO.toString());
    }
}
