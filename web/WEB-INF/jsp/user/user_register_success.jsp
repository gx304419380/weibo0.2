<%--
  Created by IntelliJ IDEA.
  User: 别点我我怕疼
  Date: 2017/9/17
  Time: 23:23
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta name="viewport" content="width=device-width,initial-scale=1" />
    <link rel="stylesheet" href="css/bootstrap.min.css" />
    <script type="text/javascript" src="js/jquery-1.11.0.min.js" ></script>
    <script type="text/javascript" src="js/bootstrap.js" ></script>
    <title>注册成功</title>
</head>
<body style="font-family: 微软雅黑">
<div class="container-fluid">
    <jsp:include page="../../../menu.jsp"></jsp:include>
    <%--main--%>
    <h1>恭喜你，注册成功！</h1>
    <a class="btn btn-primary btn-block" href="user?method=loginInput">登陆</a>
</div>
</body>
</html>
