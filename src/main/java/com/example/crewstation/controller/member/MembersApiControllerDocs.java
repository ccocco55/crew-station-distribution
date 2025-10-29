package com.example.crewstation.controller.member;

import com.example.crewstation.dto.member.MemberDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

// http://localhost:10000/swagger-ui/index.html
@Tag(name = "Member", description = "Member API")
public interface MembersApiControllerDocs {

    @Operation(summary = "이메일 중복 체크",
            description = "결과 값으로 이메일 중복 체크",
            parameters = {
                    @Parameter(name = "email", description = "회원가입 시 입력한 이메일")
            }
    )
    public ResponseEntity<Boolean> checkEmail(@RequestParam String email);

    @Operation(summary = "메일 보내기",
            description = "메일로 랜덤 알파벳 코드를 발송한다.",
            parameters = {
                    @Parameter(name = "email", description = "사용자가 입력한 이메일")
            }
    )
    public ResponseEntity<Map<String, String>> sendEmail(@RequestParam("email") String email);

    @Operation(summary = "핸드폰 인증 메세지 보내기",
            description = "핸드폰 메세지로 숫자 랜덤 코드를 발송한다.",
            parameters = {
                    @Parameter(name = "phone", description = "회원가입 시 입력한 핸드폰 번호")
            }
    )
    public ResponseEntity<Map<String, String>> checkPhone(@RequestParam String phone);
    public MemberDTO getProfileMember(Long memberId);
}
