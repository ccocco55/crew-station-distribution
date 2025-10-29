package com.example.crewstation.controller.reply;

import com.example.crewstation.auth.CustomUserDetails;
import com.example.crewstation.dto.reply.ReplyCriteriaDTO;
import com.example.crewstation.dto.reply.ReplyDTO;
import com.example.crewstation.dto.report.ReportDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

// http://localhost:10000/swagger-ui/index.html
@Tag(name = "Post", description = "Post API")
public interface ReplyControllerDocs {
    @Operation(summary = "댓글 불러오기",
    description = "다이어리 댓글 목록 불러오기",
    parameters = {
            @Parameter(name = "page",description = "페이지 정보가 들어온다"),
            @Parameter(name="postId",description = "게시글 아이디가 들어온다"),
            @Parameter(name="customUserDetails",description = "로그인한 회원의 정보가 들어온다.")
    })
    public ResponseEntity<ReplyCriteriaDTO> getReplies(
            @RequestParam int page,
            @PathVariable Long postId,
            @AuthenticationPrincipal CustomUserDetails customUserDetails);

    @Operation(summary = "댓글 작성",
            description = "다이어리 댓글 작성",
            parameters = {
                    @Parameter(name = "replyDTO",description = "댓글 쓴 정보가 들어온다"),
                    @Parameter(name="customUserDetails",description = "로그인한 회원의 정보가 들어온다.")
            })
    public ResponseEntity<String> write(@RequestBody ReplyDTO replyDTO, @AuthenticationPrincipal CustomUserDetails customUserDetails);

    @Operation(summary = "댓글 수정",
            description = "다이어리 댓글 수정",
            parameters = {
                    @Parameter(name = "replyId",description = "수정한 댓글 아이디가 들어온다"),
                    @Parameter(name = "replyDTO",description = "수정한 댓글의 게시글 아이디와 내용이 들어온다"),
            })
    public ResponseEntity<String> modify(@PathVariable Long replyId,@RequestBody ReplyDTO replyDTO);

    @Operation(summary = "댓글 삭제",
            description = "다이어리 댓글 삭제",
            parameters = {
                    @Parameter(name = "replyId",description = "삭제한 댓글 아이디가 들어온다."),
                    @Parameter(name = "postId",description = "삭제한 댓글의 게시글 아이디가 들어온다."),
            })
    public ResponseEntity<String> delete(@PathVariable Long replyId, @RequestBody Long postId);
}
