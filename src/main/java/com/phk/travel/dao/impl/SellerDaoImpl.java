package com.phk.travel.dao.impl;

import com.phk.travel.dao.SellerDao;
import com.phk.travel.domain.Seller;
import com.phk.travel.util.JdbcUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

public class SellerDaoImpl implements SellerDao {
    private JdbcTemplate template=new JdbcTemplate(JdbcUtils.getDataSource());

    @Override
    public Seller findSeller(int sid) {
        String sql="select *from tab_seller where sid=?";
        Seller seller = template.queryForObject(sql, new BeanPropertyRowMapper<>(Seller.class), sid);
        return seller;
    }
}
