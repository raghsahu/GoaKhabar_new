package dev.news.goakhabarr.Fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
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
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

import dev.news.goakhabarr.Adapter.MyAdapter;
import dev.news.goakhabarr.Activity.NewsDetailsActivity;
import dev.news.goakhabarr.R;
import dev.news.goakhabarr.Utils.Connectivity;

import static dev.news.goakhabarr.Activity.MainActivity.goa_video_id;
import static dev.news.goakhabarr.Fragment.FragmentHome.md5;

/**
 * Created by Raghvendra Sahu on 10-Nov-19.
 */
public class Fragment_Video extends Fragment {
    List<Object> list;
    Gson gson;
    Map<String,Object> mapPost;
    Map<String,Object> mapTitle;
    ListView postList;
    String postTitle[];
    int featured_media[];
    int postID;
    private AdView mAdView;
    private AdView mAdView1;
    AdRequest adRequest;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.video_fragment, container, false);

        getActivity().setTitle(R.string.home);
        postList = (ListView)view.findViewById(R.id.postList);


        //**************************************
        try {

            Log.d("mmm",goa_video_id.toString());
            String cate_url="http://www.goakhabar.com/wp-json/wp/v2/posts?categories="+goa_video_id;

            if (Connectivity.isConnected(getActivity())){
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
                featured_media[position]= ((Double) mapPost.get("featured_media")).intValue();

                Intent intent = new Intent(getActivity(), NewsDetailsActivity.class);
                intent.putExtra("id", ""+postID);
                intent.putExtra("video", "video");
                intent.putExtra("featured_media", "http://www.goakhabar.com/wp-json/wp/v2/media/"+featured_media[position]);
                startActivity(intent);
            }
        });

//***********************************************************
        //**********
        //banner ads initialize
        MobileAds.initialize(getActivity(), new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {}
        });


        mAdView = (AdView)view.findViewById(R.id.adView);
        mAdView1 = (AdView)view.findViewById(R.id.adView1);
        adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        mAdView1.loadAd(adRequest);

        //***************1st ads
        mAdView = new AdView(getActivity());
        mAdView.setAdSize(AdSize.SMART_BANNER);
        mAdView.setAdUnitId("ca-app-pub-5014601384589531/1947070900");
        AdRequest.Builder adRequest = new AdRequest.Builder();

        String ANDROID_ID = Settings.Secure.getString(getContext().getContentResolver(),
                Settings.Secure.ANDROID_ID);
        String deviceId = md5(ANDROID_ID).toUpperCase();
        Log.e("ANDROID_ID", ""+ANDROID_ID);
        Log.e("deviceId", ""+deviceId);

        adRequest.addTestDevice(deviceId);


        AdRequest request = adRequest.build();
        mAdView.loadAd(request);

        mAdView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                Log.e("ban_adsload", "Banner");
                // Toast.makeText(getActivity(), "Banner ads", Toast.LENGTH_SHORT).show();
                // Code to be executed when an ad finishes loading.
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                Log.e("ban_ads", ""+errorCode);
                //Toast.makeText(getActivity(), "Banner fail", Toast.LENGTH_SHORT).show();
                // Code to be executed when an ad request fails.
            }

            @Override
            public void onAdOpened() {
                Log.e("ban_adsopen", "Banner");
                // Toast.makeText(getActivity(), "Banner open", Toast.LENGTH_SHORT).show();
                // Code to be executed when an ad opens an overlay that
                // covers the screen.
            }

            @Override
            public void onAdClicked() {
                Log.e("ban_adsclick", "Banner");
                //Toast.makeText(getActivity(), "Banner click", Toast.LENGTH_SHORT).show();
                // Code to be executed when the user clicks on an ad.
            }

            @Override
            public void onAdLeftApplication() {
                Log.e("ban_adsleft", "Banner");
                //  Toast.makeText(getActivity(), "Banner left", Toast.LENGTH_SHORT).show();
                // Code to be executed when the user has left the app.
            }

            @Override
            public void onAdClosed() {
                Log.e("ban_adsclose", "Banner");
                //Toast.makeText(getActivity(), "Banner close", Toast.LENGTH_SHORT).show();
                // Code to be executed when the user is about to return
                // to the app after tapping on an ad.
            }
        });

        //******************2nd ads****************************************
        mAdView1 = new AdView(getActivity());
        mAdView1.setAdSize(AdSize.SMART_BANNER);
        mAdView1.setAdUnitId("ca-app-pub-5014601384589531/1947070900");


        adRequest.addTestDevice(deviceId);

        mAdView1.loadAd(request);

        mAdView1.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                Log.e("ban_adsload", "Banner");
                // Toast.makeText(getActivity(), "Banner ads", Toast.LENGTH_SHORT).show();
                // Code to be executed when an ad finishes loading.
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                Log.e("ban_ads", ""+errorCode);
                //Toast.makeText(getActivity(), "Banner fail", Toast.LENGTH_SHORT).show();
                // Code to be executed when an ad request fails.
            }

            @Override
            public void onAdOpened() {
                Log.e("ban_adsopen", "Banner");
                // Toast.makeText(getActivity(), "Banner open", Toast.LENGTH_SHORT).show();
                // Code to be executed when an ad opens an overlay that
                // covers the screen.
            }

            @Override
            public void onAdClicked() {
                Log.e("ban_adsclick", "Banner");
                //Toast.makeText(getActivity(), "Banner click", Toast.LENGTH_SHORT).show();
                // Code to be executed when the user clicks on an ad.
            }

            @Override
            public void onAdLeftApplication() {
                Log.e("ban_adsleft", "Banner");
                //  Toast.makeText(getActivity(), "Banner left", Toast.LENGTH_SHORT).show();
                // Code to be executed when the user has left the app.
            }

            @Override
            public void onAdClosed() {
                Log.e("ban_adsclose", "Banner");
                //Toast.makeText(getActivity(), "Banner close", Toast.LENGTH_SHORT).show();
                // Code to be executed when the user is about to return
                // to the app after tapping on an ad.
            }
        });





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
