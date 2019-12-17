package dev.news.goakhabar.Pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Raghvendra Sahu on 17-Dec-19.
 */
public class Signup_model {


    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("controller")
    @Expose
    private String controller;
    @SerializedName("method")
    @Expose
    private String method;
    @SerializedName("nonce")
    @Expose
    private String nonce;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getController() {
        return controller;
    }

    public void setController(String controller) {
        this.controller = controller;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getNonce() {
        return nonce;
    }

    public void setNonce(String nonce) {
        this.nonce = nonce;
    }
}
