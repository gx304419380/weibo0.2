package com.weibo.domain;

import java.util.Date;
import java.util.List;
import java.util.Set;

public class User {
    private Integer id;
    private String username;
    private String password;
    private String nickname;
    private String email;
    private Date rdate;
    private List<Blah> blahs;
    private List<Integer> followIdList;
    private Set<Integer> likeBidSet;

    public User() {
    }

    public User(Integer id, String username, String password, String email) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getRdate() {
        return rdate;
    }

    public void setRdate(Date rdate) {
        this.rdate = rdate;
    }


    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public List<Blah> getBlahs() {
        return blahs;
    }

    public void setBlahs(List<Blah> blahs) {
        this.blahs = blahs;
    }

    public List<Integer> getFollowIdList() {
        return followIdList;
    }

    public void setFollowIdList(List<Integer> followIdList) {
        this.followIdList = followIdList;
    }

    public Set<Integer> getLikeBidSet() {
        return likeBidSet;
    }

    public void setLikeBidSet(Set<Integer> likeBidSet) {
        this.likeBidSet = likeBidSet;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", rdate=" + rdate +
                '}';
    }
}
