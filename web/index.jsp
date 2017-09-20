<%--
  Created by IntelliJ IDEA.
  User: 别点我我怕疼
  Date: 2017/9/17
  Time: 20:19
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta name="viewport" content="width=device-width,initial-scale=1" />
    <link rel="stylesheet" href="css/bootstrap.min.css" />
    <script type="text/javascript" src="js/jquery-1.11.0.min.js" ></script>
    <script type="text/javascript" src="js/bootstrap.js" ></script>
    <title>$Title$</title>
    <style>
        #div-cont {
            padding-top: 120px;
            margin-top: -30px;
            background-size:100%;
            background: url(img/flowers/img12.jpg) no-repeat;
            height: 480px;
        }
    </style>
</head>
<body>
<div class="container-fluid">
    <jsp:include page="menu.jsp"></jsp:include>
    <%----%>
    <div id="div-cont" class="row text-center">
        <h1 class="h1">
            欢迎来到微博！
        </h1>
        <h5 class="h5">
            这是一个测试版本，你可以在这里畅所欲言！
        </h5>
        <h5 class="h5">
            随时随地发现新鲜事
        </h5>
    </div>
    <div class="col-sm-6" style="padding: 15px">
        <a href="user?method=registerInput" class="btn btn-warning btn-block">注册</a>
    </div>
    <div class= "col-sm-6"  style="padding: 15px">
        <a href="user?method=loginInput" class="btn btn-info btn-block">登陆</a>
    </div>
</div>

</body>
</html>
