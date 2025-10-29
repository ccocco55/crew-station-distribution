package com.example.crewstation.mapper.like;

import com.example.crewstation.domain.like.LikeVO;
import com.example.crewstation.dto.diary.DiaryDTO;
import com.example.crewstation.dto.like.LikeDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface LikeMapper {
// 좋아요
    public void insertLike(LikeDTO likeDTO);
// 좋아요 취소
    public void deleteLike(Long id);

//  좋아요 체크
    public Long existsLikeByPostIdAndMemberId(DiaryDTO diaryDTO);

}
