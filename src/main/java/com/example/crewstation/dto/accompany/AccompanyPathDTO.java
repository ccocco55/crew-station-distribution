package com.example.crewstation.dto.accompany;

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
public class AccompanyPathDTO {
    private Long id;
    private String countryStartDate;
    private String countryEndDate;
    private String countryName;
    private Long accompanyId;
}
