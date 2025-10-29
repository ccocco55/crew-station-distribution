package com.example.crewstation.post.file.tag;

import com.example.crewstation.domain.post.PostVO;
import com.example.crewstation.domain.post.file.tag.PostFileTagVO;
import com.example.crewstation.dto.post.file.tag.PostFileTagDTO;
import com.example.crewstation.dto.purchase.PurchaseDTO;
import com.example.crewstation.repository.post.PostDAO;
import com.example.crewstation.repository.post.file.tag.PostFileTagDAO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Slf4j
public class DaoTest {
    @Autowired
    private PostFileTagDAO postFileTagDAO;

    @Test
    @Transactional
    public void insert() {
        PostFileTagVO postFileTagVO = PostFileTagVO.builder()
                .postSectionFileId(1L)
                .tagLeft(10)
                .tagTop(10)
                .memberId(1L)
                .build();
        postFileTagDAO.save(postFileTagVO);
    }

    @Test
    public void testFindByFileId(){
        postFileTagDAO.findByFileId(73L).stream().map(PostFileTagDTO::toString).forEach(log::info);
    }
}
