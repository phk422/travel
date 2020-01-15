package com.phk.travel.dao;

import com.phk.travel.domain.Category;

import java.util.List;

public interface CategoryDao {
    List<Category> findAll();
}
