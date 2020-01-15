package com.phk.travel.dao;

import com.phk.travel.domain.Route;

import java.util.List;

public interface RouteDao {

    /**
     * 根据cid查询总记录数
     */
    int findTotalCount(int cid,String rname);
    /**
     * 根据cid，start(开始页码)，pageSize查询
     */
    List<Route> findByPage(int cid, int start, int pageSize,String rname);
    /**
     * 根据rid查询Route对象
     */
    Route findOne(int rid);
}
