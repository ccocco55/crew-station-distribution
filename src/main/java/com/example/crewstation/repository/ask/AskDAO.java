package com.example.crewstation.repository.ask;

import com.example.crewstation.domain.ask.AskVO;
import com.example.crewstation.mapper.ask.AskMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class AskDAO {

    private final AskMapper askMapper;
    public void save(AskVO askVO){
        askMapper.insert(askVO);
    }
    public void setAskVO(AskVO askVO){
        askMapper.update(askVO);
    }
    public void delete(Long id){
        askMapper.delete(id);
    }

}
