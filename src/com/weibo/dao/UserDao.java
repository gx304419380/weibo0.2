package com.weibo.dao;

import com.weibo.domain.User;

import java.sql.SQLException;
import java.util.List;
import java.util.Set;

public interface UserDao {
    User getUser(User user) throws SQLException;

    User getUserByName(String username) throws SQLException;

    void save(User user) throws SQLException;

    List<Integer> getFollowIdList(User user) throws SQLException;

    void followById(Integer uid, Integer fid) throws SQLException;

    void unFollowById(Integer uid, Integer fid) throws SQLException;

    Set<Integer> getLikeBidSet(User user) throws SQLException;
}
