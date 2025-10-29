package com.example.crewstation.dto.post.section;

import com.example.crewstation.common.enumeration.Type;
import com.example.crewstation.dto.post.file.tag.PostFileTagDTO;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Getter
@Setter
@ToString
@EqualsAndHashCode(of="fileId")
public class SectionDTO {
    private Long postId;
    private Long fileId;
    private Long postSectionId;
    private String postContent;
    private Type imageType;
    private String fileOriginName;
    private String fileName;
    private String filePath;
    private String fileSize;
    private List<PostFileTagDTO> tags;
    private String createdDatetime;
    private String updatedDatetime;
}