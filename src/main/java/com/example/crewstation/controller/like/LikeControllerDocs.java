package com.example.crewstation.controller.like;

import com.example.crewstation.auth.CustomUserDetails;
import com.example.crewstation.dto.diary.DiaryCriteriaDTO;
import com.example.crewstation.dto.diary.DiaryDTO;
import com.example.crewstation.dto.diary.LikedDiaryCriteriaDTO;
import com.example.crewstation.dto.diary.ReplyDiaryCriteriaDTO;
import com.example.crewstation.dto.like.LikeDTO;
import com.example.crewstation.util.Search;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

// http://localhost:10000/swagger-ui/index.html
@Tag(name = "Like", description = "Like API")
public interface LikeControllerDocs {
    @Operation(summary = "다이어리 좋아요",
    description = "다이어리 좋아요 추가",
    parameters = {
            @Parameter(name = "postId",description = "다이어리의 게시글 아이디가 들어온다"),
            @Parameter(name="userDetails",description = "로그인한 회원의 정보가 들어온다.")
    })
    public ResponseEntity<String> addLike(@PathVariable Long postId, @AuthenticationPrincipal CustomUserDetails userDetails);

    @Operation(summary = "다이어리 좋아요",
            description = "다이어리 좋아요 삭제",
            parameters = {
                    @Parameter(name = "postId",description = "다이어리의 게시글 아이디가 들어온다"),
                    @Parameter(name="userDetails",description = "로그인한 회원의 정보가 들어온다.")
            })
    public ResponseEntity<String> deleteLike(@PathVariable Long postId,@AuthenticationPrincipal CustomUserDetails userDetails);
}
