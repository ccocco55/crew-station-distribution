package com.example.crewstation.mapper.ask;

import com.example.crewstation.domain.ask.AskVO;
import com.example.crewstation.dto.ask.AskDTO;
import com.example.crewstation.util.Search;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AskMapper {
    public void insert(AskVO askVO);
    public void update(AskVO askVO);
    public void delete(Long id);

//    관리자 문의 목록
    public List<AskDTO> selectInquiryList(Search search);

//    관리자 문의 상세 조회
    public AskDTO selectInquiryById(Long id);

//    문의 답변 등록
    public void insertReply(AskDTO replyDTO);


}
