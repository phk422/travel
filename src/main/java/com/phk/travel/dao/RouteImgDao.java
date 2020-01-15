package com.phk.travel.dao;

import com.phk.travel.domain.RouteImg;

import java.util.List;

public interface RouteImgDao {
    /**
     * 根据rid查询RouteImg对象
     */
    List<RouteImg> findRouteImg(int rid);
}
