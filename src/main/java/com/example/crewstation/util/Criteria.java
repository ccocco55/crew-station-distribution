package com.example.crewstation.util;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter
@ToString
public class Criteria {
    private int page;
    private int pageCount;
    private int startPage;
    private int endPage;
    private int rowCount;
    private int realEnd;
    private int offset;
    private boolean hasNextPage;
    private boolean hasPreviousPage;
    private boolean hasMore;
    private int count;
    private int total;

    public Criteria(int page, int total) {
        this.total = total;
        rowCount = 10;
        pageCount = 10;
        count = rowCount + 1;
        this.page = Math.max(1, page);
        endPage = (int)(Math.ceil(this.page / (double)pageCount) * pageCount);
        startPage = endPage - pageCount + 1;
        realEnd = (int)(Math.ceil(total / (double)rowCount));
        endPage = Math.min(realEnd, endPage);
        endPage = Math.max(1, endPage);
        offset = (this.page - 1) * rowCount;
        hasNextPage = endPage < realEnd;
        hasPreviousPage = startPage > 1;
    }


    public Criteria(int page, int total,int rowCount ,int pageCount) {
        this.total = total;
        this.rowCount = rowCount;
        this.pageCount = pageCount;
        count = rowCount + 1;
        this.page = Math.max(1, page);
        endPage = (int)(Math.ceil(this.page / (double)this.pageCount) * this.pageCount);
        startPage = endPage - this.pageCount + 1;
        realEnd = (int)(Math.ceil(total / (double)rowCount));
        endPage = Math.min(realEnd, endPage);
        endPage = Math.max(1, endPage);
        offset = (this.page - 1) * this.rowCount;
        hasNextPage = endPage < realEnd;
        hasPreviousPage = startPage > 1;
    }
}
