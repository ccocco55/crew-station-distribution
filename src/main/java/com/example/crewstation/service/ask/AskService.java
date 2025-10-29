package com.example.crewstation.service.ask;

import com.example.crewstation.domain.ask.AskVO;
import com.example.crewstation.repository.ask.AskDAO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AskService {

    private final AskDAO askDAO;

    public void register(AskVO askVO){
        askDAO.save(askVO);
    }
    public void modify(AskVO askVO){
        askDAO.setAskVO(askVO);
    }
    public void remove(Long id){
        askDAO.delete(id);
    }

}
