package com.example.crewstation.controller.purchase;

import com.example.crewstation.auth.CustomUserDetails;
import com.example.crewstation.dto.like.LikeDTO;
import com.example.crewstation.dto.member.MemberDTO;
import com.example.crewstation.dto.purchase.PurchaseCriteriaDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

// http://localhost:10000/swagger-ui/index.html
@Tag(name = "Purchase", description = "Purchase API")
public interface PurchaseControllerDocs {
    @Operation(summary = "상품(기프트) 목록",
    description = "기프트 목록 무한 스크롤 방식으로 조회하기",
    parameters = {
            @Parameter(name = "page",description = "페이지 정보가 들어온다"),
            @Parameter(name="keyword",description = "검색 정보가 들어온다.")
    })
    public ResponseEntity<PurchaseCriteriaDTO> getPurchases(@RequestParam int page, @RequestParam String keyword);

}
