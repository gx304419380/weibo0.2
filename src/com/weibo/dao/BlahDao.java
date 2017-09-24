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

    List<Blah> getResultBlahs(String keywords, int page, int count) throws SQLException;

    int getResultBlahsCount(String keywords) throws SQLException;

    List<Blah> getCircleBlahs(User user, int page, int count) throws SQLException;

    Blah getBlahById(int blahId) throws SQLException;

    void updateBlah(Blah blah) throws SQLException;

    boolean isLike(int uid, int bid) throws SQLException;

    void setLike(int uid, int bid) throws SQLException;

    void deleteLike(int uid, int bid) throws SQLException;

    int getCommentCountByRoot(int root) throws SQLException;

    List<Blah> getCommentByRoot(int root, int page, int count) throws SQLException;
}

