package com.phk.travel.service;

import com.phk.travel.domain.PageBean;
import com.phk.travel.domain.Route;

public interface RouteService {
    PageBean<Route> pageQuery(int cid,int currentPage,int pageSize,String rname);
    /**
     * 根据rid查询Route对象
     */
    Route findOne(int rid);
}
