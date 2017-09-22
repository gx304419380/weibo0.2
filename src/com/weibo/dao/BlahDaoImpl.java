package com.weibo.dao;

import com.weibo.domain.Blah;
import com.weibo.domain.User;
import com.weibo.util.DataSourceUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.SQLException;
import java.util.List;

public class BlahDaoImpl implements BlahDao {
    @Override
    public List<Blah> getBlahs(User user) throws SQLException {
        QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
        String sql = "select * from blah where uid = ? and pid = -1 order by bdate desc";
        List<Blah> blahs = null;
        blahs = qr.query(sql, new BeanListHandler<Blah>(Blah.class), user.getId());
        return blahs;
    }

    @Override
    public void save(Blah blah) throws SQLException {
        QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
        String sql = "insert into blah values(null,?,?,?,?)";
        List<Blah> blahs = null;
        qr.update(sql,
                blah.getUid(),
                blah.getContent(),
                blah.getBdate(),
                blah.getPid());

    }

    @Override
    public List<Blah> getBlahs(User user, int page, int count) throws SQLException {
        QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
        int start = (page - 1) * count;
        String sql = "select * from blah b,user u where u.id=b.uid and uid = ? and pid = -1 order by bdate desc limit ?,?";
        List<Blah> blahs = null;
        blahs = qr.query(sql,
                new BeanListHandler<Blah>(Blah.class),
                user.getId(),
                start,
                count);

        return blahs;
    }

    @Override
    public int getTotalCount(User user) throws SQLException {
        QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
        String sql = "select count(*) from blah where uid = ? and pid = -1";

        Object totalCount = 0;
        totalCount =  qr.query(sql, new ScalarHandler(), user.getId());

        return Integer.parseInt(totalCount.toString());
    }

    @Override
    public void deleteById(int blahId) throws SQLException {
        QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
        String sql = "delete from blah where id = ?";
        qr.update(sql, blahId);
    }

    @Override
    public List<Blah> getAllBlahs(int page, int count) throws SQLException {
        QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
        int start = (page - 1) * count;
        String sql = "select * from blah b,user u " +
                "where u.id=b.uid and " +
                "pid = -1 " +
                "order by bdate desc " +
                "limit ?,?";
        List<Blah> blahs = null;
        blahs = qr.query(sql,
                new BeanListHandler<Blah>(Blah.class),
                start,
                count);

        return blahs;
    }

    @Override
    public int getAllBlahsCount() throws SQLException {
        QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
        String sql = "select count(*) from blah where pid = -1";
        Object count = 0;
        count =  qr.query(sql, new ScalarHandler());
        return Integer.parseInt(count.toString());
    }

    @Override
    public List<Blah> getResultBlahs(String keywords, int page, int count) throws SQLException {
        keywords = "%" + keywords + "%";
        QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
        int start = (page - 1) * count;
        String sql = "select * from blah b,user u " +
                "where u.id=b.uid " +
                "and pid = -1 " +
                "and content like ? " +
                "order by bdate desc " +
                "limit ?,?";
        List<Blah> blahs = null;
        blahs = qr.query(sql,
                new BeanListHandler<Blah>(Blah.class),
                keywords,
                start,
                count);
        return blahs;
    }

    @Override
    public int getResultBlahsCount(String keywords) throws SQLException {
        keywords = "%" + keywords + "%";
        QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
        String sql = "select count(*) from blah where pid = -1 and content like ?";
        Object count = 0;
        count =  qr.query(sql, new ScalarHandler(), keywords);
        return Integer.parseInt(count.toString());
    }
}
