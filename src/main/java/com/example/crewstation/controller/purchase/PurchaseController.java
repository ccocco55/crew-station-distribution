package com.example.crewstation.controller.purchase;

import com.example.crewstation.auth.CustomUserDetails;
import com.example.crewstation.auth.JwtTokenProvider;
import com.example.crewstation.common.exception.PurchaseNotFoundException;
import com.example.crewstation.dto.member.MemberDTO;
import com.example.crewstation.dto.purchase.PurchaseDTO;
import com.example.crewstation.dto.purchase.PurchaseDetailDTO;
import com.example.crewstation.dto.purchase.PurchaseListCriteriaDTO;
import com.example.crewstation.service.purchase.PurchaseService;
import com.example.crewstation.util.ScrollCriteria;
import com.example.crewstation.util.Search;
import jakarta.servlet.http.Cookie;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.util.*;

@Controller
@RequestMapping("/gifts/**")
@RequiredArgsConstructor
@Slf4j
public class PurchaseController {
    private final PurchaseService purchaseService;
    //    임시 로그인
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;
    private final PurchaseListCriteriaDTO purchaseListCriteriaDTO;

    @GetMapping
    public String list() {
        return "gift-shop/list";
    }

    @GetMapping("detail/{postId}")
    public String detail(@PathVariable Long postId, Model model,@AuthenticationPrincipal CustomUserDetails customUserDetails) {

        PurchaseDTO purchase = purchaseService.getPurchase(postId);
        log.info("purchase {}:::::::::::", purchase);
        model.addAttribute("purchase", purchase);
        model.addAttribute("writer", customUserDetails != null && customUserDetails.getId().equals(purchase.getMemberId()));
        return "gift-shop/detail";
    }

    @GetMapping("write")
    public String goToWriteForm(PurchaseDTO purchaseDTO, Model model) {
        model.addAttribute("purchase", purchaseDTO);
        return "gift-shop/write";
    }

    @PostMapping("write")
    public RedirectView write(PurchaseDTO purchaseDTO,
                      @AuthenticationPrincipal CustomUserDetails customUserDetails,
                              @RequestParam("files") List<MultipartFile> files) {
        log.info("purchaseDTO {}", purchaseDTO);
        log.info("files {}", files.size());
//        purchaseDTO.setMemberId(1L);
        purchaseDTO.setMemberId(customUserDetails.getId());
        purchaseService.write(purchaseDTO, files);

//        purchaseDTO.setMemberId(customUserDetails.getId());

        return new RedirectView("/gifts/detail/" + purchaseDTO.getPostId());
    }

    @GetMapping("{postId}")
    public String goToModifyForm(@PathVariable Long postId, Model model) {
        PurchaseDTO purchase = purchaseService.getPurchase(postId);
        log.info("purchase {}", purchase);
        model.addAttribute("purchase", purchase);
        return "gift-shop/update";
    }

    @PostMapping("{postId}")
    public RedirectView modify(@PathVariable Long postId,
                         PurchaseDTO purchaseDTO,
                         Long[] deleteFiles,
                         @RequestParam("files") List<MultipartFile> files,
                         @AuthenticationPrincipal UserDetails userDetails) {
        purchaseDTO.setPostId(postId);
        log.info("purchaseDTO {}", purchaseDTO);
        purchaseService.update(purchaseDTO, deleteFiles, files);
        return  new RedirectView("/gifts/detail/" + postId);
    }

    @GetMapping("delete/{id}")
    public RedirectView delete(@PathVariable Long id) {
        purchaseService.softDelete(id);
        return new RedirectView("/gifts");
    }


}
