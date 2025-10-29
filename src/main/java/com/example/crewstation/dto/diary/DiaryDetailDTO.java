package com.example.crewstation.dto.diary;

import com.example.crewstation.dto.country.CountryDTO;
import com.example.crewstation.dto.post.section.SectionDTO;
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
public class DiaryDetailDTO {
    private List<SectionDTO> sections;
    private DiaryDTO diary;
    private List<CountryDTO> countries;
}
