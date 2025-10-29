package com.example.crewstation.repository.guest;

import com.example.crewstation.domain.guest.GuestVO;
import com.example.crewstation.dto.guest.GuestDTO;
import com.example.crewstation.dto.guest.GuestOrderDetailDTO;
import com.example.crewstation.mapper.guest.GuestMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
@Slf4j
public class GuestDAO {
    private final GuestMapper guestMapper;

    //  게스트 회원 추가
    public void save(GuestVO guestVO) {
        guestMapper.insert(guestVO);
    }

//    게스트 로그인
    public Optional<GuestDTO> findGuest(GuestDTO guestDTO) {
        return guestMapper.select(guestDTO);
    }

    // 게스트 주문 상세 조회 (주문번호로 단건 조회)
    public Optional<GuestOrderDetailDTO> findOrderDetail(String guestOrderNumber) {
        return Optional.ofNullable(guestMapper.selectOrderDetails(guestOrderNumber));
    }

//   게스트 주문번호 조회
    public Optional<GuestDTO> selectGuestByOrderNumber(String guestOrderNumber) {
        return guestMapper.selectGuestByOrderName(guestOrderNumber);
    }

}
