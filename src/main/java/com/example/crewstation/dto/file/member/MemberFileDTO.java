package com.example.crewstation.dto.file.member;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
public class MemberFileDTO {
    private Long FileId;
    private Long memberId;


}
