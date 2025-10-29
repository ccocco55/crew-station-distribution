package com.example.crewstation.dto.member;

import com.example.crewstation.dto.country.CountryStatics;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class MemberAdminStatics {
    private List<MemberStatics> monthlyJoins;
    private int todayJoin;
    private int totalCrewCount;
    private int totalMemberCount;
    private List<CountryStatics> popularCountries;

}
