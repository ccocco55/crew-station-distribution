package com.example.crewstation.dto.diary;

import com.example.crewstation.audit.Period;
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
public class ReplyDiaryDTO {
    private Long postId;
    private String postTitle;
    private String content;
    private String mainImage;
    private String replyContent;
    private String createdDatetime;
    private String updatedDatetime;
    private String relativeDatetime;
}
