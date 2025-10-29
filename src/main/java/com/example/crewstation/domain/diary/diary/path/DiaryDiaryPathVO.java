package com.example.crewstation.domain.diary.diary.path;

import com.example.crewstation.audit.Period;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@ToString(callSuper = true)
@Getter
@EqualsAndHashCode(of = "id",callSuper = false)
public class DiaryDiaryPathVO extends Period {
    private Long id;
    private Long postId;
    private Long diaryPathId;
}
