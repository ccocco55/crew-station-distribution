package com.example.crewstation.domain.post.section;


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
public class PostSectionVO extends Period {
    private Long id;
    private String postContent;
    private Long postId;
}
