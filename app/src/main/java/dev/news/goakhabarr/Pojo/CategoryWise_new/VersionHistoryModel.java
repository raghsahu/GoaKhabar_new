package dev.news.goakhabarr.Pojo.CategoryWise_new;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Raghvendra Sahu on 30-Nov-19.
 */
public class VersionHistoryModel {
    @SerializedName("count")
    @Expose
    private Integer count;
    @SerializedName("href")
    @Expose
    private String href;

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }
}
