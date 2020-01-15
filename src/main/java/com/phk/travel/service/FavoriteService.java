package com.phk.travel.service;

public interface FavoriteService {
    boolean isFavorite(int uid,String rid);

    void add(String rid, int uid);
}
