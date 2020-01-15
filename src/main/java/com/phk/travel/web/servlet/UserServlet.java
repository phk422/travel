package com.phk.travel.web.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.phk.travel.domain.ResultInfo;
import com.phk.travel.domain.User;
import com.phk.travel.service.UserService;
import com.phk.travel.service.impl.UserServiceImpl;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

@WebServlet("/user/*")
public class UserServlet extends BaseServlet {
    private UserService service=new UserServiceImpl();

    /**
     * 注册功能
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void register(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取验证码
        String check = request.getParameter("check");
        //获取session
        HttpSession session = request.getSession();
        String checkcode_server = (String) session.getAttribute("CHECKCODE_SERVER");
        session.removeAttribute("CHECKCODE_SERVER");//保证验证码只能使用一次
        if (checkcode_server == null || !checkcode_server.equalsIgnoreCase(check)) {
            //验证码错误
            ResultInfo info = new ResultInfo();//用于封装后端返回前端数据对象
            info.setFlag(false);
            info.setErrorMsg("验证码错误！");
           /* ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(info);
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().write(json);*/
           writeValue(info,response);
            return;
        }


        //获取数据
        Map<String, String[]> map = request.getParameterMap();

        //封装实体类
        User user = new User();
        try {
            BeanUtils.populate(user, map);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        //调用service注册
        //UserService service = new UserServiceImpl();
        boolean flag = service.register(user);
        ResultInfo info = new ResultInfo();//用于封装后端返回前端数据对象
        //响应结果
        if (flag) {
            //注册成功
            info.setFlag(true);
        } else {
            //注册失败
            info.setFlag(false);
            info.setErrorMsg("注册失败,用户名已存在！");
        }
       /* //转换为json数据
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(info);

        //响应
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(json);*/
       writeValue(info,response);
    }

    /**
     * 登录功能
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取参数
        Map<String, String[]> map = request.getParameterMap();
        //获取验证码
        String check = request.getParameter("check");

        HttpSession session = request.getSession();
        ResultInfo info = new ResultInfo();
        //获取session中的验证码
        String checkcode_server = (String) session.getAttribute("CHECKCODE_SERVER");
        session.removeAttribute("CHECKCODE_SERVER");//防止重复使用
        //判断验证码
        if (checkcode_server == null || !checkcode_server.equalsIgnoreCase(check)) {
            //验证码错误
            info.setFlag(false);
            info.setErrorMsg("验证码错误！");
            /*ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(info);
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().write(json);*/
            writeValue(info,response);
            return;//后面的代码不执行了
        }

        //封装用户
        User u = new User();
        try {
            BeanUtils.populate(u, map);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        //调用service查询用户是否存在
       // UserService service = new UserServiceImpl();
        User user = service.login(u);
        //判断用户是否存在
        if (user != null) {
            //用户存在，判断激活码是否激活
            if ("Y".equals(user.getStatus())) {
                //用户已激活，登录成功，跳转登陆界面
                info.setFlag(true);
                //将用户对象存入session中
                session.setAttribute("user", user);
            } else {
                //用户未激活
                info.setFlag(false);
                info.setErrorMsg("用户账号未激活，请先激活再登录！");
            }
        } else {
            //用户不存在(密码错误)，给出提示信息
            info.setFlag(false);
            info.setErrorMsg("用户名或密码错误！");
        }

        /*ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(info);
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(json);*/
        writeValue(info,response);
    }

    /**
     * 查找一个对象
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void findOne(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取session中的user对象
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

       /* response.setContentType("application/json;charset=utf-8");
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(response.getOutputStream(), user);*/
       writeValue(user,response);
    }

    /**
     * 退出登录
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void exit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /*//获取session对象
        HttpSession session = request.getSession();
        //删除user
        session.removeAttribute("user");*/

        //直接销毁session
        request.getSession().invalidate();
        //跳转登录界面
        response.sendRedirect(request.getContextPath() + "/login.html");
    }

    /**
     * 激活功能
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void active(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取激活码
        String code = request.getParameter("code");
        if(code!=null){
            //调用service激活激活码
            //UserService service=new UserServiceImpl();
            boolean flag=service.active(code);
            String msg=null;
            if(flag){
                //激活成功
                msg="激活成功，点击<a href='http://49.235.18.183:8080/travel/login.html'>登录</a>";
            }else {
                //激活失败
                msg="激活失败，请联系管理员!";
            }
            response.setContentType("text/html;charset=utf-8");
            String s = writeValueAsString(msg);
            response.getWriter().write(s);
        }
    }

}
