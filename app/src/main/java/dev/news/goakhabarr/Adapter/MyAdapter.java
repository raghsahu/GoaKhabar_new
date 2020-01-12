package dev.news.goakhabarr.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.PopupMenu;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.ServerError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import dev.news.goakhabarr.R;

/**
 * Created by Raghvendra Sahu on 01-Dec-19.
 */
public class MyAdapter extends ArrayAdapter<Object> {

    List<Object> list = new ArrayList<>();
    List<String> media_img = new ArrayList<>();
    Map<String,Object> mapPost;
    Map<String,Object> mapPost1;
    Map<String,Object> mapTitle;
    Map<String,Object> mapGuide;
    Map<String,Object> mapLink;
    String postTitle[];
    String postLink[];
    int featured_media[];
    int postID;
    Gson gson;
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

        try {
            media_img.clear();
        }catch (Exception e){

        }

        for(int i=0;i<list.size();++i){
            mapPost = (Map<String,Object>)list.get(i);
            mapTitle = (Map<String, Object>) mapPost.get("title");
            //mapLink = (Map<String, Object>) mapPost.get("link");
            postTitle[i] = (String) mapTitle.get("rendered");
            postLink[i]= (String)mapPost.get("link");
            featured_media[i]= ((Double) mapPost.get("featured_media")).intValue();


        }

        String img_url="http://www.goakhabar.com/wp-json/wp/v2/media/"+featured_media[position];
        FindImage(img_url,imageView);

       Log.e("featured_media",""+featured_media[position]);
        Log.e("links_news",""+postLink[position]);
        textView.setText(postTitle[position]);
       // imageView.setImageResource(animalList.get(position).getAnimalImage());

        Log.e("post_title",postTitle[position].toString());


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

                               // try {
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

    private void FindImage(String img_url, final ImageView imageView) {

        final StringRequest stringRequest = new StringRequest(Request.Method.GET, img_url,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                       // progressDialog.dismiss();
                        Log.d("kkkk", s.toString());
                        gson = new Gson();
//                        list1 = (List) gson.fromJson(s, List.class);

                        try {
                            JSONObject object = new JSONObject(s);
                            JSONObject jsonObject=object.getJSONObject("guid");
                            String rendered=jsonObject.getString("rendered");
                            Log.d("img_media", rendered);

                            media_img.add(rendered);

                            Glide.with(getContext())
                                    .load(rendered)
                                    .override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                                    //.error(R.drawable.glide_app_img_loader)
                                    .listener(new RequestListener<Drawable>() {
                                        @Override
                                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                            return false;
                                        }

                                        @Override
                                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                            return false;
                                        }
                                    }).into(imageView);

                        }catch (Exception e){

                        }


                    }
                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("k_error", error.toString());
                       // progressDialog.dismiss();
                        // Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();

                        NetworkResponse response = error.networkResponse;
                        if (error instanceof ServerError && response != null) {
                            try {
                                String res = new String(response.data,
                                        HttpHeaderParser.parseCharset(response.headers, "utf-8"));
                                // Now you can use any deserializer to make sense of data
                               // JSONObject obj = new JSONObject(res);
                            } catch (UnsupportedEncodingException e1) {
                                // Couldn't properly decode data to string
                                e1.printStackTrace();
                            }
                        }



                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        //adding the string request to request queue
        requestQueue.add(stringRequest);

    }

    private void ShareNews(String url, String title) {

        try {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, "GoaKhabar");
            shareIntent.putExtra(Intent.EXTRA_TEXT, url);
            getContext().startActivity(Intent.createChooser(shareIntent, "choose one"));

        } catch(Exception e) {
            //e.toString();
        }
    }
}
