package com.example.crewstation.dto.reply;

import com.example.crewstation.dto.diary.DiaryDTO;
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
public class ReplyCriteriaDTO {
    private List<ReplyDTO> replies;
    private Criteria criteria;
}
