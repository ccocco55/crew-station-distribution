package com.example.crewstation.dto.diary.file;

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
public class DiaryFileDTO {
    private Long id;
    private String filePath;
    private String fileName;
    private String fileOriginName;
    private String imageType;
}
