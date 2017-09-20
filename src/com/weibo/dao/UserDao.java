package com.weibo.dao;

import com.weibo.domain.User;

import java.sql.SQLException;

public interface UserDao {
    User getUser(User user) throws SQLException;

    User getUserByName(String username) throws SQLException;

    void save(User user) throws SQLException;
}
