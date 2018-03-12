package com.arkaces.aces_marketplace_api.common;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class PageViewMapper {

    public <T1> PageView<T1> map(Page<T1> page, Class<T1> t1) {
        PageView<T1> pageView = new PageView<>();
        pageView.setItems(page.getContent());
        pageView.setPage(page.getNumber());
        pageView.setPageSize(page.getSize());
        pageView.setTotalItems(page.getTotalElements());
        return pageView;
    }

}
