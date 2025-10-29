package com.example.crewstation.post.file.tag;

import com.example.crewstation.domain.post.PostVO;
import com.example.crewstation.domain.post.file.tag.PostFileTagVO;
import com.example.crewstation.dto.post.file.tag.PostFileTagDTO;
import com.example.crewstation.dto.purchase.PurchaseDTO;
import com.example.crewstation.mapper.post.PostMapper;
import com.example.crewstation.mapper.post.file.tag.PostFileTagMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Slf4j
public class MapperTest {
    @Autowired
    private PostFileTagMapper postFileTagMapper;

    @Test
    @Transactional
    public void insert() {
        PostFileTagVO postFileTagVO = PostFileTagVO.builder()
                .postSectionFileId(1L)
                .tagLeft(10)
                .tagTop(10)
                .memberId(1L)
                .build();
        postFileTagMapper.insert(postFileTagVO);
    }

    @Test
    public void tesSelectByFileId(){
        postFileTagMapper.selectByFileId(73L).stream().map(PostFileTagDTO::toString).forEach(log::info);
    }
}
