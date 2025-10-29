package com.example.crewstation.domain.crew;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@ToString(callSuper = true)
public class CrewDiaryVO {
    private Long id;
    private Long crewId;
    private Long diaryId;
}
