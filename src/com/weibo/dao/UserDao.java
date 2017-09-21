package com.weibo.dao;

import com.weibo.domain.User;

import java.sql.SQLException;
import java.util.List;

public interface UserDao {
    User getUser(User user) throws SQLException;

    User getUserByName(String username) throws SQLException;

    void save(User user) throws SQLException;

    List<Integer> getFollowId(User user) throws SQLException;

    void followById(Integer uid, Integer fid) throws SQLException;

    void unFollowById(Integer uid, Integer fid) throws SQLException;
}
