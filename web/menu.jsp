<%--
  Created by IntelliJ IDEA.
  User: 别点我我怕疼
  Date: 2017/9/18
  Time: 19:32
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--head--%>
<div class="row">
    <nav class="navbar navbar-inverse" role="navigation">
        <div class="container-fluid">
            <!-- Brand and toggle get grouped for better mobile display -->
            <div class="navbar-header">
                <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
                    <span class="sr-only">Toggle navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <a class="navbar-brand" href="/">主页</a>
            </div>

            <!-- Collect the nav links, forms, and other content for toggling -->
            <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
                <ul class="nav navbar-nav">
                    <li><a href="user?method=home">我的</a></li>
                    <li><a href="user?method=circle">圈子</a></li>
                    <li><a href="global?method=hot">热门</a></li>
                    <li><a href="#">榜单</a></li>
                    <li><a href="#">本地</a></li>
                </ul>
                <ul class="nav navbar-nav navbar-right">
                    <li><a href="user?method=loginInput" class="btn btn-info" style="color: white;">登陆</a></li>
                    <li><a href="user?method=registerInput" class="btn btn-warning" style="color: white;">注册</a></li>
                </ul>

                <form class="navbar-form navbar-right" role="search">
                    <div class="form-group">
                        <input type="text" class="form-control" placeholder="搜索新鲜事">
                    </div>
                    <button type="submit" class="btn btn-default">搜索</button>
                </form>
            </div><!-- /.navbar-collapse -->
        </div><!-- /.container-fluid -->
    </nav>
</div>
