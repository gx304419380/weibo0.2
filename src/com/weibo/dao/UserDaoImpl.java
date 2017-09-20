package com.weibo.dao;

import com.weibo.domain.User;
import com.weibo.util.DataSourceUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import java.sql.SQLException;
import java.util.Date;

public class UserDaoImpl implements UserDao {
    @Override
    public User getUser(User user) throws SQLException {
        QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
        String sql = "select * from user where username = ? and password = ?";
        User user1 = null;
        user1 = qr.query(sql, new BeanHandler<User>(User.class), user.getUsername(), user.getPassword());

        return user1;
    }

    @Override
    public User getUserByName(String username) throws SQLException {
        QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
        String sql = "select * from user where username = ?";
        User user1 = null;
        user1 = qr.query(sql, new BeanHandler<User>(User.class), username);

        return user1;
    }

    @Override
    public void save(User user) throws SQLException {
        QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
        String sql = "insert into user values(null, ?, ?, ?, ?, ?)";
        qr.update(sql,
                user.getUsername(),
                user.getPassword(),
                user.getEmail(),
                new Date(),
                user.getNickname());
    }
}
