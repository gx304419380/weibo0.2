package com.weibo.service;

import com.weibo.dao.BlahDao;
import com.weibo.dao.BlahDaoImpl;
import com.weibo.domain.Blah;
import com.weibo.domain.User;

import java.sql.SQLException;
import java.util.List;

public class BlahService {
    private static BlahService INSTANCE = new BlahService();
    private BlahDao dao = new BlahDaoImpl();

    private BlahService() {}

    public static BlahService getService() {
        return INSTANCE;
    }

    public List<Blah> getBlahs(User user) throws SQLException {
        return dao.getBlahs(user);
    }

    public void save(Blah blah) throws SQLException {
        dao.save(blah);
        int root = 0;
        if ((root = blah.getRoot()) > 0) {
            Blah b = dao.getBlahById(root);
            b.setCommentcount(b.getCommentcount() + 1);
            dao.updateBlah(b);
        }
    }

    public List<Blah> getBlahs(User user, int page, int count) throws SQLException {
        return dao.getBlahs(user, page, count);
    }

    public int getTotalCount(User user) throws SQLException {
        return dao.getTotalCount(user);
    }

    public void deleteById(int blahId) throws SQLException {
        dao.deleteById(blahId);
    }

    public List<Blah> getAllBlahs(int page, int count) throws SQLException {
        return dao.getAllBlahs(page, count);
    }

    public int getAllBlahsCount() throws SQLException {
        return dao.getAllBlahsCount();
    }

    public int getResultBlahsCount(String keywords) throws SQLException {
        if (keywords == null || keywords.trim().equals("")) {
            return getAllBlahsCount();
        }
        return dao.getResultBlahsCount(keywords);
    }

    public List<Blah> getResultBlahs(String keywords, int page, int count) throws SQLException {
        if (keywords == null || keywords.trim().equals("")) {
            return getAllBlahs(page, count);
        }
        return dao.getResultBlahs(keywords ,page, count);
    }

    public List<Blah> getCircleBlahs(User user, int page, int count) throws SQLException {
        return dao.getCircleBlahs(user, page, count);
    }

    public Blah getBlahById(int blahId) throws SQLException {
        return dao.getBlahById(blahId);
    }

    public int likeById(int uid, int bid) throws SQLException {
        //begin the transaction
        Blah blah = dao.getBlahById(bid);
        int likeCount = blah.getLikecount();
        if (!dao.isLike(uid, bid)) {
            dao.setLike(uid, bid);
            blah.setLikecount(++likeCount);
            dao.updateBlah(blah);
        }
        //commit
        return likeCount;
    }

    public int dislikeById(int uid, int bid) throws SQLException {
        //begin the transaction
        Blah blah = dao.getBlahById(bid);
        int likeCount = blah.getLikecount();
        if (likeCount <= 0) {
            return 0;
        }
        if (dao.isLike(uid, bid)) {
            dao.deleteLike(uid, bid);
            blah.setLikecount(--likeCount);
            dao.updateBlah(blah);
        }
        //end the transaction
        return likeCount;
    }

    public int getCommentCountByRoot(int root) throws SQLException {
        return dao.getCommentCountByRoot(root);
    }

    public List<Blah> getCommentByRoot(int root, int page, int count) throws SQLException {
        return dao.getCommentByRoot(root, page, count);
    }
}
