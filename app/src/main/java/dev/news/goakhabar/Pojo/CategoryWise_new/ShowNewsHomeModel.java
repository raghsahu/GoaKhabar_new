package dev.news.goakhabar.Pojo.CategoryWise_new;

/**
 * Created by Raghvendra Sahu on 30-Nov-19.
 */
public class ShowNewsHomeModel {
    String rendered;
    String tempdetails;
    String href;

    public ShowNewsHomeModel(String rendered, String tempdetails, String href) {
        this.rendered = rendered;
        this.tempdetails = tempdetails;
        this.href = href;
    }



    public String getRendered() {
        return rendered;
    }

    public void setRendered(String rendered) {
        this.rendered = rendered;
    }

    public String getTempdetails() {
        return tempdetails;
    }

    public void setTempdetails(String tempdetails) {
        this.tempdetails = tempdetails;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }
}
