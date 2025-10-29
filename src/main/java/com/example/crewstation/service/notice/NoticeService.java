package com.example.crewstation.service.notice;

import com.example.crewstation.dto.notice.NoticeCriteriaDTO;
import com.example.crewstation.dto.notice.NoticeDTO;
import com.example.crewstation.mapper.notice.NoticeMapper;
import com.example.crewstation.repository.notice.NoticeDAO;
import com.example.crewstation.util.Criteria;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class NoticeService {

    private final NoticeDAO noticeDAO;

    //목록
    public NoticeCriteriaDTO getNotices(int page){
        NoticeCriteriaDTO noticeCriteriaDTO = new NoticeCriteriaDTO();
        Criteria criteria = new Criteria(page, noticeDAO.getTotal());
        log.info(criteria.toString());

        noticeCriteriaDTO.setCriteria(criteria);
        noticeCriteriaDTO.setNoticeLists(noticeDAO.findAllNotice(criteria));
        return noticeCriteriaDTO;



    }
//  관리자 공지사항 목록
    public NoticeCriteriaDTO getAdminNotices(int page) {
        int total = noticeDAO.getTotal();
        Criteria criteria = new Criteria(page, total, 16, 10);

        List<NoticeCriteriaDTO> noticeListAdmin = noticeDAO.findNoticeCriteriaForAdmin(criteria);

        NoticeCriteriaDTO noticeCriteriaDTO = new NoticeCriteriaDTO();
        noticeCriteriaDTO.setCriteria(criteria);
        noticeCriteriaDTO.setNoticeListAdmin(noticeListAdmin);

        return noticeCriteriaDTO;
    }

//    관리자 공지사항 작성
    public Long insertNotice(Long memberId, String title, String content) {
        NoticeDTO noticeDTO = new NoticeDTO();
        noticeDTO.setNoticeTitle(title);
        noticeDTO.setNoticeContent(content);
        noticeDTO.setMemberId(memberId);

        noticeDAO.insert(noticeDTO);

        return noticeDTO.getId();
    }

}
