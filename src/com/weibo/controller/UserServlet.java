package com.weibo.controller;

import com.weibo.domain.Blah;
import com.weibo.domain.User;
import com.weibo.service.BlahService;
import com.weibo.service.UserService;
import com.weibo.util.DataSourceUtils;

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
            request.getRequestDispatcher("error.jsp").forward(request, response);
            return;
        }

        //get and involve the method
        try {
            Method method = this.getClass().getDeclaredMethod(methodName, HttpServletRequest.class, HttpServletResponse.class);

            //Authority check!
            String result = checkAuthority(methodName, request);
            if (result == null) {
                //start transaction

                result = (String) method.invoke(this, request, response);
                //commit transaction

            }
            String dealMethod = (String) request.getAttribute("dealMethod");
            if (dealMethod == null) {
                request.getRequestDispatcher(result).forward(request, response);
            } else if (dealMethod.equals("sendRedirect")) {
                response.sendRedirect(result);
            } else if (dealMethod.equals("write")) {
                response.setCharacterEncoding("utf-8");
                response.getWriter().write(result);
            }
        } catch (NoSuchMethodException e) {
            errorMessage.add("No such method");
            request.getRequestDispatcher("error.jsp").forward(request, response);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (Exception e) {
            errorMessage.add(e.getCause().toString());
            request.getRequestDispatcher("error.jsp").forward(request, response);
            e.printStackTrace();
        }
    }

    private String checkAuthority(String methodName, HttpServletRequest request) {
        Object user = request.getSession().getAttribute("user");
        if (user == null) {
            if (!methodName.equals("login")
                    && !methodName.equals("loginInput")
                    && !methodName.equals("register")
                    && !methodName.equals("registerInput")) {
                request.setAttribute("dealMethod", "sendRedirect");
                return "user?method=loginInput";
            }
        }
        return null;
    }

    private String loginInput(HttpServletRequest request, HttpServletResponse response) {
        String error = request.getParameter("error");
        if ("1".equals(error)) {
            List<String> errormsg = (List<String>)request.getAttribute("errormsg");
            errormsg.add("用户名或密码错误！");
        }
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
        request.setAttribute("dealMethod", "sendRedirect");
        User existUser = userService.getUser(user);
        if (existUser != null) {
            request.getSession().setAttribute("user", existUser);
            return "user?method=home";
        } else {
            return "user?method=loginInput&error=1";
        }

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
        List<Blah> blahs = blahService.getCircleBlahs(user, page, 10);
        request.setAttribute("circleBlahs", blahs);

        return "/WEB-INF/jsp/user/user_circle.jsp";
    }

    private String home(HttpServletRequest request, HttpServletResponse response) throws SQLException {
        User user = (User)request.getSession().getAttribute("user");
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

    private String deleteBlah(HttpServletRequest request, HttpServletResponse response) throws SQLException {
        User user = (User)request.getSession().getAttribute("user");
        int blahId = Integer.parseInt(request.getParameter("blahId"));
        //whether need to check the authority here?
        //check out if the blah to be deleted is the current user's blah
        Blah blah = blahService.getBlahById(blahId);
        if (blah == null || blah.getUid() != user.getId()) {
            List<String> errormsg = (List<String>)request.getAttribute("errormsg");
            errormsg.add("你没有权限删除不属于你的言论！");
            return "error.jsp";
        }
        blahService.deleteById(blahId);
        return "user?method=home";
    }

    private String publish(HttpServletRequest request, HttpServletResponse response) throws SQLException {
        String content = request.getParameter("content");
        User user = (User) request.getSession().getAttribute("user");
        Blah blah = new Blah();
        blah.setContent(content);
        blah.setUid(user.getId());
        blah.setBdate(new Date());
        blah.setPid(-1);
        blah.setRoot(-1);

        request.setAttribute("dealMethod", "sendRedirect");

        if (blah.getContent().length() >140) {
            blah.setContent(blah.getContent().substring(0, 140));
        } else if (blah.getContent().trim().length() == 0) {
            return "user?method=home";
        }
        blahService.save(blah);
        return "user?method=home";
    }

    private String comment(HttpServletRequest request, HttpServletResponse response) throws SQLException {
        String content = request.getParameter("content");
        String pid = request.getParameter("pid");
        String root = request.getParameter("root");

        User user = (User) request.getSession().getAttribute("user");
        Blah blah = new Blah();
        blah.setContent(content);
        blah.setUid(user.getId());
        blah.setBdate(new Date());
        blah.setPid(Integer.parseInt(pid));
        blah.setRoot(Integer.parseInt(root));
        blah.setNickname(user.getNickname());

        request.setAttribute("dealMethod", "write");
        if (blah.getContent().length() >140) {
            blah.setContent(blah.getContent().substring(0, 140));
        } else if (blah.getContent().trim().length() == 0) {
            return "comment error: content is null";
        }

        blahService.save(blah);
        String result = "<li class=\"list-group-item\" style=\"background-color: #d8d7d9\">" +
                blah.getNickname() +
                "</li>\n" +
                "<div class=\"panel-body\">\n" +
                "<p>" +
                blah.getContent() +
                "</p>\n" +
                "</div>\n";
        return "success" + result;
    }

    private String getComment(HttpServletRequest request, HttpServletResponse response) throws SQLException {
        User user = (User)request.getSession().getAttribute("user");
        String pageStr = request.getParameter("page");
        int root = Integer.parseInt(request.getParameter("root"));

        int page = 1;
        if (pageStr != null) {
            page = Integer.parseInt(pageStr);
        }
        int totalPage = (blahService.getCommentCountByRoot(root) - 1) / 10 + 1;
        if (page > totalPage) {
            request.setAttribute("dealMethod", "write");
            return "";
        }
        if (page < 1) {
            page = 1;
        }
        request.setAttribute("currentPage", page);
        request.setAttribute("totalPage", totalPage);
        List<Blah> comments = blahService.getCommentByRoot(root, page, 10);

        StringBuilder result = new StringBuilder();
        if (comments != null) {
            result.append("success");
            for (Blah comment:comments) {
                result.append("<li class=\"list-group-item\" style=\"background-color: #d8d7d9\">" +
                        comment.getNickname() +
                        "</li>\n" +
                        "<div class=\"panel-body\">\n" +
                        "<p>" +
                        comment.getContent() +
                        "</p>\n" +
                        "</div>\n");
            }
        }
        request.setAttribute("dealMethod", "write");
        return result.toString();
    }

    private String follow(HttpServletRequest request, HttpServletResponse response) throws SQLException {
        User user = (User)request.getSession().getAttribute("user");
        int id = Integer.parseInt(request.getParameter("id"));
        System.out.println("follow");
        System.out.println(user.getNickname());
        System.out.println(id);
        List<Integer> followIdList = userService.getFollowIdList(user);
        if (!followIdList.contains(id)) {
            userService.followById(user.getId(), id);
            user.getFollowIdList().add(id);
        }

        request.setAttribute("dealMethod", "write");
        return "follow success";
    }

    private String unFollow(HttpServletRequest request, HttpServletResponse response) throws SQLException {
        User user = (User)request.getSession().getAttribute("user");
        int id = Integer.parseInt(request.getParameter("id"));
        System.out.println("unFollow");
        System.out.println(user.getNickname());
        System.out.println(id);
        if (user.getFollowIdList().contains(id)) {
            userService.unFollowById(user.getId(), id);
            user.getFollowIdList().remove((Integer)id);
        }

        request.setAttribute("dealMethod", "write");
        return "unFollow success";
    }

    private String like(HttpServletRequest request, HttpServletResponse response) throws SQLException {
        User user = (User)request.getSession().getAttribute("user");
        int id = Integer.parseInt(request.getParameter("id"));

        int likeCount = blahService.likeById(user.getId(), id);
        user.getLikeBidSet().add(id);
        System.out.println(user.getLikeBidSet());

        request.setAttribute("dealMethod", "write");
        return "like success: " + likeCount;
    }

    private String dislike(HttpServletRequest request, HttpServletResponse response) throws SQLException {
        User user = (User)request.getSession().getAttribute("user");
        int id = Integer.parseInt(request.getParameter("id"));

        int likeCount = blahService.dislikeById(user.getId(), id);
        user.getLikeBidSet().remove((Integer)id);
        System.out.println(user.getLikeBidSet());

        request.setAttribute("dealMethod", "write");
        return "dislike success: " + likeCount;
    }

}
