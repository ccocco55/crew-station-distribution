package com.example.crewstation.dto.file.section;


import com.example.crewstation.audit.Period;
import com.example.crewstation.common.enumeration.Type;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
@ToString
@EqualsAndHashCode(of="fileId")
public class PostSectionFileVO{
    private Long fileId;
    private Long postSectionId;
    private Type imageType;
}
