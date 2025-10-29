package com.example.crewstation.mapper.notice;

import com.example.crewstation.domain.notice.NoticeVO;
import com.example.crewstation.dto.notice.NoticeCriteriaDTO;
import com.example.crewstation.dto.notice.NoticeDTO;
import com.example.crewstation.util.Criteria;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface NoticeMapper {
    public List<NoticeVO> selectAllNotice(Criteria criteria);
    public int selectCountAll();

//    관리자 공지사항 목록
    public List<NoticeCriteriaDTO> selectNoticeCriteria(Criteria criteria);

//    관리자 공지 작성
    public int insertNotice(NoticeDTO noticeDTO);


}
