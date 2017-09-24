function like(obj,blahid) {
    var $obj = $(obj).find("span").first();
    if ($obj.hasClass("glyphicon-heart-empty")) {
        var url = 'user?method=like&id='+blahid;
        $.get(url, function(data,status) {
            var res = data.split(": ");
            if (res[0] == "like success") {
                $obj.removeClass("glyphicon-heart-empty").addClass("glyphicon-heart");
                $obj.next().text(res[1]);
            }
        });
    }
    else {
        var url = 'user?method=dislike&id='+blahid;
        $.get(url, function(data,status) {
            var res = data.split(": ");
            if (res[0] == "dislike success")  {
                $obj.removeClass("glyphicon-heart").addClass("glyphicon-heart-empty");
                $obj.next().text(res[1]);
            }
        });
    }
}
function unfollow1(obj) {
    $(obj).addClass("btn-danger").removeClass("btn-default").text("取消关注");
}
function unfollow2(obj) {
    $(obj).removeClass("btn-danger").addClass("btn-default").text("已关注");
}
function unfollow(id) {
    var url = 'user?method=unFollow&id='+id;
    $.get(url, function(data,status){
        if (data == "unFollow success") {
            var $obj = $(".uid" + id + " button");
            $obj.removeAttr("onmouseover").removeAttr("onmouseout").removeAttr("onclick");
            $obj.attr("onclick", "follow(" + id + ")");
            $obj.text("关注").removeClass("btn-danger btn-default").addClass("btn-success");
        }
    });
}
function follow(id) {
    var url = 'user?method=follow&id='+id;
    $.get(url, function(data,status){
        if (data == "follow success") {
            var $obj = $(".uid" + id + " button");
            $obj.attr("onclick", "unfollow(" + id + ")");
            $obj.attr("onmouseover", "unfollow1(this)").attr("onmouseout", "unfollow2(this)");
            $obj.text("已关注").addClass("btn-default").removeClass("btn-success");
        }
    });
}

function listcomment(obj, id) {
    var $div = $(obj).parent().next();
    var ul = $div.find("ul");
    if ($div.hasClass("hidden")) {
        $div.removeClass("hidden");
        updateCommentList(ul, id, 1);
    } else {
        var ul = $div.find("ul");
        var listmore = ul.find("li.listmore");
        ul.empty().append(listmore);
        listmore.attr("page","1");
        $div.addClass("hidden");
    }

}

function updateCommentList(ul, root, page) {
    var url = "user?method=getComment";
    $.post(url,
        {
            root:root,
            page:page
        },
        function(data,status){
            if (data.substr(0, 7) == "success") {
                ul.find("li.listmore").before(data.substr(7));
            }
            if (data != "")
                ul.find("li.listmore").attr("page", parseInt(page)+1);
        });
}

function comment(obj, pid, root) {
    var $form = $(obj).parent().parent().parent();
    var content = $form.find("textarea").val();
    var url = "user?method=comment";
    $.post(url,
        {
            content:content,
            pid:pid,
            root:root
        },
        function(data,status){
            if (data.substr(0, 7) == "success") {
                var ul = $form.parent().next();
                ul.children().first().before(data.substr(7));
                $form.find("textarea").text("");
                var commentcount = $("#comment-count-" + pid);
                var count = parseInt(commentcount.text()) + 1;
                commentcount.text(count);
            }
        });

}