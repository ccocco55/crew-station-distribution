package com.example.crewstation.domain.post.file.tag;

import com.example.crewstation.audit.Period;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@ToString(callSuper = true)
@Getter
@EqualsAndHashCode(of = "id")
public class PostFileTagVO extends Period {
    private Long id;
    private float tagLeft;
    private float tagTop;
    private Long memberId;
    private Long postSectionFileId;
}
