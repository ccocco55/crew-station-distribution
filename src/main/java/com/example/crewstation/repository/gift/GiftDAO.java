package com.example.crewstation.repository.gift;

import com.example.crewstation.dto.gift.GiftDTO;
import com.example.crewstation.mapper.gift.GiftMapper;
import com.example.crewstation.util.Criteria;
import com.example.crewstation.util.Search;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class GiftDAO {
    private final GiftMapper giftMapper;

//    기프트 목록 (메인)
    public List<GiftDTO> getMainGifts(int limit) {
        return giftMapper.getGift(limit);
    }

//    기프트 목록 개수
    public int countGifts(Search search) {
        return giftMapper.countGifts(search);
    }

//    기프트 검색 목록
    public List<GiftDTO> findGifts(Criteria criteria, Search search) {
        return giftMapper.findGifts(criteria, search);
    }

}

