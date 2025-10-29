package com.example.crewstation.dto.accompany;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@Getter
@Setter
@ToString
@EqualsAndHashCode(of="id")
public class AccompanyDTO {
    private Long postId;
    private String postTitle;
    private String createdDatetime;
    private String updatedDatetime;
    private String accompanyStatus;
    private String accompanyAgeRange;
    private String countryStartDate;
    private String countryEndDate;
    private Long countryId;
    private String countryName;
    private String countries;
    private Long memberId;
    private String memberName;
    private String memberDescription;
    private Long fileId;
    private String fileOriginName;
    private String filePath;
    private String fileName;
    private Long crewId;
    private String crewName;
    private String crewDescription;
    private String socialImgUrl;
    private String postContent;
    private int fileCount;
    private List<AccompanyPathDTO> accompanyPathDTO;
    private String relativeDate;
}
