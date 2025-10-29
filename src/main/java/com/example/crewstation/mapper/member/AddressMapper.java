package com.example.crewstation.mapper.member;

import com.example.crewstation.domain.address.AddressVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AddressMapper {
    //    회원가입시 주소
    public void insert(AddressVO addressVO);


}
