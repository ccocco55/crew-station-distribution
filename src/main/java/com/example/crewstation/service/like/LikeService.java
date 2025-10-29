package com.example.crewstation.service.like;

import com.example.crewstation.auth.CustomUserDetails;
import com.example.crewstation.domain.like.LikeVO;
import com.example.crewstation.dto.like.LikeDTO;

public interface LikeService {

    public void like(Long postId, CustomUserDetails customUserDetails);

    public void unlike(Long postId,CustomUserDetails customUserDetails);

    default LikeVO toVO(LikeDTO dto) {
        return LikeVO.builder()
                .id(dto.getId())
                .memberId(dto.getMemberId())
                .postId(dto.getPostId())
                .build();
    }
}
