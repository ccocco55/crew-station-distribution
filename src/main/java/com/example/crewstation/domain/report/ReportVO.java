package com.example.crewstation.domain.report;

import com.example.crewstation.common.enumeration.ProcessStatus;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@ToString(callSuper = true)
@Getter
@EqualsAndHashCode(of = "id")
public class ReportVO {
    private Long id;
    private String reportContent;
    private ProcessStatus processStatus;
    private Long memberId;
}
