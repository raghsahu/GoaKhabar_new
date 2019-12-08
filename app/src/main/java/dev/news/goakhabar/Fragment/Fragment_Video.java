package dev.news.goakhabar.Fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.ServerError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

import dev.news.goakhabar.Adapter.MyAdapter;
import dev.news.goakhabar.CommentActivity;
import dev.news.goakhabar.NewsDetailsActivity;
import dev.news.goakhabar.R;
import dev.news.goakhabar.Utils.Connectivity;
import dev.news.goakhabar.VideoPlayActivity;

import static dev.news.goakhabar.Fragment.FragmentHome.goa_video_id;

/**
 * Created by Raghvendra Sahu on 10-Nov-19.
 */
public class Fragment_Video extends Fragment {
    LinearLayout ll_vid_play;
    List<Object> list;
    Gson gson;
    Map<String,Object> mapPost;
    Map<String,Object> mapTitle;
    ListView postList;
    String postTitle[];
    int featured_media[];
    int postID;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.video_fragment, container, false);

        getActivity().setTitle(R.string.home);
        // backpress = (ImageView) view.findViewById(R.id.back_press);
         ll_vid_play =  view.findViewById(R.id.ll_vid_play);
        postList = (ListView)view.findViewById(R.id.postList);


//        backpress.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ((MainActivity)getActivity()).onBackPressed();
//            }
//        });
        ll_vid_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(getActivity(), VideoPlayActivity.class);
                startActivity(intent);

            }
        });
//

        //**************************************
        try {

            Log.d("mmm",goa_video_id.toString());
            String cate_url="http://www.goakhabar.com/wp-json/wp/v2/posts?categories="+goa_video_id;

            if (Connectivity.isConnected(getActivity())){
                //GetCategoryNews(id);
                getpost(cate_url);
            }else Toast.makeText(getActivity(), "Please check Internet", Toast.LENGTH_SHORT).show();
        }catch (Exception e){

        }

        //*********onclick list view
        postList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mapPost = (Map<String,Object>)list.get(position);
                postID = ((Double)mapPost.get("id")).intValue();

                Intent intent = new Intent(getActivity(), NewsDetailsActivity.class);
                intent.putExtra("id", ""+postID);
                startActivity(intent);
            }
        });

//***********************************************************




        return view;
    }

    private void getpost(String cate_url) {

        final ProgressDialog progressDialog = new ProgressDialog(getActivity(),R.style.MyGravity);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, cate_url,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        progressDialog.dismiss();
                        Log.d("kkkk", s.toString());

                        try {

                            gson = new Gson();
                            list = (List) gson.fromJson(s, List.class);

                            postTitle = new String[list.size()];
                            featured_media = new int[list.size()];

                            for(int i=0;i<list.size();++i){
                                mapPost = (Map<String,Object>)list.get(i);
                                mapTitle = (Map<String, Object>) mapPost.get("title");
                                postTitle[i] = (String) mapTitle.get("rendered");
                                featured_media[i]= ((Double) mapPost.get("featured_media")).intValue();

                                // showNewsHomeModels.add(i, new ShowNewsHomeModel(   mapTitle.get("rendered")   ));

                            }
                            MyAdapter myAdapter=new MyAdapter(getActivity(),R.layout.list_view_items,list);
                            postList.setAdapter(myAdapter);

                        }catch (Exception e){

                        }

                    }
                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("k_error", error.toString());
                        progressDialog.dismiss();
                        // Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();

                        NetworkResponse response = error.networkResponse;
                        if (error instanceof ServerError && response != null) {
                            try {
                                String res = new String(response.data,
                                        HttpHeaderParser.parseCharset(response.headers, "utf-8"));
                                // Now you can use any deserializer to make sense of data
                                JSONObject obj = new JSONObject(res);
                            } catch (UnsupportedEncodingException e1) {
                                // Couldn't properly decode data to string
                                e1.printStackTrace();
                            } catch (JSONException e2) {
                                // returned data is not JSONObject?
                                e2.printStackTrace();
                            }
                        }



                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        //adding the string request to request queue
        requestQueue.add(stringRequest);

    }
}
