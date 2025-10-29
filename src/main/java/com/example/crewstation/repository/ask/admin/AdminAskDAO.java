package com.example.crewstation.repository.ask.admin;

import com.example.crewstation.dto.ask.AskDTO;
import com.example.crewstation.mapper.ask.AskMapper;
import com.example.crewstation.util.Search;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class AdminAskDAO {

    private final AskMapper askMapper;

    // 문의 목록
    public List<AskDTO> selectInquiryList(Search search) {
        return askMapper.selectInquiryList(search);
    }

    // 문의 상세
    public AskDTO selectInquiryById(Long id) {
        return askMapper.selectInquiryById(id);
    }

    // 답변 등록
    public void insertReply(AskDTO askDTO){
        askMapper.insertReply(askDTO);
    }

}
