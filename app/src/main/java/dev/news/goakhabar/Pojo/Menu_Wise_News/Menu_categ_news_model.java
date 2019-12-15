package dev.news.goakhabar.Pojo.Menu_Wise_News;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Raghvendra Sahu on 15-Dec-19.
 */
public class Menu_categ_news_model {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("count")
    @Expose
    private Integer count;
    @SerializedName("pages")
    @Expose
    private Integer pages;
//    @SerializedName("category")
//    @Expose
//    private Category category;
    @SerializedName("posts")
    @Expose
    private List<MenuPostNews> posts = null;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getPages() {
        return pages;
    }

    public void setPages(Integer pages) {
        this.pages = pages;
    }

//    public Category getCategory() {
//        return category;
//    }

//    public void setCategory(Category category) {
//        this.category = category;
//    }

    public List<MenuPostNews> getPosts() {
        return posts;
    }

    public void setPosts(List<MenuPostNews> posts) {
        this.posts = posts;
    }


}
