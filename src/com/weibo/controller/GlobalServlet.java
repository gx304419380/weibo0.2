package com.weibo.controller;

import com.weibo.domain.Blah;
import com.weibo.service.BlahService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/global")
public class GlobalServlet extends HttpServlet {

    private BlahService blahService = BlahService.getService();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        //create an error message list to store the error information
        List<String> errors = new ArrayList<>();
        request.setAttribute("errormsg",errors);
        //get the name of method which is to be involved from request parameters
        String methodName = request.getParameter("method");

        //if the method name is null, forward to the error page
        if (methodName == null) {
            errors.add("Error parameter!");
            request.getRequestDispatcher("error.jsp").forward(request, response);
            return;
        }

        String result = null;
        try {
            switch (methodName) {
                case "hot":
                    result = hot(request, response);
                    break;
                case "local":
                    result = local(request, response);
                    break;
                case "search":
                    result = search(request, response);
                    break;
                default:
                    break;
            }
            String dealMethod = (String) request.getAttribute("dealMethod");
            if (dealMethod == null) {
                request.getRequestDispatcher(result).forward(request, response);
            } else if (dealMethod.equals("sendRedirect")) {
                response.sendRedirect(result);
            }
        } catch (SQLException e) {
            System.out.println("sql error");
            e.printStackTrace();
            errors.add("SQL ERROR");
            request.getRequestDispatcher("error.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String search(HttpServletRequest request, HttpServletResponse response) throws SQLException, UnsupportedEncodingException {

        String keywords = request.getParameter("keywords");
        keywords = new String(keywords.getBytes("ISO-8859-1"), "utf-8");
        String pageStr = request.getParameter("page");
        System.out.println("search");
        System.out.println(keywords);

        int page = 1;
        if (pageStr != null) {
            page = Integer.parseInt(pageStr);
        }
        int totalPage = (blahService.getResultBlahsCount(keywords) - 1) / 10 + 1;
        if (page > totalPage) {
            page = totalPage;
        }
        if (page < 1) {
            page = 1;
        }
        request.setAttribute("currentPage", page);
        request.setAttribute("totalPage", totalPage);
        List<Blah> blahs = blahService.getResultBlahs(keywords, page, 10);
        request.setAttribute("searchBlahs",blahs);
//        request.setAttribute("keywords", URLEncoder.encode(keywords, "utf-8"));
        request.setAttribute("keywords", keywords);
        return "search.jsp";
    }

    private String hot(HttpServletRequest request, HttpServletResponse response) throws SQLException {
        String pageStr = request.getParameter("page");
        int page = 1;
        if (pageStr != null) {
            page = Integer.parseInt(pageStr);
        }
        int totalPage = (blahService.getAllBlahsCount() - 1) / 10 + 1;
        if (page > totalPage) {
            page = totalPage;
        }
        if (page < 1) {
            page = 1;
        }
        request.setAttribute("currentPage", page);
        request.setAttribute("totalPage", totalPage);
        List<Blah> blahs = blahService.getAllBlahs(page, 10);
        request.setAttribute("hotBlahs",blahs);
        return "hot.jsp";
    }

    private String local(HttpServletRequest request, HttpServletResponse response) {
        return "local.jsp";
    }


}
