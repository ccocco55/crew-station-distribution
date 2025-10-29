package com.example.crewstation.repository.section;

import com.example.crewstation.domain.post.section.PostSectionVO;
import com.example.crewstation.dto.post.section.SectionDTO;
import com.example.crewstation.dto.purchase.PurchaseDTO;
import com.example.crewstation.dto.purchase.PurchaseDetailDTO;
import com.example.crewstation.mapper.section.SectionMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Slf4j
@RequiredArgsConstructor
public class SectionDAO {
    private final SectionMapper sectionMapper;

    //  상품글 섹션에 내용과 이미지 가져오기
    public List<SectionDTO> findSectionsByPostId(Long postId){
        return sectionMapper.selectSectionsByPostId(postId);
    }
//    섹션 작성하기
    public void save(Object Object) {
        sectionMapper.insert(Object);
    }
// 다이어리 섹션 저장하기
    public void saveDiary(Object Object){
        sectionMapper.insertDiary(Object);
    }

    //  섹션 수정하기
    public void update(PostSectionVO postSectionVO){
        sectionMapper.update(postSectionVO);
    }
//  섹션 삭제하기
    public void delete(Long id){
        sectionMapper.delete(id);
    }

    //  섹션에 이미지 개수 세기
    public int findSectionFileCount(Long postId){
        return sectionMapper.selectSectionFileCount(postId);
    }
}
