package com.phk.travel.dao.impl;

import com.phk.travel.dao.UserDao;
import com.phk.travel.domain.User;
import com.phk.travel.util.JdbcUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

public class UserDaoImpl implements UserDao {
    private JdbcTemplate template = new JdbcTemplate(JdbcUtils.getDataSource());

    @Override
    public User findUserByName(String username) {
        User user = null;
        try {
            String sql = "select *from tab_user where username=?";
            //该方法没有查询到数据会报异常，所以要进行异常处理
            user = template.queryForObject(sql, new BeanPropertyRowMapper<>(User.class), username);
        } catch (DataAccessException e) {

        }
        return user;
    }

    @Override
    public void save(User user) {
        String sql = "insert into tab_user(username,password,name,birthday,sex,telephone,email,status,code) values(?,?,?,?,?,?,?,?,?)";
        template.update(sql, user.getUsername(), user.getPassword(), user.getName(),
                user.getBirthday(), user.getSex(), user.getTelephone(), user.getEmail(),user.getStatus(),user.getCode()
        );
    }

    @Override
    public User findUserByCode(String code) {
        User user = null;
        try {
            String sql="select *from tab_user where code=?";
            user = template.queryForObject(sql, new BeanPropertyRowMapper<>(User.class), code);
        } catch (DataAccessException e) {
            //e.printStackTrace();//记录日志
        }
        return user;
    }

    @Override
    public void updateStatus(User user) {
        String sql="update tab_user set status=? where code=?";
        template.update(sql,"Y",user.getCode());
    }

    @Override
    public User findUserByUsernameAndPassword(String username, String password) {
        User user = null;
        try {
            String sql="select *from tab_user where username=? and password=? ";
            user = template.queryForObject(sql, new BeanPropertyRowMapper<>(User.class), username, password);
        } catch (DataAccessException e) {
            //e.printStackTrace();?
        }
        return user;
    }
}
