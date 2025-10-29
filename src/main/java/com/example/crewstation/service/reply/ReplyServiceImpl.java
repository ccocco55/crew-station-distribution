package com.example.crewstation.service.reply;

import com.example.crewstation.aop.aspect.annotation.LogReturnStatus;
import com.example.crewstation.aop.aspect.annotation.LogStatus;
import com.example.crewstation.auth.CustomUserDetails;
import com.example.crewstation.common.exception.MemberNotFoundException;
import com.example.crewstation.common.exception.PostNotFoundException;
import com.example.crewstation.dto.reply.ReplyCriteriaDTO;
import com.example.crewstation.dto.reply.ReplyDTO;
import com.example.crewstation.repository.alarm.AlarmDAO;
import com.example.crewstation.repository.diary.DiaryDAO;
import com.example.crewstation.repository.post.PostDAO;
import com.example.crewstation.repository.reply.ReplyDAO;
import com.example.crewstation.service.s3.S3Service;
import com.example.crewstation.util.Criteria;
import com.example.crewstation.util.DateUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReplyServiceImpl implements ReplyService {
    private final ReplyDAO replyDAO;
    private final AlarmDAO alarmDAO;
    private final S3Service s3Service;
    private final PostDAO postDAO;
    private final DiaryDAO diaryDAO;

    @Override
    @LogReturnStatus
    public ReplyCriteriaDTO getReplies(int page,
                                       Long postId,
                                       CustomUserDetails customUserDetails) {
        ReplyCriteriaDTO replyCriteriaDTO = new ReplyCriteriaDTO();
        Criteria criteria = new Criteria(page, replyDAO.findAllCountByPostId(postId), 5, 5);
        List<ReplyDTO> replies = replyDAO.findAllByPostId(postId, criteria);
        replies.forEach(reply -> {
            if (reply.getFilePath() != null) {
                reply.setFilePath(s3Service.getPreSignedUrl(reply.getFilePath(), Duration.ofMinutes(5)));
            }
            reply.setRelativeDate(DateUtils.toRelativeTime(reply.getCreatedDatetime()));
            if (customUserDetails != null) {
                reply.setWriter(reply.getMemberId().equals(customUserDetails.getId()));
            }
        });
        replyCriteriaDTO.setReplies(replies);
        replyCriteriaDTO.setCriteria(criteria);
        return replyCriteriaDTO;
    }

    @Override
    @LogStatus
    @Transactional(rollbackFor = Exception.class)
    public void write(ReplyDTO replyDTO, CustomUserDetails customUserDetails) {
        if (!postDAO.isActivePost(replyDTO.getPostId())) {
            throw new PostNotFoundException("이미 삭제된 게시글입니다.");
        }
        if (customUserDetails == null) {
            throw new MemberNotFoundException("로그인 후 사용 가능");
        }
        replyDTO.setMemberId(customUserDetails.getId());
        replyDAO.save(replyDTO);
        alarmDAO.saveReplyAlarm(replyDTO.getPostId());
        diaryDAO.changeReplyCount(+1, replyDTO.getPostId());
    }

    @Override
    @LogStatus
    public void upate(Long replyId,ReplyDTO replyDTO) {
        replyDTO.setId(replyId);
        if (!postDAO.isActivePost(replyDTO.getPostId())) {
            throw new PostNotFoundException("이미 삭제된 게시글입니다.");
        }
        replyDAO.update(toReplyVO(replyDTO));
    }

    @Override
    @LogStatus
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long replyId, Long postId) {
        if (!postDAO.isActivePost(postId)) {
            throw new PostNotFoundException("이미 삭제된 게시글입니다.");
        }
        replyDAO.softDelete(replyId);
        diaryDAO.changeReplyCount(-1, postId);
    }
}
