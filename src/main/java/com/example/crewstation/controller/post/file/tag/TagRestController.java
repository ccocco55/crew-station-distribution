package com.example.crewstation.controller.post.file.tag;

import com.example.crewstation.dto.member.MemberDTO;
import com.example.crewstation.service.member.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/tags/**")
public class TagRestController {
    private final MemberService memberService;

    @GetMapping
    public ResponseEntity<List<MemberDTO>> getTagList(@Param("search")String search){
        List<MemberDTO> memberDTOS = memberService.searchMember(search);
        return ResponseEntity.ok(memberDTOS);
    }
}
