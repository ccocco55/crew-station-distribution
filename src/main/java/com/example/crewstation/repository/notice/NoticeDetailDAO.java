package com.example.crewstation.repository.notice;

import com.example.crewstation.domain.notice.NoticeDetailVO;
import com.example.crewstation.mapper.notice.NoticeDetailMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class NoticeDetailDAO {
    private final NoticeDetailMapper noticeDetailMapper;

    public NoticeDetailVO findById(Long id) {
        return noticeDetailMapper.findById(id);
    }
}


