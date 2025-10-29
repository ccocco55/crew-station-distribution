package com.example.crewstation.domain.report.post;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@ToString(callSuper = true)
@Getter
@EqualsAndHashCode(of = "id")
public class ReportPostVO {
    private Long reportId;
    private Long postId;
}
