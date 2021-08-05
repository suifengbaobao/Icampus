package com.icampus.manage.pojo;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "ic_article")
public class Article extends BasePojo{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    private String content;
    @Column(name = "user_id")
    private Long userId;
    @Column(name = "read_nums")
    private Integer readNums;
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public Long getUserId() {
        return userId;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    public Integer getReadNums() {
        return readNums;
    }
    public void setReadNums(Integer readNums) {
        this.readNums = readNums;
    }
    @Override
    public String toString() {
        return "Article [id=" + id + ", title=" + title + ", description=" + description + ", content="
                + content + ", userId=" + userId + ", readNums=" + readNums + "]";
    }
    
    
}
