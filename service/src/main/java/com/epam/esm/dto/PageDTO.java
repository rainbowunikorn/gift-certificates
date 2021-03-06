package com.epam.esm.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import java.util.List;

@Setter
@Getter
@EqualsAndHashCode(callSuper = false)
public class PageDTO<T> extends RepresentationModel<PageDTO<T>>{
    private static final int NUMB_FIRST_PAGE = 1;

    private final int pageNumber;
    private final int pageSize;
    private long totalPages;
    private final long totalElements;
    private boolean isFirst;
    private boolean isLast;
    private final List<T> content;

    public PageDTO(int pageNumber, int pageSize, long totalElements, List<T> content) {
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.totalPages = (totalElements / pageSize) + 1;
        this.totalElements = totalElements;
        this.setFirst(pageNumber == NUMB_FIRST_PAGE);
        this.setLast(pageNumber == totalPages);
        this.content = content;
    }
}

