package com.example.crewstation.dto.member;

import lombok.*;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
@ToString
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
public class AddressDTO {
    private Long id;
    private String addressZipCode;
    private String addressDetail;
    private String address;
    private Long memberId;
    private String createdDatetime;
    private String updatedDatetime;

}
