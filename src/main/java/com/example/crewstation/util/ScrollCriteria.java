package com.example.crewstation.util;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ScrollCriteria {
    private int page;
    private int size;
    private int offset;
    private int total;
    private boolean hasMore;

    // 생성자
    public ScrollCriteria(int page, int size) {
        this.page = Math.max(1, page);
        this.size = size;
        this.offset = (this.page - 1) * size;
    }

    // total 값 세팅 시 hasMore 자동 계산
    public void setTotal(int total) {
        this.total = total;
        this.hasMore = (page * size) < total;
    }

}
