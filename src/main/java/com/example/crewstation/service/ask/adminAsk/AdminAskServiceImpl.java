package com.example.crewstation.service.ask.adminAsk;

import com.example.crewstation.dto.ask.AskDTO;
import com.example.crewstation.repository.ask.admin.AdminAskDAO;
import com.example.crewstation.util.Search;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdminAskServiceImpl implements AdminAskService {

    private final AdminAskDAO adminAskDAO;

    @Override
    public List<AskDTO> getInquiryList(Search search) {
        return adminAskDAO.selectInquiryList(search);
    }

    @Override
    public AskDTO getInquiryDetail(Long id) {
        return adminAskDAO.selectInquiryById(id);
    }

    @Override
    public void registerReply(AskDTO askDTO) {
        adminAskDAO.insertReply(askDTO);

    }
}
