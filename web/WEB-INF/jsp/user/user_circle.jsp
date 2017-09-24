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
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
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
            background:url(../../../img/flowers/img12.jpg);
            background-size:100%;
            background-repeat:repeat-y;
        }
    </style>
    <script src="js/weibo-global.js"></script>
    <script>
        $(function () {

        });
    </script>
</head>
<body style="font-family: 微软雅黑">
<div class="container-fluid">
    <jsp:include page="/menu.jsp"></jsp:include>
    <div id="div-main" class="row">
        <div class="page-header row">
            <h1>亲爱的：${user.nickname}，欢迎来到微博！</h1>
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

            <c:choose>
                <c:when test="${empty circleBlahs}">
                    <h3>
                        你和你的朋友还没有发微博，赶快号召他们加入吧！
                    </h3>
                </c:when>
                <c:otherwise>
                    <c:forEach var="blah" items="${circleBlahs}">
                        <div class="row uid${blah.uid}">
                            <div class="panel panel-info">
                                <div class="panel-heading">
                                    <span href="#" class="btn btn-link" style="margin: -6px -10px -5px -10px">
                                            ${blah.nickname}
                                    </span>
                                        <%--<a href="#" class="btn btn-xs btn-success glyphicon glyphicon-ok" style="margin-top: -6px; margin-bottom: -3px"></a>--%>
                                    <c:if test="${user.id != blah.uid}">
                                        <button onclick="unfollow(${blah.uid})" onmouseover="unfollow1(this)" onmouseout="unfollow2(this)" class="btn btn-xs btn-default" style="margin-top: -6px; margin-bottom: -3px">
                                            已关注
                                        </button>
                                    </c:if>

                                    <span style="float:right">
                                        <fmt:formatDate value="${blah.bdate}" pattern="yyyy-MM-dd hh:mm:ss"></fmt:formatDate>
                                    </span>
                                    <span style="clear: both"></span>
                                </div>
                                <div class="panel-body">
                                    &nbsp;&nbsp;${blah.content}
                                </div>
                                <div class="panel-footer text-right">
                                    <a class="btn btn-sm" onclick="listcomment(this, ${blah.id})" style="background-color: #44bceb;color: white;">评论&nbsp;
                                        <span class="glyphicon glyphicon-comment"></span>
                                        <span class="badge" id="comment-count-${blah.id}">${blah.commentcount}</span>

                                    </a>
                                    <a class="btn btn-sm" id="like-${blah.id}" onclick="like(this,${blah.id})" style="background-color: #eb2b1d;color: white;">点赞&nbsp;
                                        <c:set var="isLiked" value="false"></c:set>
                                        <c:forEach var="bid" items="${user.likeBidSet}">
                                            <c:if test="${bid == blah.id}">
                                                <c:set var="isLiked" value="true"></c:set>
                                            </c:if>
                                        </c:forEach>
                                        <c:choose>
                                            <c:when test="${isLiked}">
                                                <span class="glyphicon glyphicon-heart"></span>
                                            </c:when>
                                            <c:otherwise>
                                                <span class="glyphicon glyphicon-heart-empty"></span>
                                            </c:otherwise>
                                        </c:choose>
                                        <span class="badge">${blah.likecount}</span>
                                    </a>
                                </div>
                                <%@ include file="/comment-div.jsp"%>
                            </div>
                        </div>
                    </c:forEach>
                </c:otherwise>
            </c:choose>
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
