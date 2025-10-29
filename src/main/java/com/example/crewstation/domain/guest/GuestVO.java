package com.example.crewstation.domain.guest;

import com.example.crewstation.audit.Period;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Getter
@ToString(callSuper = true)
@EqualsAndHashCode(of = "id", callSuper = false)
@SuperBuilder
public class GuestVO extends Period {
    private Long id;
    private Long memberId;
    private String guestPhone;
    private String guestPassword;
    private String guestOrderNumber;
    private String addressZipCode;
    private String addressDetail;
    private String address;
}
