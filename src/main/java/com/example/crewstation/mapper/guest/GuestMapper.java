    package com.example.crewstation.mapper.guest;

    import com.example.crewstation.domain.guest.GuestVO;
    import com.example.crewstation.dto.guest.GuestDTO;
    import com.example.crewstation.dto.guest.GuestOrderDetailDTO;
    import org.apache.ibatis.annotations.Mapper;

    import java.util.Optional;

    @Mapper
    public interface GuestMapper {
    //  게스트 회원 추가
        public void insert(GuestVO guestVO);

    //    게스트 로그인
        public Optional<GuestDTO> select(GuestDTO guestDTO);

    //  게스트 조회
        public Optional<GuestDTO> selectGuest(Long GuestId);

    //  상세조회
        public GuestOrderDetailDTO selectOrderDetails(String guestOrderNumber);


//        주문번호로 조회
        public Optional<GuestDTO> selectGuestByOrderName (String guestOrderName);

    }
