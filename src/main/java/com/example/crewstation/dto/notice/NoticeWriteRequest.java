package com.example.crewstation.dto.notice;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class NoticeWriteRequest {
    private Long memberId;
    private String title;
    private String content;
}
