package com.example.crewstation.mapper.notice;

import com.example.crewstation.domain.notice.NoticeDetailVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface NoticeDetailMapper {
    NoticeDetailVO findById(Long id);
}
