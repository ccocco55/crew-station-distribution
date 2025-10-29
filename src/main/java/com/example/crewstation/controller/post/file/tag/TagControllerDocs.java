package com.example.crewstation.controller.post.file.tag;

import com.example.crewstation.auth.CustomUserDetails;
import com.example.crewstation.dto.like.LikeDTO;
import com.example.crewstation.dto.member.MemberDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.ibatis.annotations.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

// http://localhost:10000/swagger-ui/index.html
@Tag(name = "Tag", description = "Tag API")
public interface TagControllerDocs {
    @Operation(summary = "멤버 검색",
    description = "태그를 달기 위해 멤버를 검색한다",
    parameters = {
            @Parameter(name = "search",description = "멤버이름 또는 이메일이 들어온다"),
    })
    public ResponseEntity<List<MemberDTO>> getTagList(@Param("search")String search);
}
