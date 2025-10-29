package com.example.crewstation.file.daoTests;

import com.example.crewstation.domain.file.FileVO;
import com.example.crewstation.dto.file.FileDTO;
import com.example.crewstation.mapper.file.FileMapper;
import com.example.crewstation.repository.file.FileDAO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Slf4j
public class FileDAOTests {
    @Autowired
    private FileDAO fileDAO;

    @Test
    @Transactional
    public void testInsertFile() {
        FileVO fileVO = FileVO.builder()
                .fileSize("12MB")
                .filePath("../../test")
                .fileOriginName("test")
                .fileName("test")
                .build();
        fileDAO.save(fileVO);
    }

    @Test
    @Transactional
    public void testSaveFile(){
        FileDTO fileDTO = new FileDTO();
        fileDTO.setFileName("test");
        fileDTO.setFileSize("12MB");
        fileDTO.setFileOriginName("test");
        fileDTO.setFilePath("../../test");

        fileDAO.saveFile(fileDTO);
        log.info("fileDTO {}",fileDTO.getId());
    }

    @Test
    @Transactional
    public void testDelete(){
        fileDAO.delete(39L);
    }
}
