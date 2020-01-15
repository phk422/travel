package com.phk.travel.dao;

import com.phk.travel.domain.Seller;

public interface SellerDao {
    /**
     * 根据sid查询Seller对象
     */
    Seller findSeller(int sid);
}
