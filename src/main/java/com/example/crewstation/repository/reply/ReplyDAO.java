package com.example.crewstation.repository.reply;


import com.example.crewstation.domain.reply.ReplyVO;
import com.example.crewstation.dto.reply.ReplyDTO;
import com.example.crewstation.mapper.reply.ReplyMapper;
import com.example.crewstation.util.Criteria;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Slf4j
@RequiredArgsConstructor
public class ReplyDAO {
    private final ReplyMapper replyMapper;

    public List<ReplyDTO> findAllByPostId(Long postId, Criteria criteria){
        return replyMapper.selectAllByPostId(postId,criteria);
    }
    public int findAllCountByPostId(Long postId){
        return replyMapper.selectAllCountByPostId(postId);
    }


    public void save(ReplyDTO replyDTO){
        replyMapper.insert(replyDTO);
    }

    public void update(ReplyVO replyVO){
        replyMapper.update(replyVO);
    }

    public void softDelete(Long id){
        replyMapper.softDelete(id);
    }
}
