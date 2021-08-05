package com.icampus.web.bean;


public class Card extends BasePojo{
    private Long id;
    private String title;
    private String type;
    private String description;
    private String images;
    private Long userId;
    private String site;
    private Integer stars;
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
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getImages() {
        return images;
    }
    public void setImages(String images) {
        this.images = images;
    }
    public Long getUserId() {
        return userId;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    public String getSite() {
        return site;
    }
    public void setSite(String site) {
        this.site = site;
    }
    public Integer getStars() {
        return stars;
    }
    public void setStars(Integer stars) {
        this.stars = stars;
    }
    @Override
    public String toString() {
        return "Card [id=" + id + ", title=" + title + ", type=" + type + ", description=" + description
                + ", images=" + images + ", userId=" + userId + ", site=" + site + ", stars=" + stars + "]";
    }
    
    
    
}
