package com.example.crewstation.service.reply;


import com.example.crewstation.auth.CustomUserDetails;
import com.example.crewstation.domain.reply.ReplyVO;
import com.example.crewstation.dto.reply.ReplyCriteriaDTO;
import com.example.crewstation.dto.reply.ReplyDTO;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface ReplyService {
    public ReplyCriteriaDTO getReplies(int page,
                                       Long postId,
                                       CustomUserDetails customUserDetails);

    public void write(ReplyDTO replyDTO, CustomUserDetails customUserDetails);

    public void upate(Long replyId, ReplyDTO replyDTO);

    public void delete(Long replyId, Long postId);

    default ReplyVO toReplyVO(ReplyDTO replyDTO){
        return ReplyVO.builder()
                .id(replyDTO.getId())
                .createdDatetime(replyDTO.getCreatedDatetime())
                .updatedDatetime(replyDTO.getUpdatedDatetime())
                .memberId(replyDTO.getMemberId())
                .diaryId(replyDTO.getPostId())
                .replyContent(replyDTO.getReplyContent())
                .build();
    }
}
