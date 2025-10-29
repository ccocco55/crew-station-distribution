package com.example.crewstation.controller.diary;

import com.example.crewstation.auth.CustomUserDetails;
import com.example.crewstation.common.exception.PostNotActiveException;
import com.example.crewstation.dto.diary.DiaryCriteriaDTO;
import com.example.crewstation.dto.diary.DiaryDTO;
import com.example.crewstation.dto.diary.LikedDiaryCriteriaDTO;
import com.example.crewstation.dto.diary.ReplyDiaryCriteriaDTO;
import com.example.crewstation.dto.member.MemberDTO;
import com.example.crewstation.util.ScrollCriteria;
import com.example.crewstation.util.Search;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

// http://localhost:10000/swagger-ui/index.html
@Tag(name = "Diary", description = "Diary API")
public interface DiaryControllerDocs {


    public ResponseEntity<LikedDiaryCriteriaDTO> getLikedDiaries(
            @PathVariable Long memberId,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size);

    // 좋아요한 일기 총 개수 반환
    public ResponseEntity<Integer> getLikedDiaryCount(@PathVariable Long memberId);

    // 좋아요 취소
    public ResponseEntity<Map<String, Object>> cancelLikedDiary(
            @PathVariable Long memberId,
            @PathVariable Long diaryId) ;

    // 내가 댓글 단 다이어리 목록 (무한스크롤)
    public ResponseEntity<ReplyDiaryCriteriaDTO> getReplyDiaries(
            @PathVariable Long memberId,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) ;

    // 댓글 단 다이어리 개수 조회
    public ResponseEntity<Integer> getReplyDiaryCount(@PathVariable Long memberId) ;

    @Operation(summary = "다이어리 조회",
    description = "모든 다이어리 조회 및 이미지 개수랑 좋아요 여부",
    parameters = {
            @Parameter(name = "search",description = "검색 키워드, 정렬 순서, 페이지 정보가 들어온다."),
            @Parameter(name="customUserDetails",description = "로그인한 회원의 정보가 들어온다.")
    })
    public ResponseEntity<DiaryCriteriaDTO> getDiaries(Search search,
                                                       @AuthenticationPrincipal CustomUserDetails customUserDetails) ;

    @Operation(summary = "다이어리 설정 변경",
            description = "다이어리 공개, 비공개 설정 변경",
            parameters = {
                    @Parameter(name = "diaryId",description = "다이어리 게시글 아이디가 들어온다."),
                    @Parameter(name = "check",description = "공개인지 비공개인지 정보가 들어온다.")
            })
    public ResponseEntity<String> changeSecret(@PathVariable Long diaryId,@RequestBody boolean check) ;
}
