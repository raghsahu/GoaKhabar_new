package dev.news.goakhabar.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.PopupMenu;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import dev.news.goakhabar.NewsDetailsActivity;
import dev.news.goakhabar.R;

/**
 * Created by Raghvendra Sahu on 01-Dec-19.
 */
public class MyAdapter extends ArrayAdapter<Object> {

    List<Object> list = new ArrayList<>();
    Map<String,Object> mapPost;
    Map<String,Object> mapTitle;
    Map<String,Object> mapLink;
    String postTitle[];
    String postLink[];
    int featured_media[];
    int postID;

    public MyAdapter(Context context, int textViewResourceId, List<Object> objects) {
        super(context, textViewResourceId, objects);
        list = objects;
    }

    @Override
    public int getCount() {
        return super.getCount();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        View v = convertView;
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        v = inflater.inflate(R.layout.news_item, null);
        TextView textView = (TextView) v.findViewById(R.id.tv_title);
        final TextView textViewOptions = (TextView) v.findViewById(R.id.textViewOptions);
        ImageView imageView = (ImageView) v.findViewById(R.id.news1);

        postTitle = new String[list.size()];
        postLink = new String[list.size()];
        featured_media = new int[list.size()];

        for(int i=0;i<list.size();++i){
            mapPost = (Map<String,Object>)list.get(i);
            mapTitle = (Map<String, Object>) mapPost.get("title");
            //mapLink = (Map<String, Object>) mapPost.get("link");
            postTitle[i] = (String) mapTitle.get("rendered");
            postLink[i]= (String)mapPost.get("link");
            featured_media[i]= ((Double) mapPost.get("featured_media")).intValue();


        }

       Log.e("featured_media",""+featured_media[position]);
        Log.e("links_news",""+postLink[position]);
        textView.setText(postTitle[position]);
       // imageView.setImageResource(animalList.get(position).getAnimalImage());

        Log.e("post_title",postTitle[position].toString());

//        textView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                mapPost = (Map<String,Object>)list.get(position);
//                postID = ((Double)mapPost.get("id")).intValue();
//
//                Intent intent = new Intent(getContext(), NewsDetailsActivity.class);
//                intent.putExtra("id", ""+postID);
//                getContext().startActivity(intent);
//
//            }
//
//        });

        textViewOptions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //creating a popup menu
                PopupMenu popup = new PopupMenu(getContext(), textViewOptions);
                //inflating menu from xml resource
                popup.inflate(R.menu.options_menu);
                //adding click listener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.navigation_share:
                                //handle menu1 click

                                mapPost = (Map<String,Object>)list.get(position);
                                postID = ((Double)mapPost.get("id")).intValue();
               //  String url = "http://www.goakhabar.com/wp-json/wp/v2/posts/"+postID+"?fields=title,content";
                String links=postLink[position];
                String title=postTitle[position];
                                ShareNews(links,title);
                                return true;
                            case R.id.navigation_bookmark:
                                //handle menu2 click
                                return true;

                            default:
                                return false;
                        }
                    }
                });
                //displaying the popup
                popup.show();

            }
        });




        return v;

    }

    private void ShareNews(String url, String title) {

        try {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, "GoaKhabar");
            String shareMessage= title+"\n";
            shareMessage = shareMessage + url;
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
            getContext().startActivity(Intent.createChooser(shareIntent, "choose one"));
        } catch(Exception e) {
            //e.toString();
        }
    }
}
