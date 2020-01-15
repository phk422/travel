package com.phk.travel.dao.impl;

import com.phk.travel.dao.RouteImgDao;
import com.phk.travel.domain.RouteImg;
import com.phk.travel.util.JdbcUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class RouteImgDaoimpl implements RouteImgDao {
    private JdbcTemplate template=new JdbcTemplate(JdbcUtils.getDataSource());
    @Override
    public List<RouteImg> findRouteImg(int rid) {
        String sql="select *from tab_route_img where rid=?";
        return template.query(sql, new BeanPropertyRowMapper<>(RouteImg.class), rid);
    }
}
