package com.example.crewstation.controller.like;

import com.example.crewstation.auth.CustomUserDetails;
import com.example.crewstation.common.exception.MemberNotFoundException;
import com.example.crewstation.common.exception.PostNotActiveException;
import com.example.crewstation.dto.like.LikeDTO;
import com.example.crewstation.service.like.LikeService;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@Service
@Slf4j
@RestController
@RequestMapping("/api/likes/**")
public class LikeRestController {
    private final LikeService likeService;

    @PostMapping("{postId}")
    public ResponseEntity<String> addLike(@PathVariable Long postId, @AuthenticationPrincipal CustomUserDetails userDetails) {
        try {

            likeService.like(postId, userDetails);

            return ResponseEntity.ok().body("좋아요를 추가하였습니다.");
        } catch (MemberNotFoundException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        } catch (PostNotActiveException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("오류가 발생했습니다.");
        }
    }

    @DeleteMapping("{postId}")
    public ResponseEntity<String> deleteLike(@PathVariable Long postId,@AuthenticationPrincipal CustomUserDetails userDetails) {
        try {
            log.info("deleteLike ========= {}", postId);
            likeService.unlike(postId, userDetails);
            return ResponseEntity.ok().body("좋아요를 취소하였습니다.");
        } catch (MemberNotFoundException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        } catch (PostNotActiveException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("오류가 발생했습니다.");
        }
    }
}
