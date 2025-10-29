package com.example.crewstation.file.mapperTests;

import com.example.crewstation.common.enumeration.Type;
import com.example.crewstation.domain.file.section.FilePostSectionVO;
import com.example.crewstation.dto.file.section.FilePostSectionDTO;
import com.example.crewstation.mapper.file.post.section.FilePostSectionMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;
import java.util.Optional;

@SpringBootTest
@Slf4j
public class FilePostSectionMapperTests {
    @Autowired
    private FilePostSectionMapper filePostSectionMapper;


    @Test
    @Transactional
    public void testSaveFile() {
        FilePostSectionVO filePostSectionVO = FilePostSectionVO.builder()
                .fileId(20L)
                .postSectionId(20L)
                .imageType(Type.MAIN)
                .build();
        filePostSectionMapper.insert(filePostSectionVO);
    }


    @Test
    @Transactional
    public void testDelete() {
        filePostSectionMapper.delete(32L);
    }


    @Test
    @Transactional
    public void testSelectPostSectionFileIdBySectionId() {
        Optional<FilePostSectionDTO> postSectionFileDTO = filePostSectionMapper.selectPostSectionFileIdBySectionId(24L);
        assertThat(postSectionFileDTO.isPresent()).isTrue();
        log.info("{}", postSectionFileDTO.get());
    }

    @Test
    @Transactional
    public void testUpdateImageType(){
        FilePostSectionVO filePostSectionVO = FilePostSectionVO.builder()
                .postSectionId(38L)
                .imageType(Type.MAIN)
                .build();
        filePostSectionMapper.updateImageType(38L,Type.MAIN);
    }
}

