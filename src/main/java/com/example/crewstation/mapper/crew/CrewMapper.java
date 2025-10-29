package com.example.crewstation.mapper.crew;

import com.example.crewstation.dto.crew.CrewDTO;
import com.example.crewstation.util.Criteria;
import com.example.crewstation.util.Search;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


@Mapper
public interface CrewMapper {
//    크루 정보 조회
    public List<CrewDTO> getCrews();

//  크루 목록 개수
    public int countCrews(@Param("search") Search search);

//    크루 검색 목록
    public List<CrewDTO> findCrews(@Param("criteria") Criteria criteria,
                            @Param("search") Search search);

//    크루 총 갯수
    public int selectTotalCrewCount();
}
