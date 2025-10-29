package com.example.crewstation.service.notice;

import com.example.crewstation.domain.notice.NoticeDetailVO;
import com.example.crewstation.repository.notice.NoticeDetailDAO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NoticeDetailService {
    private final NoticeDetailDAO noticeDetailDAO;

    public NoticeDetailVO getDetail(Long id) {
        return noticeDetailDAO.findById(id);
    }
}
