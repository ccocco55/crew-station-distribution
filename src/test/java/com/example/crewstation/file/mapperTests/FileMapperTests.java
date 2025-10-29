package com.example.crewstation.file.mapperTests;

import com.example.crewstation.domain.file.FileVO;
import com.example.crewstation.dto.file.FileDTO;
import com.example.crewstation.mapper.file.FileMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Slf4j
public class FileMapperTests {
    @Autowired
    private FileMapper fileMapper;

    @Test
    public void testInsertFile() {
        FileVO fileVO = FileVO.builder()
                .fileSize("12MB")
                .filePath("../../test")
                .fileOriginName("test")
                .fileName("test")
                .build();
        fileMapper.insertFile(fileVO);
    }

    @Test
    @Transactional
    public void testInsert(){
        FileDTO fileDTO = new FileDTO();
        fileDTO.setFileName("test");
        fileDTO.setFileSize("12MB");
        fileDTO.setFileOriginName("test");
        fileDTO.setFilePath("../../test");

        fileMapper.insert(fileDTO);
        log.info("fileDTO {}",fileDTO.getId());
    }

    @Test
    @Transactional
    public void testDelete(){
        fileMapper.delete(39L);
    }
}
