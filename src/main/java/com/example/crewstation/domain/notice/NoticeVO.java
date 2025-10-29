package com.example.crewstation.domain.notice;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Component;

@Component
@Getter @Setter @ToString
public class NoticeVO {
    private Long id;
    private String noticeTitle;
    private String createdDatetime;
    private String updatedDatetime;

}
