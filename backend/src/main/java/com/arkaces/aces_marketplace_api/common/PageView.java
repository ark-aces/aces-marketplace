package com.arkaces.aces_marketplace_api.common;

import lombok.Data;

import java.util.List;

@Data
public class PageView<T> {
    private Integer pageSize;
    private Integer page;
    private Long totalItems;
    private List<T> items;
}
