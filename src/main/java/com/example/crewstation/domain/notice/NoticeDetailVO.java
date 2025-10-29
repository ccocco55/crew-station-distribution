package com.example.crewstation.domain.notice;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class NoticeDetailVO {
    private Long id;
    private String noticeTitle;
    private String noticeContent;
    private Long memberId;
    private String createdDatetime;
    private String updatedDatetime;
}
