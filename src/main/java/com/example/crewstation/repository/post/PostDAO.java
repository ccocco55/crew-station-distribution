package com.example.crewstation.repository.post;

import com.example.crewstation.domain.post.PostVO;
import com.example.crewstation.dto.post.PostDTO;
import com.example.crewstation.dto.purchase.PurchaseDTO;
import com.example.crewstation.mapper.post.PostMapper;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PostDAO {
    private final PostMapper postMapper;

    //    게시글 신고 추가해주기
    public void savePostReport(Long reportId, Long postId) {
        postMapper.insertPostReport(reportId, postId);
    }

    //    게시글 존재 여부 확인
    public boolean isActivePost(Long postId) {
        return postMapper.existsActivePost(postId);

    }

    //    게시글 작성
    public void savePost(Object object) {
        postMapper.insert(object);
    }

    //    게시글 수정
    public void update(PostVO postVO) {
        postMapper.update(postVO);
    }
    //   게시글 삭제 소프트 딜리트
    public void updatePostStatus(Long id){
        postMapper.updatePostStatus(id);
    }

    //    게시글 조회
    public PostDTO findPostMainImage(Long postId) {
        return postMapper.selectPostMainImageById(postId);
    }

    //   게시글 조회수
    public void updateReadCount(Long postId){
        postMapper.updateReadCount(postId);
    }
}
