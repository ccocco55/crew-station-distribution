package com.example.crewstation.controller.report;


import com.example.crewstation.auth.CustomUserDetails;
import com.example.crewstation.common.exception.MemberNotFoundException;
import com.example.crewstation.common.exception.PostNotFoundException;
import com.example.crewstation.dto.report.ReportDTO;
import com.example.crewstation.service.report.ReportService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/report/**")
@Slf4j
@RequiredArgsConstructor
public class ReportRestController {
    private final ReportService reportService;

    @PostMapping("{postId}")
    public ResponseEntity<String> reportPost(@PathVariable Long postId, @RequestBody String reportContent, @AuthenticationPrincipal CustomUserDetails userDetails) {
        try{

            reportService.report(postId,reportContent,userDetails);
            return ResponseEntity.ok().body("신고 완료되었습니다.");
        }catch (PostNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }catch (MemberNotFoundException e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }
    @PostMapping("replies/{replyId}")
    public ResponseEntity<String> reportReply(@PathVariable Long replyId, @RequestBody ReportDTO reportDTO, @AuthenticationPrincipal CustomUserDetails userDetails) {
        try{
            reportService.reportReply(replyId,reportDTO,userDetails);
            log.info(reportDTO.toString());
            return ResponseEntity.ok().body("신고 완료되었습니다.");
        }catch (PostNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }catch (MemberNotFoundException e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }
}
