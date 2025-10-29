package com.example.crewstation.domain.crew;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
public class CrewVO {
    private Long id;
    private String crewName;
    private String crewDescription;
    private int crewMemberCount;
    private String createdDatetime;
    private String updatedDatetime;
}
