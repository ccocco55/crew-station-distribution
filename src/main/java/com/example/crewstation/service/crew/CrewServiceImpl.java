package com.example.crewstation.service.crew;

import com.example.crewstation.aop.aspect.annotation.LogReturnStatus;
import com.example.crewstation.common.exception.PostNotFoundException;
import com.example.crewstation.dto.crew.CrewCriteriaDTO;
import com.example.crewstation.dto.crew.CrewDTO;
import com.example.crewstation.dto.post.PostDTO;
import com.example.crewstation.repository.crew.CrewDAO;
import com.example.crewstation.service.s3.S3Service;
import com.example.crewstation.util.Criteria;
import com.example.crewstation.util.Search;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CrewServiceImpl implements CrewService {

    private final CrewDAO crewDAO;
    private final CrewDTO crewDTO;
    private final S3Service s3Service;
    private final CrewTransactionService crewTransactionService;
    private final RedisTemplate<String, Object> redisTemplate;

    @Override
    @Transactional(rollbackFor = Exception.class)
    @LogReturnStatus
    public List<CrewDTO> getCrews() {
        List<CrewDTO> crews = (List<CrewDTO>) redisTemplate.opsForValue().get("crews");
        if (crews != null) {
            crews.forEach(crew -> {
                String filePath = crew.getFilePath();
                String presignedUrl = s3Service.getPreSignedUrl(filePath, Duration.ofMinutes(5));

                log.info("Crew ID={}, 원본 filePath={}, 발급된 presignedUrl={}",
                        crew.getId(), filePath, presignedUrl);
                crew.setFilePath(s3Service.getPreSignedUrl(crew.getFilePath(),
                        Duration.ofMinutes(5)));
            });
            return crews;
        }
        return crewTransactionService.getCrews();
    }

    @Override
    public CrewCriteriaDTO getSearchCrews(Search search) {
        CrewCriteriaDTO dto = new CrewCriteriaDTO();
        Search newSearch = new Search();
        int page = search.getPage();

        dto.setSearch(search);
        newSearch.setKeyword(search.getKeyword());

        int totalCount = crewDAO.countCrews(newSearch);
        Criteria criteria = new Criteria(page, totalCount, 4, 4);

        List<CrewDTO> crews = crewDAO.findCrews(criteria, newSearch);
        crews.forEach(crew -> {
            if (crew.getFilePath() != null) {
                crew.setFilePath(s3Service.getPreSignedUrl(crew.getFilePath(), Duration.ofMinutes(5)));
            }
        });

        criteria.setHasMore(crews.size() > criteria.getRowCount());
        if (criteria.isHasMore()) {
            crews.remove(crews.size() - 1);
        }

        dto.setCrewDTOs(crews);
        dto.setCriteria(criteria);
        dto.setTotalCount(totalCount);
        return dto;
    }
}
