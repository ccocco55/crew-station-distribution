package com.example.crewstation.reply;

import com.example.crewstation.domain.post.file.tag.PostFileTagVO;
import com.example.crewstation.dto.post.file.tag.PostFileTagDTO;
import com.example.crewstation.dto.reply.ReplyDTO;
import com.example.crewstation.mapper.post.file.tag.PostFileTagMapper;
import com.example.crewstation.mapper.reply.ReplyMapper;
import com.example.crewstation.util.Criteria;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
@Slf4j
public class MapperTest {
    @Autowired
    private ReplyMapper replyMapper;


    @Test
    public void tesSelectAllByPostId(){
        Criteria criteria = new Criteria(1,10);
        List<ReplyDTO> replyDTOS = replyMapper.selectAllByPostId(1L,criteria);
        Assertions.assertThat(replyDTOS.size()).isEqualTo(0);
    }

    @Test
    public void testSelectAllCountByPostId(){

        log.info("{}",replyMapper.selectAllCountByPostId(1L));
    }
}
