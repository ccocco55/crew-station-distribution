package com.example.crewstation.repository.like;

import com.example.crewstation.domain.like.LikeVO;
import com.example.crewstation.dto.diary.DiaryDTO;
import com.example.crewstation.dto.like.LikeDTO;
import com.example.crewstation.mapper.like.LikeMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class LikeDAO {
    private final LikeMapper likeMapper;

    // 좋아요
    public void saveLike(LikeDTO likeDTO) {
        likeMapper.insertLike(likeDTO);
    }

    // 좋아요 취소
    public void deleteLike(Long id) {
        likeMapper.deleteLike(id);
    }

    //  좋아요 체크
    public Long isLikeByPostIdAndMemberId(DiaryDTO diaryDTO) {
        return likeMapper.existsLikeByPostIdAndMemberId(diaryDTO);
    }
}
