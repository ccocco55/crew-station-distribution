package com.example.crewstation.domain.address;

import com.example.crewstation.audit.Period;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Getter
@ToString(callSuper = true)
@EqualsAndHashCode(of = "id", callSuper = false)
@SuperBuilder
public class AddressVO extends Period {
    private Long id;
    private String addressZipCode;
    private String addressDetail;
    private String address;
    private Long memberId;
}
