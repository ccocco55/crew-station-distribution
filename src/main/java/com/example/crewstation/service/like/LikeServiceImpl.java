package com.example.crewstation.service.like;

import com.example.crewstation.aop.aspect.annotation.LogReturnStatus;
import com.example.crewstation.aop.aspect.annotation.LogStatus;
import com.example.crewstation.auth.CustomUserDetails;
import com.example.crewstation.common.exception.MemberNotFoundException;
import com.example.crewstation.common.exception.PostNotFoundException;
import com.example.crewstation.dto.diary.DiaryDTO;
import com.example.crewstation.dto.like.LikeDTO;
import com.example.crewstation.dto.member.MemberDTO;
import com.example.crewstation.repository.alarm.AlarmDAO;
import com.example.crewstation.repository.diary.DiaryDAO;
import com.example.crewstation.repository.like.LikeDAO;
import com.example.crewstation.repository.post.PostDAO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class LikeServiceImpl implements LikeService {
    private final LikeDAO likeDAO;
    private final AlarmDAO alarmDAO;
    private final PostDAO postDAO;
    private final DiaryDAO diaryDAO;

    @Override
    @LogStatus
    @Transactional(rollbackFor = Exception.class)
    public void like(Long postId, CustomUserDetails customUserDetails) {
        LikeDTO likeDTO = new LikeDTO();
//        임시방편
        if (customUserDetails == null) {
            throw new MemberNotFoundException("로그인 후 사용 가능");
        }
        if (!postDAO.isActivePost(postId)) {
            throw new PostNotFoundException("이미 삭제된 게시글입니다.");
        }
        likeDTO.setMemberId(customUserDetails.getId());
        likeDTO.setPostId(postId);
//        likeDTO.setMemberId(1L);
        diaryDAO.changeLikeCount(+1,postId);
        likeDAO.saveLike(likeDTO);
        alarmDAO.saveLikeAlarm(likeDTO.getId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @LogStatus
    public void unlike(Long postId, CustomUserDetails customUserDetails) {
//        customUserDetails = new CustomUserDetails(new MemberDTO()); // 임시
        DiaryDTO diaryDTO = new DiaryDTO();
        diaryDTO.setPostId(postId);
        diaryDTO.setMemberId(customUserDetails.getId());
        diaryDTO.setUserId(customUserDetails.getId());
        if (customUserDetails == null) {
            throw new MemberNotFoundException("로그인 후 사용 가능");
        }
        if (!postDAO.isActivePost(postId)) {
            throw new PostNotFoundException("이미 삭제된 게시글입니다.");
        }
        diaryDAO.changeLikeCount(-1,postId);
        Long likeId = likeDAO.isLikeByPostIdAndMemberId(diaryDTO);
        log.info("{}",likeId);
        alarmDAO.deleteLikeAlarm(likeId);
        likeDAO.deleteLike(likeId);

    }
}
