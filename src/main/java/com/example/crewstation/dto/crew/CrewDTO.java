package com.example.crewstation.dto.crew;

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
public class CrewDTO {
    private Long id;
    private String crewName;
    private String crewDescription;
    private String crewRole;
    private int crewMemberCount;
    private String createdDatetime;
    private String updatedDatetime;
    private String fileOriginName;
    private String fileId;
    private String fileName;
    private String filePath;
    private Long memberId;
    private String memberName;
    private String memberSocialUrl;
    private String socialImgUrl;
    private int fileCount;
}
