package com.phk.travel.service.impl;

import com.phk.travel.dao.UserDao;
import com.phk.travel.dao.impl.UserDaoImpl;
import com.phk.travel.domain.User;
import com.phk.travel.service.UserService;
import com.phk.travel.util.MailUtils;
import com.phk.travel.util.UuidUtil;

public class UserServiceImpl implements UserService {
    private UserDao dao=new UserDaoImpl();

    /**
     * 注册用户
     * @param user
     * @return
     */
    @Override
    public boolean register(User user) {
        ///根据用户名查询用户信息
        User u=dao.findUserByName(user.getUsername());
        //判断用户是否存在
        if(u!=null){
            //用户名存在，注册失败
            return false;
        }
        //用户名不存在注册成功
        //保存用户信息
        //设置用户激活码，唯一
        user.setCode(UuidUtil.getUuid());
        //设置用户的初始激活状态
        user.setStatus("N");

        //激活邮件的发送
        //邮件正文
        String content="<a href='http://49.235.18.183:8080/travel/user/active?code="+user.getCode()+"'>点击激活【坤哥旅游网】</a>";
        MailUtils.sendMail(user.getEmail(),content,"激活邮件");
        dao.save(user);
        return true;
    }

    /**
     * 激活激活码
     * @param code
     * @return
     */
    @Override
    public boolean active(String code) {
        //根据code查询用户
       User user= dao.findUserByCode(code);
       if(user!=null){
           //用户存在，修改Status
           dao.updateStatus(user);
           return true;
       }
        //用户不存在
        return false;
    }

    /**
     * 登录
     * @param u
     * @return
     */
    @Override
    public User login(User u) {
        return dao.findUserByUsernameAndPassword(u.getUsername(),u.getPassword());
    }
}
