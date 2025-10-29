package com.example.crewstation.controller.diary;

import com.example.crewstation.auth.CustomUserDetails;
import com.example.crewstation.common.exception.PostNotActiveException;
import com.example.crewstation.dto.diary.DiaryCriteriaDTO;
import com.example.crewstation.dto.diary.DiaryDTO;
import com.example.crewstation.dto.diary.LikedDiaryCriteriaDTO;
import com.example.crewstation.dto.diary.ReplyDiaryCriteriaDTO;
import com.example.crewstation.service.diary.DiaryService;
import com.example.crewstation.util.ScrollCriteria;
import com.example.crewstation.util.Search;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/diaries/**")
public class DiaryRestController {

    private final DiaryService diaryService;

        // 좋아요한 일기 목록 조회 (무한스크롤)
        @GetMapping("/liked")
        public ResponseEntity<LikedDiaryCriteriaDTO> getLikedDiaries(
                @AuthenticationPrincipal CustomUserDetails customUserDetails,
                @RequestParam(defaultValue = "1") int page,
                @RequestParam(defaultValue = "10") int size) {

            ScrollCriteria criteria = new ScrollCriteria(page, size);
            LikedDiaryCriteriaDTO dto = diaryService.getDiariesLikedByMemberId(customUserDetails, criteria);

            return ResponseEntity.ok(dto);
        }

        // 좋아요한 일기 총 개수 반환
        @GetMapping("/liked/count")
        public ResponseEntity<Integer> getLikedDiaryCount(
                @AuthenticationPrincipal CustomUserDetails customUserDetails) {

            int count = diaryService.getCountDiariesLikedByMemberId(customUserDetails);
            return ResponseEntity.ok(count);
        }

        // 좋아요 취소
        @DeleteMapping("/liked/{diaryId}")
        public ResponseEntity<Map<String, Object>> cancelLikedDiary(
                @AuthenticationPrincipal CustomUserDetails customUserDetails,
                @PathVariable Long diaryId) {
            try {
                diaryService.cancelLike(customUserDetails, diaryId);
                return ResponseEntity.ok(Map.of("success", true));
            } catch (Exception e) {
                log.error("좋아요 취소 실패 - diaryId={}", diaryId, e);
                return ResponseEntity.badRequest()
                        .body(Map.of("success", false, "message", e.getMessage()));
            }
        }

        // 내가 댓글 단 다이어리 목록 (무한스크롤)
        @GetMapping("/replies")
        public ResponseEntity<ReplyDiaryCriteriaDTO> getReplyDiaries(
                @AuthenticationPrincipal CustomUserDetails customUserDetails,
                @RequestParam(defaultValue = "1") int page,
                @RequestParam(defaultValue = "10") int size) {

            ScrollCriteria criteria = new ScrollCriteria(page, size);
            ReplyDiaryCriteriaDTO dto = diaryService.getReplyDiariesByMemberId(customUserDetails, criteria);

            return ResponseEntity.ok(dto);
        }

        // 댓글 단 다이어리 개수 조회
        @GetMapping("/replies/count")
        public ResponseEntity<Integer> getReplyDiaryCount(
                @AuthenticationPrincipal CustomUserDetails customUserDetails) {

            int count = diaryService.getCountReplyDiariesByMemberId(customUserDetails);
            return ResponseEntity.ok(count);
        }

    @GetMapping
    public ResponseEntity<DiaryCriteriaDTO> getDiaries(Search search,
                                                       @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        log.info("search: {}", search);
        DiaryCriteriaDTO diaries = diaryService.getDiaries(search, customUserDetails);
        log.info("diaries::::::::::::::::::::::::::::::::::::: {}", diaries);
        return ResponseEntity.ok(diaries);
    }

    @PutMapping("/secret/{diaryId}")
    public ResponseEntity<String> changeSecret(@PathVariable Long diaryId,@RequestBody boolean check) {
        try {
//            log.info("changeSecret: {}", diaryDTO);
            log.info("diaryId: {}", diaryId);
            String message = diaryService.changeSecret(diaryId,check);
            return ResponseEntity.ok(message);
        } catch (PostNotActiveException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("오류가 발생했습니다.");
        }


    }
    @GetMapping("/profile/{memberId}")
    public List<DiaryDTO> getProfile(@PathVariable Long memberId) {
            return diaryService.findDiaryById(memberId);
    }
}