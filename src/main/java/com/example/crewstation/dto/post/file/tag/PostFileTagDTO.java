package com.example.crewstation.dto.post.file.tag;

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
public class PostFileTagDTO {
    private Long id;
    private float tagLeft;
    private float tagTop;
    private Long memberId;
    private String filePath;
    private String socialImgUrl;
    private String memberName;
    private Long postSectionFileId;
    private String createdDatetime;
    private String updatedDatetime;
}
