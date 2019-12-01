package dev.news.goakhabar.Pojo.Category_Home;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import dev.news.goakhabar.Pojo.Category_Home.Links;
import dev.news.goakhabar.Pojo.Category_Home.MetaModel;

/**
 * Created by Raghvendra Sahu on 25-Nov-19.
 */
public class Category_Home_Model {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("count")
    @Expose
    private Integer count;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("link")
    @Expose
    private String link;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("slug")
    @Expose
    private String slug;
    @SerializedName("taxonomy")
    @Expose
    private String taxonomy;
    @SerializedName("parent")
    @Expose
    private Integer parent;
    @SerializedName("meta")
    @Expose
    private List<MetaModel> meta = null;
    @SerializedName("_links")
    @Expose
    private Links links;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getTaxonomy() {
        return taxonomy;
    }

    public void setTaxonomy(String taxonomy) {
        this.taxonomy = taxonomy;
    }

    public Integer getParent() {
        return parent;
    }

    public void setParent(Integer parent) {
        this.parent = parent;
    }

    public List<MetaModel> getMeta() {
        return meta;
    }

    public void setMeta(List<MetaModel> meta) {
        this.meta = meta;
    }

    public Links getLinks() {
        return links;
    }

    public void setLinks(Links links) {
        this.links = links;
    }


}
