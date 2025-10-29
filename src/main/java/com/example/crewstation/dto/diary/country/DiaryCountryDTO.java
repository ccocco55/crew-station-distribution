package com.example.crewstation.dto.diary.country;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
@ToString
public class DiaryCountryDTO {
    private Long id;
    private Long postId;
    private Long countryId;
}
