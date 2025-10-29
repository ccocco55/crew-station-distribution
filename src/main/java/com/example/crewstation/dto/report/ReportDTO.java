package com.example.crewstation.dto.report;

import com.example.crewstation.common.enumeration.ProcessStatus;
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
public class ReportDTO {
    private Long id;
    private String reportContent;
    private Long replyId;
    private ProcessStatus processStatus;
    private Long memberId;
    private Long postId;
    private String createdDatetime;
    private String updatedDatetime;
}
