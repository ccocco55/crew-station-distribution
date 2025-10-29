package com.example.crewstation.controller.purchase;

import com.example.crewstation.auth.JwtTokenProvider;
import com.example.crewstation.common.exception.PostNotFoundException;
import com.example.crewstation.dto.member.MemberDTO;
import com.example.crewstation.dto.purchase.PurchaseCriteriaDTO;
import com.example.crewstation.dto.purchase.PurchaseDTO;
import com.example.crewstation.dto.report.ReportDTO;
import com.example.crewstation.service.purchase.PurchaseService;
import com.example.crewstation.util.Search;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/gifts/**")
public class PurchaseRestController {
    private final PurchaseService purchaseService;
    //    임시 로그인
    private final JwtTokenProvider jwtTokenProvider;

    @GetMapping
    public ResponseEntity<PurchaseCriteriaDTO> getPurchases(@RequestParam int page,@RequestParam String keyword) {
        Search search = new Search();
        search.setPage(page);
        search.setKeyword(keyword);
        PurchaseCriteriaDTO purchases = purchaseService.getPurchases(search);
        return ResponseEntity.ok().body(purchases);
    }



//  임시 로그인 정보 가져오기 나중에 삭제해야합니다.
    @GetMapping("info")
    public ResponseEntity<MemberDTO> getPurchase(HttpServletRequest request) {
        String token = jwtTokenProvider.parseTokenFromHeader(request);
        if (token == null) {
            throw   new RuntimeException("토큰이 없습니다.");
        }
        // 블랙리스트 체크 추가
        if (jwtTokenProvider.isTokenBlackList(token)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "로그아웃된 토큰입니다.");
        }
        String memberEmail = jwtTokenProvider.getUserName(token);
        String provider = (String) jwtTokenProvider.getClaims(token).get("provider");
        MemberDTO member = new MemberDTO();
        member.setId(1L);
        return ResponseEntity.ok().body(member);
    }

}
