package com.phk.travel.service.impl;

import com.phk.travel.dao.FavoriteDao;
import com.phk.travel.dao.impl.FavoriteDaoImpl;
import com.phk.travel.domain.Favorite;
import com.phk.travel.service.FavoriteService;

public class FavoriteServiceImpl implements FavoriteService {
    private FavoriteDao favoriteDao=new FavoriteDaoImpl();

    /**
     * 判断用户是否收藏
     * @param uid
     * @param rid
     * @return
     */
    @Override
    public boolean isFavorite(int uid, String rid) {
        Favorite favorite = favoriteDao.findByUidAndRid(uid, Integer.parseInt(rid));
        return favorite!=null;
    }

    /**
     * 添加收藏
     * @param rid
     * @param uid
     */
    @Override
    public void add(String rid, int uid) {
        favoriteDao.add(Integer.parseInt(rid),uid);
    }
}
