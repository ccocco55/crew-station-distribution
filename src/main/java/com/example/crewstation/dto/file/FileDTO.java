package com.example.crewstation.dto.file;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
@EqualsAndHashCode(of="id")
public class FileDTO {
    private Long id;
    private String fileOriginName;
    private String filePath;
    private String fileName;
    private String fileSize;
    private String createdDatetime;
    private String updatedDatetime;
    @Override
    public String toString() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            // this 객체를 JSON 문자열로 변환
            return mapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return super.toString();
        }
    }
}
