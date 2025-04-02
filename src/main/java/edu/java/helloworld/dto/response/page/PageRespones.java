package edu.java.helloworld.dto.response.page;

import java.io.Serializable;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PageRespones<T> implements Serializable {
    private int pageNo;
    private int pageSize;
    private long totalPage;
    private T item;
}
