package com.example.crewstation.file.daoTests;

import com.example.crewstation.common.enumeration.Type;
import com.example.crewstation.domain.file.section.FilePostSectionVO;
import com.example.crewstation.dto.file.section.FilePostSectionDTO;
import com.example.crewstation.repository.file.section.FilePostSectionDAO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@SpringBootTest
public class FilePostSectionDAOTests {
    @Autowired
    private FilePostSectionDAO filePostSectionDAO;

    @Test
    @Transactional
    public void saveFileTest() {
        FilePostSectionVO filePostSectionVO = FilePostSectionVO.builder()
                .fileId(20L)
                .postSectionId(20L)
                .imageType(Type.MAIN)
                .build();
        filePostSectionDAO.save(filePostSectionVO);
    }

    @Test
    @Transactional
    public void testDelete() {
        filePostSectionDAO.delete(32L);
    }


    @Test
    @Transactional
    public void testSelectPostSectionFileIdBySectionId() {
        Optional<FilePostSectionDTO> postSectionFileIdBySectionId = filePostSectionDAO.findPostSectionFileIdBySectionId(32L);
        assertThat(postSectionFileIdBySectionId.isPresent()).isTrue();
        log.info("{}", postSectionFileIdBySectionId.get());
    }

    @Test
    @Transactional
    public void testUpdateImageType(){
        FilePostSectionVO filePostSectionVO = FilePostSectionVO.builder()
                .postSectionId(38L)
                .imageType(Type.MAIN)
                .build();
        filePostSectionDAO.updateImageType(38L,Type.MAIN);
    }
}
