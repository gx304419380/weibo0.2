package com.weibo.domain;

import java.util.Date;

public class Blah {
    private int id;
    private int uid;
    private String content;
    private Date bdate;
    private int pid;
    private String nickname;

    public Blah() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getBdate() {
        return bdate;
    }

    public void setBdate(Date bdate) {
        this.bdate = bdate;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    @Override
    public String toString() {
        return "Blah{" +
                "id=" + id +
                ", uid=" + uid +
                ", content='" + content + '\'' +
                ", bdate=" + bdate +
                ", pid=" + pid +
                ", nickname='" + nickname + '\'' +
                '}';
    }
}
