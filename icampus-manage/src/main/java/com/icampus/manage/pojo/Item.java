package com.icampus.manage.pojo;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
/**
 * 商品信息类
 * @author suife
 *
 */
@Table(name = "ic_item")
public class Item extends BasePojo{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    private String content;
    private Long price;
    private Integer nums;
    private String image;
    private Integer status;
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
    public Long getPrice() {
        return price;
    }
    public void setPrice(Long price) {
        this.price = price;
    }
    public Integer getNums() {
        return nums;
    }
    public void setNums(Integer nums) {
        this.nums = nums;
    }
    public String getImage() {
        return image;
    }
    public void setImage(String image) {
        this.image = image;
    }
    public Integer getStatus() {
        return status;
    }
    public void setStatus(Integer status) {
        this.status = status;
    }
    @Override
    public String toString() {
        return "Item [id=" + id + ", title=" + title + ", description=" + description + ", content="
                + content + ", price=" + price + ", nums=" + nums + ", image=" + image + ", status=" + status
                + "]";
    }
    
}
