package com.example.crewstation.domain.accompany;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
public class AccompanyVO {
    private Long postId;
    private String accompanyStatus;
    private String accompanyAgeRange;
}
