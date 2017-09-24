package com.weibo.service;

import com.weibo.dao.UserDao;
import com.weibo.dao.UserDaoImpl;
import com.weibo.domain.User;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserService {
    private static UserService INSTANCE = new UserService();
    private UserDao dao = new UserDaoImpl();

    public static UserService getService() {
        return INSTANCE;
    }

    private UserService() {

    }


    public boolean validateUser(User user) {
        return false;
    }

    public User getUser(User user) throws SQLException {
        if (user.getUsername() == null || "".equals(user.getUsername().trim())) {
            return null;
        }
        if (user.getPassword() == null || "".equals(user.getPassword().trim())) {
            return null;
        }
        User u = dao.getUser(user);
        if (u != null) {
            u.setFollowIdList(dao.getFollowIdList(u));
            u.setLikeBidSet(dao.getLikeBidSet(u));
        }
        return u;
    }

    public List<String> checkForRegister(User user, String repassword) throws SQLException {
        List<String> errors = new ArrayList<>();
        if (user.getUsername() == null || "".equals(user.getUsername().trim())) {
            errors.add("用户名不能为空！");
        } else {
            User u = dao.getUserByName(user.getUsername());
            if (u != null) {
                errors.add("用户名已存在！");
            }
        }
        if (user.getPassword() == null || "".equals(user.getPassword().trim())) {
            errors.add("密码不能为空！");
        }
        if (user.getPassword() != null && !user.getPassword().equals(repassword)) {
            errors.add("两次密码不一致！");
        }
        String emailRegex = "\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*";
        if (user.getEmail() != null && !user.getEmail().matches(emailRegex)) {
            errors.add("邮箱格式有误！");
        }
        return errors;
    }

    public void save(User user) throws SQLException {
        dao.save(user);
    }

    public List<Integer> getFollowIdList(User user) throws SQLException {
        return dao.getFollowIdList(user);
    }

    public void followById(Integer uid, Integer fid) throws SQLException {
        dao.followById(uid, fid);
    }

    public void unFollowById(Integer uid, Integer fid) throws SQLException {
        dao.unFollowById(uid, fid);
    }
}
