package com.example.crewstation.mapper.post.file.tag;

import com.example.crewstation.domain.post.file.tag.PostFileTagVO;
import com.example.crewstation.dto.post.file.tag.PostFileTagDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PostFileTagMapper {

//    태그 저장
    public void insert(PostFileTagVO postFileTagVO);

//    태그 불러오기
    public List<PostFileTagDTO> selectByFileId(Long fileId);

//    태그 전체 삭제
    public void deleteAllByFileId(Long fileId);
//    태그 개별 삭제
    public void deleteById(Long id);
}
