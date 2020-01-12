package dev.news.goakhabarr.Pojo;

/**
 * Created by Raghvendra Sahu on 10-Nov-19.
 */
public class DrawerItem {
    private long iconImg;
    private String itemName;
   private String item_id;
    private long iconId;


    public DrawerItem(long iconImg, String itemName, long iconId) {
        this.iconImg = iconImg;
        this.itemName = itemName;
        this.iconId = iconId;
    }


    public DrawerItem(String s_id, String s_name) {
        this.itemName = s_name;
        this.item_id = s_id;
    }


    public String getItem_id() {
        return item_id;
    }

    public void setItem_id(String item_id) {
        this.item_id = item_id;
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
