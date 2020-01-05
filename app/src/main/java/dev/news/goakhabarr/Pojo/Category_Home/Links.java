package dev.news.goakhabarr.Pojo.Category_Home;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Raghvendra Sahu on 25-Nov-19.
 */
public class Links {

    @SerializedName("self")
    @Expose
    private List<SelfModel> self = null;
    @SerializedName("collection")
    @Expose
    private List<CollectionModel> collection = null;
    @SerializedName("about")
    @Expose
    private List<AboutModel> about = null;
    @SerializedName("wp:post_type")
    @Expose
    private List<WpPostType_Model> wpPostType = null;
    @SerializedName("curies")
    @Expose
    private List<CuryModel> curies = null;

    public List<SelfModel> getSelf() {
        return self;
    }

    public void setSelf(List<SelfModel> self) {
        this.self = self;
    }

    public List<CollectionModel> getCollection() {
        return collection;
    }

    public void setCollection(List<CollectionModel> collection) {
        this.collection = collection;
    }

    public List<AboutModel> getAbout() {
        return about;
    }

    public void setAbout(List<AboutModel> about) {
        this.about = about;
    }

    public List<WpPostType_Model> getWpPostType() {
        return wpPostType;
    }

    public void setWpPostType(List<WpPostType_Model> wpPostType) {
        this.wpPostType = wpPostType;
    }

    public List<CuryModel> getCuries() {
        return curies;
    }

    public void setCuries(List<CuryModel> curies) {
        this.curies = curies;
    }
}
