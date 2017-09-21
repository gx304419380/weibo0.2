package com.weibo.dao;

import com.weibo.domain.Blah;
import com.weibo.domain.User;

import java.sql.SQLException;
import java.util.List;

public interface BlahDao {
    List<Blah> getBlahs(User user) throws SQLException;

    void save(Blah blah) throws SQLException;

    List<Blah> getBlahs(User user, int page, int count) throws SQLException;

    int getTotalCount(User user) throws SQLException;

    void deleteById(int blahId) throws SQLException;

    List<Blah> getAllBlahs(int page, int count) throws SQLException;

    int getAllBlahsCount() throws SQLException;
}

