package com.icampus.web.bean;

/**
 * 后台控制生成的内容（包括首页广告，视频，声明，小广告）
 * @author suife
 *
 */
public class Content extends BasePojo{
    private Long id;
    private String title;
    private String description;
    private String image;
    private String url;
    private Integer cid;
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
    public String getImage() {
        return image;
    }
    public void setImage(String image) {
        this.image = image;
    }
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public Integer getCid() {
        return cid;
    }
    public void setCid(Integer cid) {
        this.cid = cid;
    }
    
    
}
