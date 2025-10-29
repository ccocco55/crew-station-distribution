package com.example.crewstation.domain.diary;

import com.example.crewstation.audit.Period;
import com.example.crewstation.common.enumeration.Secret;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Getter
@ToString
@SuperBuilder
@EqualsAndHashCode(of = "postId",callSuper = false)
public class DiaryVO extends Period {
    private Long postId;
    private Secret diarySecret;
    private String diaryLikeCount;
    private int diaryReplyCount;
    private String diaryCountryPathId;
}
