<%--
  Created by IntelliJ IDEA.
  User: 别点我我怕疼
  Date: 2017/9/17
  Time: 20:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta name="viewport" content="width=device-width,initial-scale=1" />
    <link rel="stylesheet" href="css/bootstrap.css" />
    <script type="text/javascript" src="js/jquery-1.11.0.min.js" ></script>
    <script type="text/javascript" src="js/bootstrap.js" ></script>
    <title>用户注册</title>
    <style>
        #main-div {
            padding-top: 30px;
            margin-top: -30px;
            background:url(../../../img/flowers/img8.jpg);
            background-size:100% 100%;
            background-repeat:no-repeat;
            height: 480px;
        }
    </style>
</head>
<body style="font-family: 微软雅黑">
<div class="container-fluid">
    <jsp:include page="/menu.jsp"></jsp:include>
    <%--main--%>
    <div class="row" id="main-div">
        <div class="col-md-1"></div>
        <div class="col-md-4">
            <form role="form" method="post" action="user?method=register">
                <div class="form-group">
                    <label for="username">用户名</label>
                    <input type="text" class="form-control" id="username" name="username" placeholder="请输入用户名">
                </div>
                <div class="form-group">
                    <label for="password">密码</label>
                    <input type="password" class="form-control" id="password" name="password" placeholder="请输入密码">
                </div>
                <div class="form-group">
                    <label for="repassword">确认密码</label>
                    <input type="password" class="form-control" id="repassword" name="repassword" placeholder="请输入密码">
                </div>
                <div class="form-group">
                    <label for="username">昵称</label>
                    <input type="text" class="form-control" id="nickname" name="nickname" placeholder="请输入昵称">
                </div>
                <div class="form-group">
                    <label for="email">邮箱</label>
                    <input type="" class="form-control" id="email" name="email" placeholder="请输入email邮箱">
                </div>
                <br>
                <button type="submit" class="btn btn-block btn-info">注册</button>
            </form>
        </div>
        <div class="col-md-6 hidden-sm hidden-xs text-right">
            <h3 class="h3">
                欢迎来到微博
            </h3>
            <p>
                这是一个测试版本，在这里分享你的忧伤和快乐
            </p>
        </div>
        <div class="col-md-1"></div>
    </div>
    <div class="row text-center" style="padding-top: 20px">
        <ul class="list-inline list-unstyled">
            <li><a href="#">友情链接</a></li>
            <li><a href="#">友情链接</a></li>
            <li><a href="#">友情链接</a></li>
            <li><a href="#">友情链接</a></li>
            <li><a href="#">友情链接</a></li>
        </ul>
    </div>
</div>
</body>
</html>
