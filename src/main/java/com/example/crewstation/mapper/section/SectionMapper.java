package com.example.crewstation.mapper.section;

import com.example.crewstation.domain.post.PostVO;
import com.example.crewstation.domain.post.section.PostSectionVO;
import com.example.crewstation.dto.post.section.SectionDTO;
import com.example.crewstation.dto.purchase.PurchaseDTO;
import com.example.crewstation.dto.purchase.PurchaseDetailDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SectionMapper {
//  상품글 섹션에 내용과 이미지 가져오기
    public List<SectionDTO> selectSectionsByPostId(Long postId);

//  섹션 작성하기
    public void insert(Object Object);
    //  다이어리 섹션 작성하기
    public void insertDiary(Object Object);
//  섹션 수정하기
    public void update(PostSectionVO postSectionVO);

//  섹션 삭제하기
    public void delete(Long id);

//  섹션에 이미지 개수 세기
    public int selectSectionFileCount(Long postId);
}
