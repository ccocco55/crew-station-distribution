package com.example.crewstation.domain.post;


import com.example.crewstation.audit.Period;
import com.example.crewstation.common.enumeration.Status;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@ToString(callSuper = true)
@Getter
@EqualsAndHashCode(of = "id")
public class PostVO extends Period {

    private Long id;
    private String postTitle;
    private Status postStatus;
    private int postReadCount;
    private Long memberId;
}
