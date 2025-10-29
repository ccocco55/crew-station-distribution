package com.example.crewstation.dto.guest;

import lombok.*;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
@ToString
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
public class GuestDTO {
    private Long id;
    private Long memberId;
    private String guestPhone;
//    guestPassword: 인코딩된 핸드폰 번호
    private String guestPassword;
    private String guestOrderNumber;
    private String addressZipCode;
    private String addressDetail;
    private String address;
}
