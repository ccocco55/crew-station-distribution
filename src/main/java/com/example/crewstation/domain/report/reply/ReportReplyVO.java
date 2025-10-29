package com.example.crewstation.domain.report.reply;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@ToString(callSuper = true)
@Getter
@EqualsAndHashCode(of = "id")
public class ReportReplyVO {
    private Long reportId;
    private Long replyId;
}
