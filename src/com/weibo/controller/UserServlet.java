package com.weibo.controller;

import com.weibo.domain.Blah;
import com.weibo.domain.User;
import com.weibo.service.BlahService;
import com.weibo.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@WebServlet("/user")
public class UserServlet extends HttpServlet {

    private UserService userService = UserService.getService();
    private BlahService blahService = BlahService.getService();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        //create an error message list to store the error information
        List<String> errorMessage = new ArrayList<>();
        request.setAttribute("errormsg",errorMessage);

        //get the name of method which is to be involved from request parameters
        String methodName = request.getParameter("method");

        //if the method name is null, forward to the error page
        if (methodName == null) {
            errorMessage.add("Error parameter!");
            request.getRequestDispatcher("/error.jsp").forward(request, response);
            return;
        }

        //get and involve the method
        try {
            Method method = this.getClass().getDeclaredMethod(methodName, HttpServletRequest.class, HttpServletResponse.class);
            String result = (String) method.invoke(this, request, response);
            String dealMethod = (String) request.getAttribute("dealMethod");
            if (dealMethod == null) {
                request.getRequestDispatcher(result).forward(request, response);
            } else if (dealMethod.equals("sendRedirect")) {
                response.sendRedirect(result);
            }
        } catch (NoSuchMethodException e) {
            errorMessage.add("No such method");
            request.getRequestDispatcher("/error.jsp").forward(request, response);
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            errorMessage.add(e.getCause().getMessage());
            request.getRequestDispatcher("/error.jsp").forward(request, response);
            e.printStackTrace();
        }
    }

    private String loginInput(HttpServletRequest request, HttpServletResponse response) {
        return "/WEB-INF/jsp/user/user_login.jsp";
    }

    private String registerInput(HttpServletRequest request, HttpServletResponse response) {
        return "/WEB-INF/jsp/user/user_register.jsp";
    }

    private String login(HttpServletRequest request, HttpServletResponse response) throws SQLException {
        System.out.println("user login");
        User user = new User();
        user.setUsername(request.getParameter("username"));
        user.setPassword(request.getParameter("password"));
        User existUser = userService.getUser(user);
        if (existUser != null) {
            request.getSession().setAttribute("user", existUser);
            request.setAttribute("dealMethod", "sendRedirect");
            return "user?method=home";
        }
        List<String> errormsg = (List<String>)request.getAttribute("errormsg");
        errormsg.add("用户名或密码错误！");
        return "error.jsp";
    }

    private String register(HttpServletRequest request, HttpServletResponse response) throws SQLException {
        System.out.println("user register");
        User user = new User();
        user.setUsername(request.getParameter("username"));
        user.setPassword(request.getParameter("password"));
        user.setEmail(request.getParameter("email"));
        user.setNickname(request.getParameter("nickname"));
        String repassword = request.getParameter("repassword");

        List<String> errors = userService.checkForRegister(user, repassword);
        if (errors.size() > 0) {
            List<String> errormsg = (List<String>)request.getAttribute("errormsg");
            errormsg.addAll(errors);
            return "error.jsp";
        }

        userService.save(user);
        return "/WEB-INF/jsp/user/user_register_success.jsp";
    }

    private String circle(HttpServletRequest request, HttpServletResponse response) throws SQLException {
        User user = (User)request.getSession().getAttribute("user");
        if (user == null) {
            return "/WEB-INF/jsp/user/user_login.jsp";
        }

        String pageStr = request.getParameter("page");
        int page = 1;
        if (pageStr != null) {
            page = Integer.parseInt(pageStr);
        }
        int totalPage = (blahService.getTotalCount(user) - 1) / 10 + 1;
        if (page > totalPage) {
            page = totalPage;
        }
        if (page < 1) {
            page = 1;
        }
        request.setAttribute("currentPage", page);
        request.setAttribute("totalPage", totalPage);
        List<Blah> blahs = blahService.getBlahs(user, page, 10);
        user.setBlahs(blahs);

        return "/WEB-INF/jsp/user/user_circle.jsp";
    }

    private String home(HttpServletRequest request, HttpServletResponse response) throws SQLException {
        User user = (User)request.getSession().getAttribute("user");
        if (user == null) {
            return "/WEB-INF/jsp/user/user_login.jsp";
        }
        String pageStr = request.getParameter("page");
        int page = 1;
        if (pageStr != null) {
            page = Integer.parseInt(pageStr);
        }
        int totalPage = (blahService.getTotalCount(user) - 1) / 10 + 1;
        if (page > totalPage) {
            page = totalPage;
        }
        if (page < 1) {
            page = 1;
        }
        request.setAttribute("currentPage", page);
        request.setAttribute("totalPage", totalPage);
        List<Blah> blahs = blahService.getBlahs(user, page, 10);
        user.setBlahs(blahs);
        return "/WEB-INF/jsp/user/user_home.jsp";
    }

    private String publish(HttpServletRequest request, HttpServletResponse response) throws SQLException {
        String content = request.getParameter("content");
        User user = (User) request.getSession().getAttribute("user");
        Blah blah = new Blah();
        blah.setContent(content);
        blah.setUid(user.getId());
        blah.setBdate(new Date());
        blah.setPid(-1);

        request.setAttribute("dealMethod", "sendRedirect");

        if (blah.getContent().length() >140) {
            blah.setContent(blah.getContent().substring(0, 140));
        } else if (blah.getContent().trim().length() == 0) {
            return "user?method=home";
        }
        blahService.save(blah);
        return "user?method=home";
    }
}
