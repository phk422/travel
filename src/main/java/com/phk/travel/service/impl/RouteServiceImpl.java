package com.phk.travel.service.impl;

import com.phk.travel.dao.FavoriteDao;
import com.phk.travel.dao.RouteDao;
import com.phk.travel.dao.RouteImgDao;
import com.phk.travel.dao.SellerDao;
import com.phk.travel.dao.impl.FavoriteDaoImpl;
import com.phk.travel.dao.impl.RouteDaoImp;
import com.phk.travel.dao.impl.RouteImgDaoimpl;
import com.phk.travel.dao.impl.SellerDaoImpl;
import com.phk.travel.domain.*;
import com.phk.travel.service.RouteService;

import java.util.List;

public class RouteServiceImpl implements RouteService {
    private RouteDao dao = new RouteDaoImp();
    private RouteImgDao routeImgDao=new RouteImgDaoimpl();
    private SellerDao sellerDao=new SellerDaoImpl();
    private FavoriteDao favoriteDao=new FavoriteDaoImpl();
    @Override
    public PageBean<Route> pageQuery(int cid, int currentPage, int pageSize,String rname) {
        //查询总记录数
        int totalCount = dao.findTotalCount(cid,rname);
        PageBean<Route> pageBean = new PageBean<Route>();
        pageBean.setPageSize(pageSize);
        pageBean.setCurrentPage(currentPage);
        pageBean.setTotalCount(totalCount);
        //根据总记录数计算总页数
        int totalPage = totalCount % pageSize == 0 ? totalCount / pageSize : totalCount / pageSize + 1;
        pageBean.setTotalPage(totalPage);
        //计算开始页码
        int start=(currentPage-1)*pageSize;
        //查询List
        List<Route> list = dao.findByPage(cid, start, pageSize,rname);
        pageBean.setList(list);
        return pageBean;
    }

    @Override
    public Route findOne(int rid) {
        //调用dao查询Route对象
        Route route = dao.findOne(rid);
        //调用routeImgDao查询RouteImg对象
        List<RouteImg> routeImgs = routeImgDao.findRouteImg(route.getRid());
        //放入Route对象
        route.setRouteImgList(routeImgs);
        //调用sellerDao查询seller对象
        Seller seller = sellerDao.findSeller(route.getSid());
        //放入Route对象
        route.setSeller(seller);

        //查询线路的收藏次数Count
        int count=favoriteDao.findCountByRid(route.getRid());
        route.setCount(count);
        return route;
    }
}
