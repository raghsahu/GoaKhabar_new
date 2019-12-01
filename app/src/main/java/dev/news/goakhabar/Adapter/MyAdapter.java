package dev.news.goakhabar.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import dev.news.goakhabar.R;

/**
 * Created by Raghvendra Sahu on 01-Dec-19.
 */
public class MyAdapter extends ArrayAdapter<Object> {

    List<Object> list = new ArrayList<>();
    Map<String,Object> mapPost;
    Map<String,Object> mapTitle;
    String postTitle[];

    public MyAdapter(Context context, int textViewResourceId, List<Object> objects) {
        super(context, textViewResourceId, objects);
        list = objects;
    }

    @Override
    public int getCount() {
        return super.getCount();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        v = inflater.inflate(R.layout.list_view_items, null);
        TextView textView = (TextView) v.findViewById(R.id.textView);
        ImageView imageView = (ImageView) v.findViewById(R.id.imageView);



        postTitle = new String[list.size()];

        for(int i=0;i<list.size();++i){
            mapPost = (Map<String,Object>)list.get(i);
            mapTitle = (Map<String, Object>) mapPost.get("title");
            postTitle[i] = (String) mapTitle.get("rendered");


        }
        textView.setText(postTitle[position]);
       // imageView.setImageResource(animalList.get(position).getAnimalImage());

        Log.e("post_title",postTitle[position].toString());

        return v;

    }
}
