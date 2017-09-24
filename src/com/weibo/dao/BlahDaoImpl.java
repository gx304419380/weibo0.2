package com.weibo.dao;

import com.weibo.domain.Blah;
import com.weibo.domain.User;
import com.weibo.util.DataSourceUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
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
        String sql = "insert into blah (uid,content,bdate,pid,root) values(?,?,?,?,?)";
        List<Blah> blahs = null;
        qr.update(sql,
                blah.getUid(),
                blah.getContent(),
                blah.getBdate(),
                blah.getPid(),
                blah.getRoot());

    }

    @Override
    public List<Blah> getBlahs(User user, int page, int count) throws SQLException {
        QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
        int start = (page - 1) * count;
        String sql = "select b.*,u.nickname " +
                "from blah b,user u " +
                "where u.id=b.uid " +
                "and uid = ? " +
                "and pid = -1 " +
                "order by bdate " +
                "desc limit ?,?";
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
//        String sql = "select b.id,uid,content,bdate,pid,commentcount,likecount,nickname " +
        String sql = "select b.*,u.nickname " +
                "from blah b,user u " +
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
        String sql = "select b.*,u.nickname " +
                "from blah b,user u " +
                "where u.id=b.uid " +
                "and pid = -1 " +
                "and (content like ? " +
                "or nickname like ?) " +
                "order by bdate desc " +
                "limit ?,?";
        List<Blah> blahs = null;
        blahs = qr.query(sql,
                new BeanListHandler<Blah>(Blah.class),
                keywords,
                keywords,
                start,
                count);
        return blahs;
    }

    @Override
    public int getResultBlahsCount(String keywords) throws SQLException {
        keywords = "%" + keywords + "%";
        QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
        String sql = "select count(*) " +
                "from blah b,user u " +
                "where u.id=b.uid " +
                "and pid = -1 " +
                "and (content like ? " +
                "or nickname like ?)";
        Object count = 0;
        count =  qr.query(sql, new ScalarHandler(), keywords, keywords);
        return Integer.parseInt(count.toString());
    }

    @Override
    public List<Blah> getCircleBlahs(User user, int page, int count) throws SQLException {
        QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
        int start = (page - 1) * count;
        String sql = "select b.*,u.nickname " +
                "from blah b,user u " +
                "where u.id=b.uid " +
                "and pid = -1 " +
                "and (" +
                "u.id in " +
                "(" +
                "select fid from relationship where uid = ?" +
                ") " +
                "or u.id=?" +
                ") " +
                "order by bdate desc " +
                "limit ?,?";

        List<Blah> blahs = null;
        blahs = qr.query(sql,
                new BeanListHandler<Blah>(Blah.class),
                user.getId(),
                user.getId(),
                start,
                count);
        return blahs;
    }

    @Override
    public Blah getBlahById(int blahId) throws SQLException {
        QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
        String sql = "select * from blah where id=?";
        Blah blah = qr.query(sql, new BeanHandler<Blah>(Blah.class), blahId);
        return blah;
    }

    @Override
    public void updateBlah(Blah blah) throws SQLException {
        QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
        String sql = "update blah set uid=?,content=?,bdate=?,pid=?,root=?,commentcount=?,likecount=? where id=?";
        qr.update(sql,
                blah.getUid(),
                blah.getContent(),
                blah.getBdate(),
                blah.getPid(),
                blah.getRoot(),
                blah.getCommentcount(),
                blah.getLikecount(),
                blah.getId());
    }

    @Override
    public boolean isLike(int uid, int bid) throws SQLException {
        QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
        String sql = "select count(*) from like_map where uid=? and bid=?";
        Object count = qr.query(sql, new ScalarHandler(), uid, bid);
        return (Integer.parseInt(count.toString()) > 0);
    }

    @Override
    public void setLike(int uid, int bid) throws SQLException {
        QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
        String sql = "insert into like_map values(null, ?, ?)";
        qr.update(sql, uid, bid);
    }

    @Override
    public void deleteLike(int uid, int bid) throws SQLException {
        QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
        String sql = "delete from like_map where uid=? and bid=?";
        qr.update(sql, uid, bid);
    }

    @Override
    public int getCommentCountByRoot(int root) throws SQLException {
        QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
        String sql = "select count(*) from blah where root=?";
        Object count = 0;
        count =  qr.query(sql, new ScalarHandler(), root);
        return Integer.parseInt(count.toString());
    }

    @Override
    public List<Blah> getCommentByRoot(int root, int page, int count) throws SQLException {
        QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
        int start = (page - 1) * count;
        String sql = "select b.*,u.nickname " +
                "from blah b,user u " +
                "where u.id=b.uid and " +
                "root = ? " +
                "order by bdate desc " +
                "limit ?,?";
        List<Blah> blahs = null;
        blahs = qr.query(sql,
                new BeanListHandler<Blah>(Blah.class),
                root,
                start,
                count);
        return blahs;
    }
}
