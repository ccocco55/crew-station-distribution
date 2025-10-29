package com.example.crewstation.service.accompany;

import com.example.crewstation.aop.aspect.annotation.LogReturnStatus;
import com.example.crewstation.dto.accompany.AccompanyCriteriaDTO;
import com.example.crewstation.dto.accompany.AccompanyDTO;
import com.example.crewstation.repository.accompany.AccompanyDAO;
import com.example.crewstation.service.s3.S3Service;
import com.example.crewstation.util.Criteria;
import com.example.crewstation.util.DateUtils;
import com.example.crewstation.util.Search;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;


@Service
@RequiredArgsConstructor
@Slf4j
public class AccompanyServiceImpl implements AccompanyService {
    private final S3Service s3Service;
    private final AccompanyTransactionService accompanyTransactionService;
    private final RedisTemplate<String, Object> redisTemplate;
    private final AccompanyDAO accompanyDAO;
    private static final DateTimeFormatter DT_FMT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static final Map<String,String> ORDER_TYPE_MAP = Map.of(
            "최신순",   "vpc.created_datetime desc",
            "시작일순", "vpc.country_start_date asc"
    );

    @Override
    @Transactional(rollbackFor = Exception.class)
    @LogReturnStatus
    public List<AccompanyDTO> getAccompanies(int limit) {
        List<AccompanyDTO> accompanies = (List<AccompanyDTO>) redisTemplate.opsForValue().get("accompanies");
        if (accompanies != null) {
            accompanies.forEach(accompany -> {
                String filePath = accompany.getFilePath();
                String presignedUrl = s3Service.getPreSignedUrl(filePath, Duration.ofMinutes(5));

                log.info("Accompany ID={}, 원본 filePath={}, 발급된 presignedUrl={}",
                        accompany, filePath, presignedUrl);
                accompany.setFilePath(s3Service.getPreSignedUrl(accompany.getFilePath(),
                        Duration.ofMinutes(5)));
            });
            return accompanies;

        }
        return accompanyTransactionService.getAccompanies(limit);

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public AccompanyCriteriaDTO getSearchAccompanies(Search search) {
        AccompanyCriteriaDTO dto = new AccompanyCriteriaDTO();
        Search newSearch = new Search();
        int page = search.getPage();

        dto.setSearch(search);

        String orderType = search.getOrderType();
        String orderBy = ORDER_TYPE_MAP.getOrDefault(
                orderType == null ? "" : orderType,
                "vpc.created_datetime desc"
        );
        newSearch.setKeyword(search.getKeyword());
        newSearch.setOrderType(orderBy);

        int totalCount = accompanyDAO.countAccompanies(newSearch);

        Criteria criteria = new Criteria(page, totalCount, 4, 4);

        List<AccompanyDTO> accompanies = accompanyDAO.findAccompanies(newSearch, criteria);

        accompanies.forEach(accompany -> {
            if (accompany.getFilePath() != null) {
                accompany.setFilePath(s3Service.getPreSignedUrl(accompany.getFilePath(), Duration.ofMinutes(5)));
            }
        });

        criteria.setHasMore(accompanies.size() > criteria.getRowCount());
        if (criteria.isHasMore()) {
            accompanies.remove(accompanies.size() - 1);
        }

        dto.setAccompanyDTOs(accompanies);
        dto.setCriteria(criteria);
        dto.setTotalCount(totalCount);
        return dto;
    }

}
