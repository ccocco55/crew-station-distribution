package com.example.crewstation.domain.banner;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
public class BannerVO {
    private Long id;
    private int bannerOrder;
    private String createdDatetime;
    private String updatedDatetime;

}
