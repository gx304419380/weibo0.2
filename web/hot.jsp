<%--
  Created by IntelliJ IDEA.
  User: 别点我我怕疼
  Date: 2017/9/18
  Time: 11:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <meta name="viewport" content="width=device-width,initial-scale=1" />
    <link rel="stylesheet" href="css/bootstrap.css" />
    <script type="text/javascript" src="js/jquery-1.11.0.min.js" ></script>
    <script type="text/javascript" src="js/bootstrap.js" ></script>
    <title>欢迎使用微博</title>
    <style>
        #div-main {
            padding-top: 30px;
            padding-left: 30px;
            padding-right: 30px;
            margin-top: -30px;
            background:url(img/flowers/img12.jpg);
            background-size:100%;
            background-repeat:repeat-y;
        }
    </style>
</head>
<body>
<div class="container-fluid">
    <jsp:include page="menu.jsp"></jsp:include>
    <div id="div-main" class="row">
        <div class="page-header row">
            <h1>
                <c:if test="${user != null}">
                    亲爱的：${user.nickname}，
                </c:if>
                欢迎来到微博！</h1>
            <p style="font-size: 14px">————随时随地分享新鲜事</p>
        </div>

        <div class="col-md-1">
        </div>
        <div class="col-md-10">
            <nav>
                <ul class="pager">
                    <li class="previous"><a href="user?method=circle&page=${currentPage - 1}">&larr; 上一页</a></li>
                    <li>
                        <button class="btn btn-primary" type="button">
                            第${currentPage}页
                        </button>
                    </li>
                    <li class="next"><a href="user?method=circle&page=${currentPage + 1}">下一页 &rarr;</a></li>
                </ul>
            </nav>
            <c:forEach var="blah" items="${user.blahs}">
                <div class="row">
                    <div class="panel panel-info">
                        <div class="panel-heading">
                            <span>
                                    ${user.nickname}
                            </span>
                            <span style="float:right">
                                    <fmt:formatDate value="${blah.bdate}" pattern="yyyy-MM-dd hh:mm:ss"></fmt:formatDate>
                            </span>
                            <span style="clear: both"></span>
                        </div>
                        <div class="panel-body">
                            &nbsp;&nbsp;${blah.content}
                        </div>
                        <div class="panel-footer text-right">
                            foot
                        </div>
                    </div>
                </div>
            </c:forEach>
            <div class="row text-center" style="height: 200px">
                <nav>
                    <ul class="pagination">
                        <li><a href="user?method=circle&page=${currentPage - 1}">&laquo;</a></li>
                        <c:if test="${currentPage - 2 >= 1}">
                            <li><a href="user?method=circle&page=${currentPage - 2}">${currentPage - 2}</a></li>
                        </c:if>
                        <c:if test="${currentPage - 1 >= 1}">
                            <li><a href="user?method=circle&page=${currentPage - 1}">${currentPage - 1}</a></li>
                        </c:if>
                        <li class="active"><a href="#">${currentPage}<span class="sr-only">(current)</span></a></li>
                        <c:if test="${currentPage + 1 <= totalPage}">
                            <li><a href="user?method=circle&page=${currentPage + 1}">${currentPage + 1}</a></li>
                        </c:if>
                        <c:if test="${currentPage + 2 <= totalPage}">
                            <li><a href="user?method=circle&page=${currentPage + 2}">${currentPage + 2}</a></li>
                        </c:if>
                        <li><a href="user?method=circle&page=${currentPage + 1}">&raquo;</a></li>
                    </ul>
                </nav>
            </div>

        </div>
        <div class="col-md-1">
        </div>
    </div>
</div>
</body>
</html>