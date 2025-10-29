package com.example.crewstation.domain.crew;

import com.example.crewstation.common.enumeration.CrewRole;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
public class CrewMemberVO {
    private Long id;
    private CrewRole crewRole;
    private Long crewId;
    private Long memberId;
    private String createTime;
    private String updateTime;

}
