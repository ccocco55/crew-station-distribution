package com.example.crewstation.dto.notice;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class NoticeDTO {
    private Long id;
    private String noticeTitle;
    private String noticeContent;
    private Long memberId;

}
