<%--
  Created by IntelliJ IDEA.
  User: 别点我我怕疼
  Date: 2017/9/24
  Time: 21:07
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="panel panel-info hidden">
    <!-- 评论列表 -->
    <div class="panel-heading" style="background-color: #2dd9c6;color: white;">
        <form role="form" style="margin-bottom: -18px">
            <div class="form-group">
                <label for="comment-ta-${blah.id}">发表评论：</label>
                <textarea id="comment-ta-${blah.id}" name="content" class="form-control" rows="5" placeholder="输入内容"></textarea>
            </div>
            <div class="form-group" style="margin-top: -10px;">
                <div class="text-right">
                    <button onclick="comment(this, ${blah.id},${blah.id})" type="button" class="btn btn-sm btn-primary">发布</button>
                </div>
            </div>
        </form>
    </div>
    <!-- List group -->
    <ul class="list-group">
        <li class="list-group-item btn btn-sm listmore" onclick="updateCommentList($(this).parent(),${blah.id},$(this).attr('page'))" page="1" style="text-align: center; background-color: #d8d7d9">
            获取更多
        </li>
    </ul>
</div>
