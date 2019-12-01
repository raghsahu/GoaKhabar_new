package dev.news.goakhabar.Pojo.CategoryWise_new;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Raghvendra Sahu on 30-Nov-19.
 */
public class PredecessorVersionModel {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("href")
    @Expose
    private String href;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }
}
