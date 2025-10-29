package com.example.crewstation.audit;

import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@ToString
@Getter
public class Period {
    private String createdDatetime;
    private String updatedDatetime;
}
