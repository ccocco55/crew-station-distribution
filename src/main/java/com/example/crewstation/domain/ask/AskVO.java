package com.example.crewstation.domain.ask;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@Setter @Getter @ToString
public class AskVO {
    private Long id;
    private String inquiryTitle;
    private String inquiryContent;
    private Long memberId;
    private LocalDateTime createdDatetime;
    private LocalDateTime updatedDatetime;
}