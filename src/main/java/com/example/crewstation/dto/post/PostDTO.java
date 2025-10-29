package com.example.crewstation.dto.post;

import com.example.crewstation.common.enumeration.Secret;
import com.example.crewstation.common.enumeration.Status;
import lombok.*;
import org.springframework.stereotype.Component;


@Component
@Getter
@Setter
@ToString
@EqualsAndHashCode(of="id")
public class PostDTO {
    private Long postId;
    private String postTitle;
    private Status postStatus;
    private int postReadCount;
    private Long memberId;
    private String createdDatetime;
    private String updatedDatetime;
    private Secret secret;
}
