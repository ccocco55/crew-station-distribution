package com.example.crewstation.service.purchase;


import com.example.crewstation.common.exception.PurchaseNotFoundException;
import com.example.crewstation.dto.post.PostDTO;
import com.example.crewstation.dto.post.section.SectionDTO;
import com.example.crewstation.dto.purchase.PurchaseDTO;
import com.example.crewstation.repository.purchase.PurchaseDAO;
import com.example.crewstation.repository.section.SectionDAO;
import com.example.crewstation.service.s3.S3Service;
import com.example.crewstation.util.DateUtils;
import com.example.crewstation.util.PriceUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PurchaseTransactionService {
    private final PurchaseDAO purchaseDAO;
    private final SectionDAO sectionDAO;
    private final RedisTemplate<String, PurchaseDTO> purchaseRedisTemplate;
    private final S3Service s3Service;



    @Transactional(rollbackFor = Exception.class)
    public PurchaseDTO getPurchaseFromDbAndCache(Long id) {
        PurchaseDTO purchaseDTO = purchaseDAO.findByPostId(id).orElseThrow(PurchaseNotFoundException::new);
        purchaseDAO.increaseReadCount(id);
        purchaseDTO.setPurchaseProductPrice(PriceUtils.formatMoney(purchaseDTO.getPrice()));
        purchaseDTO.setLimitDateTime(DateUtils.calcLimitDateTime(purchaseDTO.getCreatedDatetime(), purchaseDTO.getPurchaseLimitTime()));
        purchaseRedisTemplate.opsForValue().set("purchase::purchases_" + id, purchaseDTO, Duration.ofMinutes(10));
        List<SectionDTO> sections = sectionDAO.findSectionsByPostId(id);
        if(purchaseDTO.getFilePath() != null){
            purchaseDTO.setFilePath(s3Service.getPreSignedUrl(purchaseDTO.getFilePath(), Duration.ofMinutes(5)));
        }
        sections.forEach(section -> {
            section.setFilePath(s3Service.getPreSignedUrl(section.getFilePath(), Duration.ofMinutes(5)));
        });
        sections.sort(Comparator.comparing(SectionDTO::getImageType));
        purchaseDTO.setSections(sections);

        return purchaseDTO;
    }
}
