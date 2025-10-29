package com.example.crewstation.dto.ask;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
public class AskDTO {
    private Long id;
    private String inquiryTitle;
    private String inquiryContent;
    private Long memberId;
    private String memberName;
    private String createdDatetime;
    private String updatedDatetime;
    private Long replyId;
    private String replyContent;
    private String inquiryStatus;

}
