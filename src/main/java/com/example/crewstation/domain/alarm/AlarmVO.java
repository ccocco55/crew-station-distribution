package com.example.crewstation.domain.alarm;

import com.example.crewstation.audit.Period;
import com.example.crewstation.common.enumeration.ReadStatus;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@ToString(callSuper = true)
@Getter
@EqualsAndHashCode(of = "id",callSuper = false)
public class AlarmVO extends Period {
    private Long id;
    private Long foreignId;
    private ReadStatus readStatus;


}
