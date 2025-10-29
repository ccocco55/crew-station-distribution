package com.example.crewstation.controller.reply;

import com.example.crewstation.auth.CustomUserDetails;
import com.example.crewstation.common.exception.MemberNotFoundException;
import com.example.crewstation.common.exception.PostNotActiveException;
import com.example.crewstation.dto.reply.ReplyCriteriaDTO;
import com.example.crewstation.dto.reply.ReplyDTO;
import com.example.crewstation.service.reply.ReplyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/replies/**")
@Slf4j
@RequiredArgsConstructor
public class ReplyRestController {
    private final ReplyService replyService;

    @GetMapping("{postId}")
    public ResponseEntity<ReplyCriteriaDTO> getReplies(
            @RequestParam int page,
            @PathVariable Long postId,
            @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        ReplyCriteriaDTO replies = replyService.getReplies(page, postId, customUserDetails);
        return ResponseEntity.ok(replies);
    }


    @PostMapping("write")
    public ResponseEntity<String> write(@RequestBody ReplyDTO replyDTO, @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        try {
            replyService.write(replyDTO, customUserDetails);
            return ResponseEntity.ok().body("작성 성공");
        } catch (PostNotActiveException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (MemberNotFoundException e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

    @PutMapping("{replyId}")
    public ResponseEntity<String> modify(@PathVariable Long replyId,@RequestBody ReplyDTO replyDTO) {
        try {
            replyService.upate(replyId,replyDTO);
            return ResponseEntity.ok().body("수정 성공");
        } catch (PostNotActiveException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
    @DeleteMapping("{replyId}")
    public ResponseEntity<String> delete(@PathVariable Long replyId, @RequestBody Long postId) {
        try {
            replyService.delete(replyId,postId);
            return ResponseEntity.ok().body("삭제 성공");
        } catch (PostNotActiveException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
