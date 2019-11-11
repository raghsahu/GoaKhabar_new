package dev.news.goakhabar;

/**
 * Created by Raghvendra Sahu on 10-Nov-19.
 */
public class DrawerItem {
    private long iconImg;
    private String itemName;
    //private String itemDesc;
    private long iconId;


    public DrawerItem(long iconImg, String itemName, long iconId) {
        this.iconImg = iconImg;
        this.itemName = itemName;
        //this.itemDesc = itemDesc;
        this.iconId = iconId;
    }

    public long getIconImg() {
        return iconImg;
    }

    public void setIconImg(long iconImg) {
        this.iconImg = iconImg;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }


    public long getIconId() {
        return iconId;
    }

    public void setIconId(long iconId) {
        this.iconId = iconId;
    }
}
