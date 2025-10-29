package com.example.crewstation.repository.crew;

import com.example.crewstation.dto.crew.CrewDTO;
import com.example.crewstation.mapper.crew.CrewMapper;
import com.example.crewstation.util.Criteria;
import com.example.crewstation.util.Search;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CrewDAO {

    private final CrewMapper crewMapper;

//    크루 목록
    public List<CrewDTO> getCrews() {

        return crewMapper.getCrews();
    }

//    크루 목록 개수
    public int countCrews(Search search) {
        return crewMapper.countCrews(search);
    }

//    크루 검색 목록
    public List<CrewDTO> findCrews(Criteria criteria, Search search) {
        return crewMapper.findCrews(criteria, search);
    }

//   크루 총 갯수
    public int selectTotalCrewCount() {
        return crewMapper.selectTotalCrewCount();
    }

}
