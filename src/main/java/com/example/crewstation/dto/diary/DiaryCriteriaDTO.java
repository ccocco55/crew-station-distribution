package com.example.crewstation.dto.diary;

import com.example.crewstation.dto.purchase.PurchaseDTO;
import com.example.crewstation.util.Criteria;
import com.example.crewstation.util.Search;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Getter
@Setter
@ToString
public class DiaryCriteriaDTO {
    private List<DiaryDTO> diaryDTOs;
    private Criteria criteria;
    private Search search;
    private int totalCount;
}
