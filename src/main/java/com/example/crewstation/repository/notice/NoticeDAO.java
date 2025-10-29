package com.example.crewstation.repository.notice;

import com.example.crewstation.domain.notice.NoticeVO;
import com.example.crewstation.dto.notice.NoticeCriteriaDTO;
import com.example.crewstation.dto.notice.NoticeDTO;
import com.example.crewstation.mapper.notice.NoticeMapper;
import com.example.crewstation.util.Criteria;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class NoticeDAO {

    private final NoticeMapper noticeMapper;

    //조회
    public List<NoticeVO> findAllNotice(Criteria criteria){

        return noticeMapper.selectAllNotice(criteria);
    }

    //전체 개수
    public int getTotal(){

        return noticeMapper.selectCountAll();
    }

//    관리자 공지사항 목록 조회
    public List<NoticeCriteriaDTO>  findNoticeCriteriaForAdmin(Criteria criteria){
        return noticeMapper.selectNoticeCriteria(criteria);
    }

//    공지사항 작성
    public int insert(NoticeDTO noticeDTO) {
        return noticeMapper.insertNotice(noticeDTO);
    }

}
