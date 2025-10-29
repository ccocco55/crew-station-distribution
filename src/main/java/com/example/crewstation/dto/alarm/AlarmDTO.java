package com.example.crewstation.dto.alarm;

import com.example.crewstation.common.enumeration.ReadStatus;
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
public class AlarmDTO {
    private Long id;
    private Long foreignId;
    private ReadStatus readStatus;
    private String createdDatetime;
    private String updatedDatetime;

}
