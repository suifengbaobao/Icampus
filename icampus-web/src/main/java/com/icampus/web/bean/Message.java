package com.icampus.web.bean;

import java.util.Date;

public class Message extends BasePojo{
    private Long id;
    private String content;
    private Integer stars;
    private Long userId;
    private String state;// 是否邮寄 0为不邮寄 1为邮寄
    private Date postTime;// 邮寄时间
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public Integer getStars() {
        return stars;
    }
    public void setStars(Integer stars) {
        this.stars = stars;
    }
    public Long getUserId() {
        return userId;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    public String getState() {
        return state;
    }
    public void setState(String state) {
        this.state = state;
    }
    public Date getPostTime() {
        return postTime;
    }
    public void setPostTime(Date postTime) {
        this.postTime = postTime;
    }
    @Override
    public String toString() {
        return "Message [id=" + id + ", content=" + content + ", stars=" + stars + ", userId=" + userId
                + ", state=" + state + ", postTime=" + postTime + "]";
    }
    
}
