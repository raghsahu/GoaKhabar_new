package dev.news.goakhabar.Pojo.CategoryWise_new;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import dev.news.goakhabar.Pojo.Category_Home.AboutModel;
import dev.news.goakhabar.Pojo.Category_Home.CollectionModel;
import dev.news.goakhabar.Pojo.Category_Home.CuryModel;
import dev.news.goakhabar.Pojo.Category_Home.SelfModel;

/**
 * Created by Raghvendra Sahu on 30-Nov-19.
 */
public class LinksHomeCatWise {

    @SerializedName("self")
    @Expose
    private List<SelfModel> self = null;
    @SerializedName("collection")
    @Expose
    private List<CollectionModel> collection = null;
    @SerializedName("about")
    @Expose
    private List<AboutModel> about = null;
    @SerializedName("author")
    @Expose
    private List<AuthorModel> author = null;
    @SerializedName("replies")
    @Expose
    private List<ReplyModel> replies = null;
    @SerializedName("version-history")
    @Expose
    private List<VersionHistoryModel> versionHistory = null;
    @SerializedName("predecessor-version")
    @Expose
    private List<PredecessorVersionModel> predecessorVersion = null;
    @SerializedName("wp:featuredmedia")
    @Expose
    private List<WpFeaturedmediumModel> wpFeaturedmedia = null;
    @SerializedName("wp:attachment")
    @Expose
    private List<WpAttachmentModel> wpAttachment = null;
    @SerializedName("wp:term")
    @Expose
    private List<WpTermModel> wpTerm = null;
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

    public List<AuthorModel> getAuthor() {
        return author;
    }

    public void setAuthor(List<AuthorModel> author) {
        this.author = author;
    }

    public List<ReplyModel> getReplies() {
        return replies;
    }

    public void setReplies(List<ReplyModel> replies) {
        this.replies = replies;
    }

    public List<VersionHistoryModel> getVersionHistory() {
        return versionHistory;
    }

    public void setVersionHistory(List<VersionHistoryModel> versionHistory) {
        this.versionHistory = versionHistory;
    }

    public List<PredecessorVersionModel> getPredecessorVersion() {
        return predecessorVersion;
    }

    public void setPredecessorVersion(List<PredecessorVersionModel> predecessorVersion) {
        this.predecessorVersion = predecessorVersion;
    }

    public List<WpFeaturedmediumModel> getWpFeaturedmedia() {
        return wpFeaturedmedia;
    }

    public void setWpFeaturedmedia(List<WpFeaturedmediumModel> wpFeaturedmedia) {
        this.wpFeaturedmedia = wpFeaturedmedia;
    }

    public List<WpAttachmentModel> getWpAttachment() {
        return wpAttachment;
    }

    public void setWpAttachment(List<WpAttachmentModel> wpAttachment) {
        this.wpAttachment = wpAttachment;
    }

    public List<WpTermModel> getWpTerm() {
        return wpTerm;
    }

    public void setWpTerm(List<WpTermModel> wpTerm) {
        this.wpTerm = wpTerm;
    }

    public List<CuryModel> getCuries() {
        return curies;
    }

    public void setCuries(List<CuryModel> curies) {
        this.curies = curies;
    }

}
