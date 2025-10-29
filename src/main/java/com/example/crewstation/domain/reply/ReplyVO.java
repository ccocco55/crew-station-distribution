package com.example.crewstation.domain.reply;

import com.example.crewstation.audit.Period;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@ToString(callSuper = true)
@Getter
@EqualsAndHashCode(of = "id",callSuper = false)
public class ReplyVO extends Period {
    private Long id;
    private String replyContent;
    private Long diaryId;
    private Long memberId;
}
