package com.example.crewstation.controller.report;

import com.example.crewstation.auth.CustomUserDetails;
import com.example.crewstation.dto.report.ReportDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

// http://localhost:10000/swagger-ui/index.html
@Tag(name = "Report", description = "Report API")
public interface ReportControllerDocs {
    @Operation(summary = "게시글 신고",
    description = "게시글 본문 내용 신고하기",
    parameters = {
            @Parameter(name = "postId",description = "다이어리 게시글 아이디가 들어온다."),
            @Parameter(name = "reportContent",description = "신고 내용이 들어온다"),
            @Parameter(name="customUserDetails",description = "로그인한 회원의 정보가 들어온다.")
    })
    public ResponseEntity<String> reportPost(@PathVariable Long postId, @RequestBody String reportContent, @AuthenticationPrincipal CustomUserDetails userDetails);

    @Operation(summary = "댓글 신고하기",
            description = "다이어리 댓글 신고하기",
            parameters = {
                    @Parameter(name = "replyId",description = "댓글 아이디가 들어온다."),
                    @Parameter(name = "reportDTO",description = "다이어리 게시글 아이디와 신고 내용이 들어온다"),
                    @Parameter(name="customUserDetails",description = "로그인한 회원의 정보가 들어온다.")
            })
    public ResponseEntity<String> reportReply(@PathVariable Long replyId, @RequestBody ReportDTO reportDTO, @AuthenticationPrincipal CustomUserDetails userDetails);
}
