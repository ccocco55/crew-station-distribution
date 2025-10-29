package com.example.crewstation.dto.notice;

import com.example.crewstation.domain.notice.NoticeVO;
import com.example.crewstation.util.Criteria;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

@Getter @Setter
@ToString
public class NoticeCriteriaDTO {
    private List<NoticeVO> noticeLists;
    private Criteria criteria;
    private Long id;
    private String title;
    private String content;
    private Long memberId;
    private String writerName;
    private String createdMd;
    private LocalDateTime createdDatetime;
    private Integer viewCount;
    private List<NoticeCriteriaDTO> noticeListAdmin;
}
