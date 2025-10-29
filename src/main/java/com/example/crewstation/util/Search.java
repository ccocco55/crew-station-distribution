package com.example.crewstation.util;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@ToString
public class Search {
    private String category;
    private String keyword;
    private int page;
    private String orderType;
    private List<String> categories;

}
