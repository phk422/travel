package com.phk.travel.service.impl;

import com.phk.travel.dao.CategoryDao;
import com.phk.travel.dao.impl.CategoryDaoImpl;
import com.phk.travel.domain.Category;
import com.phk.travel.service.CategoryService;
import com.phk.travel.util.JedisUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Tuple;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class CategoryServiceImpl implements CategoryService {
    private CategoryDao dao=new CategoryDaoImpl();

    @Override
    public List<Category> findAll() {
        //获取jedis客户端
        Jedis jedis = JedisUtil.getJedis();
       // Set<String> categroy = jedis.zrange("categroy", 0, -1);
        //查询sortedsetde分数
        Set<Tuple> categroy = jedis.zrangeWithScores("categroy", 0, -1);
        List<Category> list=null;
        if (categroy==null||categroy.size()==0){
            System.out.println("查询数据库。。。");
            //没有数据，查询数据库
            list = dao.findAll();
            //存入redis
            for (int i = 0; i < list.size(); i++) {
                jedis.zadd("categroy",list.get(i).getCid(),list.get(i).getCname());
            }
        }else {
            System.out.println("查询缓存。。。。");
            //将categroy存入集合
            list=new ArrayList<Category>();
            for (Tuple tuple : categroy) {
                Category c = new Category();
                c.setCname(tuple.getElement());
                c.setCid((int) tuple.getScore());
                list.add(c);
            }
        }
        return list;
    }
}
