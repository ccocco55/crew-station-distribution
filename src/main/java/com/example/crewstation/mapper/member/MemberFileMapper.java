package com.example.crewstation.mapper.member;

import com.example.crewstation.domain.file.member.MemberFileVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberFileMapper {
    //    회원가입 시 프로필  insert
    public void insert(MemberFileVO memberFileVO);

}
