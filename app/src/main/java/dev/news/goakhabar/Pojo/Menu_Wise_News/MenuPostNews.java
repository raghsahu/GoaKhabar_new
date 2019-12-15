package dev.news.goakhabar.Pojo.Menu_Wise_News;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Raghvendra Sahu on 15-Dec-19.
 */
public class MenuPostNews {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("slug")
    @Expose
    private String slug;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("title_plain")
    @Expose
    private String titlePlain;
    @SerializedName("content")
    @Expose
    private String content;
    @SerializedName("excerpt")
    @Expose
    private String excerpt;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("modified")
    @Expose
    private String modified;
//    @SerializedName("categories")
//    @Expose
//    private List<Category_> categories = null;
//    @SerializedName("tags")
//    @Expose
//    private List<Object> tags = null;
//    @SerializedName("author")
//    @Expose
//    private Author author;
//    @SerializedName("comments")
//    @Expose
//    private List<Object> comments = null;
    @SerializedName("attachments")
    @Expose
    private List<MenuNewsAttachment> attachments = null;
    @SerializedName("comment_count")
    @Expose
    private Integer commentCount;
    @SerializedName("comment_status")
    @Expose
    private String commentStatus;
    @SerializedName("thumbnail")
    @Expose
    private String thumbnail;
//    @SerializedName("custom_fields")
//    @Expose
//    private CustomFields customFields;
    @SerializedName("thumbnail_size")
    @Expose
    private String thumbnailSize;
//    @SerializedName("thumbnail_images")
//    @Expose
//    private ThumbnailImages thumbnailImages;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitlePlain() {
        return titlePlain;
    }

    public void setTitlePlain(String titlePlain) {
        this.titlePlain = titlePlain;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getExcerpt() {
        return excerpt;
    }

    public void setExcerpt(String excerpt) {
        this.excerpt = excerpt;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getModified() {
        return modified;
    }

    public void setModified(String modified) {
        this.modified = modified;
    }

//    public List<Category_> getCategories() {
//        return categories;
//    }
//
//    public void setCategories(List<Category_> categories) {
//        this.categories = categories;
//    }
//
//    public List<Object> getTags() {
//        return tags;
//    }
//
//    public void setTags(List<Object> tags) {
//        this.tags = tags;
//    }
//
//    public Author getAuthor() {
//        return author;
//    }
//
//    public void setAuthor(Author author) {
//        this.author = author;
//    }
//
//    public List<Object> getComments() {
//        return comments;
//    }
//
//    public void setComments(List<Object> comments) {
//        this.comments = comments;
//    }

    public List<MenuNewsAttachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<MenuNewsAttachment> attachments) {
        this.attachments = attachments;
    }

    public Integer getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(Integer commentCount) {
        this.commentCount = commentCount;
    }

    public String getCommentStatus() {
        return commentStatus;
    }

    public void setCommentStatus(String commentStatus) {
        this.commentStatus = commentStatus;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

//    public CustomFields getCustomFields() {
//        return customFields;
//    }
//
//    public void setCustomFields(CustomFields customFields) {
//        this.customFields = customFields;
//    }

    public String getThumbnailSize() {
        return thumbnailSize;
    }

    public void setThumbnailSize(String thumbnailSize) {
        this.thumbnailSize = thumbnailSize;
    }

//    public ThumbnailImages getThumbnailImages() {
//        return thumbnailImages;
//    }
//
//    public void setThumbnailImages(ThumbnailImages thumbnailImages) {
//        this.thumbnailImages = thumbnailImages;
//    }

}
