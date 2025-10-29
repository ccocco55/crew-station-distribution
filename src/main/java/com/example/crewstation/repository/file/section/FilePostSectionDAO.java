package com.example.crewstation.repository.file.section;

import com.example.crewstation.common.enumeration.Type;
import com.example.crewstation.domain.file.section.FilePostSectionVO;
import com.example.crewstation.dto.file.section.FilePostSectionDTO;
import com.example.crewstation.mapper.file.post.section.FilePostSectionMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@RequiredArgsConstructor
@Repository
@Slf4j
public class FilePostSectionDAO {
    private final FilePostSectionMapper filePostSectionMapper;

//    값 추가하기
    public void save(FilePostSectionVO filePostSectionVO){
        filePostSectionMapper.insert(filePostSectionVO);
    }

    //   hard delete
    public void delete(Long sectionId){
        filePostSectionMapper.delete(sectionId);
    }

    //  섹션 아이디로 파일 아이디 찾기
    public Optional<FilePostSectionDTO> findPostSectionFileIdBySectionId(Long sectionId){
        return filePostSectionMapper.selectPostSectionFileIdBySectionId(sectionId);
    }
    //  이미지 썸네일 변경해주기
    public void updateImageType(Long postSectionId, Type imageType){
        filePostSectionMapper.updateImageType(postSectionId,imageType);
    }

    public void deleteByFileId(Long fileId){
        filePostSectionMapper.deleteByFileId(fileId);
    }

    public void updateImageTypeByFileId(Long fileId, Type imageType){
        filePostSectionMapper.updateImageTypeByFileId(fileId,imageType);
    }
}
