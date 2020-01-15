package com.phk.travel.web.servlet;

import com.phk.travel.domain.PageBean;
import com.phk.travel.domain.Route;
import com.phk.travel.domain.User;
import com.phk.travel.service.FavoriteService;
import com.phk.travel.service.RouteService;
import com.phk.travel.service.impl.FavoriteServiceImpl;
import com.phk.travel.service.impl.RouteServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/route/*")
public class RouteServlet extends BaseServlet {
    private RouteService service = new RouteServiceImpl();
    private FavoriteService favoriteService = new FavoriteServiceImpl();

    /**
     * 分页查询
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void pageQuery(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取参数
        String cidStr = request.getParameter("cid");
        String currentPageStr = request.getParameter("currentPage");
        String pageSizeStr = request.getParameter("pageSize");
        String rname = request.getParameter("rname");
        //解决get请求乱码
//        if (rname != null) {
//            rname = new String(rname.getBytes("iso-8859-1"), "UTF-8");
        System.out.println(rname);
        //}
        //处理参数
        int cid = 0;//路线的id
        if (cidStr != null && cidStr.length() > 0 && !"null".equals(cidStr)) {
            cid = Integer.parseInt(cidStr);
        }

        int currentPage = 1;//当前页码默认为1
        if (currentPageStr != null && currentPageStr.length() > 0) {
            currentPage = Integer.parseInt(currentPageStr);
        }

        int pageSize = 5;//当前页显示的总记录数默认为5
        if (pageSizeStr != null && pageSizeStr.length() > 0) {
            pageSize = Integer.parseInt(pageSizeStr);
        }

        if (rname.equals("null")) {
            rname = "";
        }
        //调用service查询pageBean
        PageBean<Route> pageBean = service.pageQuery(cid, currentPage, pageSize, rname);

        //序列化
        writeValue(pageBean, response);
    }

    /**
     * 根据rid,查询Route对象
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void findRoute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取rid
        String rid = request.getParameter("rid");
        //调用service查询Route
        Route route = service.findOne(Integer.parseInt(rid));
        //序列化route
        writeValue(route, response);
    }


    /**
     * 判断用户收否收藏
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void isFavorite(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取rid
        String rid = request.getParameter("rid");
        //从session中获取user
        User user = (User) request.getSession().getAttribute("user");
        //判段用户是否存在
        int uid = 0;//用户id
        if (user == null) {
            //用户不存在
            uid = 0;
        } else {
            uid = user.getUid();
        }
        //调用service查询
        boolean flag = favoriteService.isFavorite(uid, rid);
        writeValue(flag, response);

    }

    /**
     * 收藏线路
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void addFavorite(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取rid
        String rid = request.getParameter("rid");
        //从session中获取用户
        User user = (User) request.getSession().getAttribute("user");
        //判段用户是否存在
        int uid = 0;//用户id
        if (user == null) {
            //用户不存在
            writeValue(false, response);
        } else {
            //用户存在
            uid = user.getUid();
            favoriteService.add(rid, uid);//添加数据
            writeValue(true, response);
        }
    }
}
