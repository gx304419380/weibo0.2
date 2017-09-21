package com.weibo.dao;

import com.weibo.domain.User;
import com.weibo.util.DataSourceUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.ColumnListHandler;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UserDaoImpl implements UserDao {
    @Override
    public User getUser(User user) throws SQLException {
        QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
        String sql = "select * from user where username = ? and password = ?";
        User u = null;
        u = qr.query(sql, new BeanHandler<User>(User.class), user.getUsername(), user.getPassword());

        return u;
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

    @Override
    public List<Integer> getFollowId(User user) throws SQLException {
        QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
        String sql = "select fid from relationship where uid=?";
        List<Object> list = qr.query(sql,
                new ColumnListHandler(),
                user.getId());
        List<Integer> followIdList = new ArrayList<>();
        for (Object o : list) {
            followIdList.add((Integer) o);
        }
        return followIdList;
    }

    @Override
    public void followById(Integer uid, Integer fid) throws SQLException {
        QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
        String sql = "insert into relationship values(null, ?, ?)";
        qr.update(sql, uid, fid);
    }

    @Override
    public void unFollowById(Integer uid, Integer fid) throws SQLException {
        QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
        String sql = "delete from relationship where uid = ? and fid = ?";
        qr.update(sql, uid, fid);
    }

}
