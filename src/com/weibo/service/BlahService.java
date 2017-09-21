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
}
