package dev.news.goakhabar.Pojo.LoginModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Raghvendra Sahu on 14-Dec-19.
 */
public class Login_model {
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("cookie")
    @Expose
    private String cookie;
    @SerializedName("cookie_name")
    @Expose
    private String cookieName;
    @SerializedName("user")
    @Expose
    private UserMdel user;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCookie() {
        return cookie;
    }

    public void setCookie(String cookie) {
        this.cookie = cookie;
    }

    public String getCookieName() {
        return cookieName;
    }

    public void setCookieName(String cookieName) {
        this.cookieName = cookieName;
    }

    public UserMdel getUser() {
        return user;
    }

    public void setUser(UserMdel user) {
        this.user = user;
    }

}
