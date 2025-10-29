package com.example.crewstation.mapper.file.post.section;

import com.example.crewstation.common.enumeration.Type;
import com.example.crewstation.domain.file.section.FilePostSectionVO;
import com.example.crewstation.dto.file.section.FilePostSectionDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.Optional;

@Mapper
public interface FilePostSectionMapper {
//   값 추가하기
    public void insert(FilePostSectionVO sectionFileVO);

//   hard delete
    public void delete(Long sectionId);

//  섹션 아이디로 파일 아이디 찾기
    public Optional<FilePostSectionDTO> selectPostSectionFileIdBySectionId(Long sectionId);

//  이미지 썸네일 변경해주기
    public void updateImageType(Long postSectionId, Type imageType);

    public void updateImageTypeByFileId(Long fileId, Type imageType);

    public void deleteByFileId(Long fileId);
}
