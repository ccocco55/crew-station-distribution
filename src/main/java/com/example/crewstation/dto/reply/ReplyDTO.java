package com.example.crewstation.dto.reply;

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
public class ReplyDTO {
    private Long id;
    private String replyContent;
    private Long diaryId;
    private Long postId;
    private String memberName;
    private Long memberId;
    private String filePath;
    private String socialImgUrl;
    private String createdDatetime;
    private String relativeDate;
    private String updatedDatetime;
    private boolean writer;
}
