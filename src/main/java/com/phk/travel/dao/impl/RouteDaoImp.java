package com.phk.travel.dao.impl;

import com.phk.travel.dao.RouteDao;
import com.phk.travel.domain.Route;
import com.phk.travel.util.JdbcUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;

public class RouteDaoImp implements RouteDao {
    private JdbcTemplate template = new JdbcTemplate(JdbcUtils.getDataSource());

    @Override
    public int findTotalCount(int cid, String rname) {
//        String sql="select count(*) from tab_route where cid=?";
        //定义sql模板
        String sql = "select count(*) from tab_route where 1=1 ";
        StringBuilder sb = new StringBuilder(sql);
        List params = new ArrayList();//存放参数的集合
        //判断是否有参数
        if (cid != 0) {
            sb.append(" and cid=? ");
            params.add(cid);
        }
        if (rname != null && rname.length() > 0) {
            sb.append(" and rname like ? ");
            params.add("%" + rname + "%");
        }
        sql = sb.toString();

        return template.queryForObject(sql, Integer.class, params.toArray());

    }

    @Override
    public List<Route> findByPage(int cid, int start, int pageSize, String rname) {
//        String sql="select *from tab_route where cid=? limit ?,?";

        //定义sql模板
        String sql = " select *from tab_route where 1=1 ";
        StringBuilder sb = new StringBuilder(sql);
        List params = new ArrayList();//存放参数的集合
        //判断是否有参数
        if (cid != 0) {
            sb.append(" and cid=? ");
            params.add(cid);
        }
        if (rname != null && rname.length() > 0) {
            sb.append(" and rname like ? ");
            params.add("%" + rname + "%");
        }
        sb.append(" limit ?,? ");
        params.add(start);
        params.add(pageSize);
        sql = sb.toString();
        return template.query(sql, new BeanPropertyRowMapper<>(Route.class), params.toArray());


    }

    @Override
    public Route findOne(int rid) {
        String sql = "select *from tab_route where rid=?";

        return template.queryForObject(sql,new BeanPropertyRowMapper<>(Route.class),rid);
    }
}
