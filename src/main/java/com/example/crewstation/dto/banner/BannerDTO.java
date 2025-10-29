package com.example.crewstation.dto.banner;

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
public class BannerDTO {
    private Long bannerId;
    private int bannerOrder;
    private String createdDatetime;
    private String updatedDatetime;
    private Long fileId;
    private String fileName;
    private String filePath;
    private String fileOriginName;
    private String url;

}
