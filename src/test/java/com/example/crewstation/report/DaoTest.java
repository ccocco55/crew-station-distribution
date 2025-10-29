package com.example.crewstation.report;

import com.example.crewstation.dto.report.ReportDTO;
import com.example.crewstation.repository.purchase.PurchaseDAO;
import com.example.crewstation.repository.report.ReportDAO;
import com.example.crewstation.util.Criteria;
import com.example.crewstation.util.Search;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
public class DaoTest {
   @Autowired
    private ReportDAO reportDAO;

   @Test
    public void testSaveReport(){
       ReportDTO reportDTO = new ReportDTO();
       reportDTO.setReportContent("임시입니다.");
       reportDTO.setMemberId(2L);
       reportDAO.saveReport(reportDTO);
   }
}
