package com.example.crewstation.service.ask.adminAsk;

import com.example.crewstation.dto.ask.AskDTO;
import com.example.crewstation.util.Search;

import java.util.List;

public interface AdminAskService {

    // 문의 목록
    List<AskDTO> getInquiryList(Search search);

    // 문의 상세
    AskDTO getInquiryDetail(Long id);

    // 답변 등록
    void registerReply(AskDTO askDTO);
}
