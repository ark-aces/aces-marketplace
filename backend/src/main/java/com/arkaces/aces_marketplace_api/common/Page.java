package com.arkaces.aces_marketplace_api.common;

import lombok.Data;

import java.util.List;

@Data
public class Page<T> {
    private Integer pageSize;
    private Integer page;
    private Integer totalItems;
    private List<T> items;
}
