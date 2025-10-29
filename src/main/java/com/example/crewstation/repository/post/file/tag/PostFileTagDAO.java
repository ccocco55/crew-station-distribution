package com.example.crewstation.repository.post.file.tag;

import com.example.crewstation.domain.post.file.tag.PostFileTagVO;
import com.example.crewstation.dto.post.file.tag.PostFileTagDTO;
import com.example.crewstation.mapper.post.file.tag.PostFileTagMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;

@RequiredArgsConstructor
@Repository
@Slf4j
public class PostFileTagDAO {
    private final PostFileTagMapper postFileTagMapper;

//    태그 저장
    public void save(PostFileTagVO postFileTagVO) {
        postFileTagMapper.insert(postFileTagVO);
    }


    //    태그 불러오기
    public List<PostFileTagDTO> findByFileId(Long fileId){
        return postFileTagMapper.selectByFileId(fileId);
    }

    public void deleteAllByFileId(Long fileId){
        postFileTagMapper.deleteAllByFileId(fileId);
    }

    public void deleteById(Long id){
        postFileTagMapper.deleteById(id);
    }
}
