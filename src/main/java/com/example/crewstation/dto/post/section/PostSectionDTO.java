package com.example.crewstation.dto.post.section;


import com.example.crewstation.common.enumeration.Status;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
@ToString
@EqualsAndHashCode(of="id")
public class PostSectionDTO{
    private Long id;
    private String postContent;
    private Long postId;
    private String createdDatetime;
    private String updatedDatetime;
}
