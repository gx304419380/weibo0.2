package com.weibo.util;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class DataSourceUtils {

    private static ThreadLocal<Connection> local = new ThreadLocal<>();

    private static ComboPooledDataSource dataSource = new ComboPooledDataSource();
    public static DataSource getDataSource() {
        return dataSource;
    }
    public static Connection getConn() throws SQLException {
        Connection conn = local.get();
        if (conn == null) {
            conn = dataSource.getConnection();
            local.set(conn);
        }
        return conn;
    }
    public static void startTransaction() throws SQLException {
        Connection conn = getConn();
        if (conn != null)
            conn.setAutoCommit(false);
        else
            throw new SQLException();
    }

    public static void commitTransaction() throws SQLException {
        Connection conn = getConn();
        if (conn != null)
            conn.commit();
        else
            throw new SQLException();
    }

    public static void rollbackTransaction() throws SQLException {
        Connection conn = getConn();
        if (conn != null)
            conn.rollback();
        else
            throw new SQLException();
    }

    public static void endTransaction() throws SQLException {
        Connection conn = getConn();
        if (conn != null) {
            conn.setAutoCommit(true);
            conn.close();
        } else {
            throw new SQLException();
        }
    }
}
