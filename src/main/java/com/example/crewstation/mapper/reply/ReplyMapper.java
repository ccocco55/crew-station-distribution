package com.example.crewstation.mapper.reply;

import com.example.crewstation.domain.reply.ReplyVO;
import com.example.crewstation.dto.reply.ReplyDTO;
import com.example.crewstation.util.Criteria;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ReplyMapper {
    public List<ReplyDTO> selectAllByPostId(@Param("postId") Long postId, @Param("criteria") Criteria criteria);

    public int selectAllCountByPostId(Long postId);

    public void insert(ReplyDTO replyDTO);

    public void update(ReplyVO replyVO);

    public void softDelete(Long id);


}
