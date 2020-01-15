package com.phk.travel.dao;

import com.phk.travel.domain.Favorite;

public interface FavoriteDao {
    /**
     * 根据uid和rid查询Favorite对象
     * @param uid
     * @param rid
     * @return
     */
    Favorite findByUidAndRid(int uid,int rid);

    /**
     * 根据Rid查询收藏次数
     * @param rid
     * @return
     */
    int findCountByRid(int rid);

    /**
     * 添加收藏
     * @param rid
     * @param uid
     */
    void add(int rid, int uid);
}
