<%--
  Created by IntelliJ IDEA.
  User: 别点我我怕疼
  Date: 2017/9/21
  Time: 23:24
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
    <script src="js/weibo-global.js">
    </script>
</head>
<body style="font-family: 微软雅黑">
<div class="container-fluid">
    <jsp:include page="/menu.jsp"></jsp:include>
    <div id="div-main" class="row">
        <div class="page-header row">
            <h1>
                <c:if test="${user != null}">
                    亲爱的：${user.nickname}，
                </c:if>
                欢迎来到微博！
            </h1>
            <p style="font-size: 14px">————随时随地分享新鲜事</p>
        </div>

        <div class="col-md-1">
        </div>
        <div class="col-md-10">

            <nav>
                <ul class="pager">
                    <li class="previous"><a href="global?method=search&page=${currentPage - 1}&keywords=${keywords}">&larr; 上一页</a></li>
                    <li>
                        <button class="btn btn-primary" type="button">
                            第${currentPage}页
                        </button>
                    </li>
                    <li class="next"><a href="global?method=search&page=${currentPage + 1}&keywords=${keywords}">下一页 &rarr;</a></li>
                </ul>
            </nav>
            <c:choose>
                <c:when test="${empty searchBlahs}">
                    <h3>
                        对不起，未找到您要搜索的微博和用户。>_<
                    </h3>
                    <h4>
                        搜索关键字：${keywords}
                    </h4>
                </c:when>
                <c:otherwise>
                    <c:forEach var="blah" items="${searchBlahs}">
                        <div class="row">
                            <div class="panel panel-info">
                                <div class="panel-heading">
                                    <span href="#" class="btn btn-link" style="margin: -6px -10px -5px -10px">
                                            ${blah.nickname}
                                    </span>
                                        <%--<a href="#" class="btn btn-xs btn-success glyphicon glyphicon-ok" style="margin-top: -6px; margin-bottom: -3px"></a>--%>
                                    <c:set var="isFollowed" value="false"></c:set>
                                    <c:if test="${user != null}">
                                        <c:forEach var="id" items="${user.followIdList}">
                                            <c:if test="${id == blah.uid}">
                                                <c:set var="isFollowed" value="true"></c:set>
                                            </c:if>
                                        </c:forEach>
                                    </c:if>

                                    <c:choose>
                                        <c:when test="${isFollowed}">
                                            <a href="user?method=unFollow&id=${blah.uid}" onmouseover="unfollow1(this)" onmouseleave="unfollow2(this)" class="btn btn-xs btn-default" style="margin-top: -6px; margin-bottom: -3px">
                                                已关注
                                            </a>
                                        </c:when>
                                        <c:otherwise>
                                            <c:if test="${user == null || user.id != blah.id}">
                                                <a href="user?method=follow&id=${blah.uid}" class="btn btn-xs btn-success" style="margin-top: -6px; margin-bottom: -3px">
                                                    关注
                                                </a>
                                            </c:if>
                                        </c:otherwise>
                                    </c:choose>

                                    <span style="float:right">
                                        <fmt:formatDate value="${blah.bdate}" pattern="yyyy-MM-dd hh:mm:ss"></fmt:formatDate>
                                    </span>
                                    <span style="clear: both"></span>
                                </div>
                                <div class="panel-body">
                                    &nbsp;&nbsp;${blah.content}
                                </div>
                                <div class="panel-footer text-right">
                                    <a href="#" class="btn btn-sm" style="background-color: #44bceb;color: white;">评论&nbsp;<span class="glyphicon glyphicon-comment"></span></a>
                                    <a class="btn btn-sm" id="like-${blah.id}" onclick="like(this)" style="background-color: #eb2b1d;color: white;">点赞&nbsp;<span class="glyphicon glyphicon-heart-empty"></span></a>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </c:otherwise>
            </c:choose>
            <div class="row text-center" style="height: 200px">
                <nav>
                    <ul class="pagination">
                        <li><a href="global?method=search&page=${currentPage - 1}&keywords=${keywords}">&laquo;</a></li>
                        <c:if test="${currentPage - 2 >= 1}">
                            <li><a href="global?method=search&page=${currentPage - 2}&keywords=${keywords}">${currentPage - 2}</a></li>
                        </c:if>
                        <c:if test="${currentPage - 1 >= 1}">
                            <li><a href="global?method=search&page=${currentPage - 1}&keywords=${keywords}">${currentPage - 1}</a></li>
                        </c:if>
                        <li class="active"><a href="#">${currentPage}<span class="sr-only">(current)</span></a></li>
                        <c:if test="${currentPage + 1 <= totalPage}">
                            <li><a href="global?method=search&page=${currentPage + 1}&keywords=${keywords}">${currentPage + 1}</a></li>
                        </c:if>
                        <c:if test="${currentPage + 2 <= totalPage}">
                            <li><a href="global?method=search&page=${currentPage + 2}&keywords=${keywords}">${currentPage + 2}</a></li>
                        </c:if>
                        <li><a href="global?method=search&page=${currentPage + 1}&keywords=${keywords}">&raquo;</a></li>
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

