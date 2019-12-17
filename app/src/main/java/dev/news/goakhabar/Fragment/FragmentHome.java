package dev.news.goakhabar.Fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.PopupMenu;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.ServerError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import dev.news.goakhabar.Adapter.HomeAdapter;
import dev.news.goakhabar.Adapter.HomeNewsAdapter;
import dev.news.goakhabar.Adapter.MyAdapter;
import dev.news.goakhabar.Api_Call.APIClient;
import dev.news.goakhabar.Api_Call.APIClient1;
import dev.news.goakhabar.Api_Call.ApiClient2;
import dev.news.goakhabar.Api_Call.Api_Call;
import dev.news.goakhabar.MainActivity;
import dev.news.goakhabar.Pojo.DrawerItem;
import dev.news.goakhabar.NewsDetailsActivity;
import dev.news.goakhabar.Pojo.CategoryWise_new.Home_categ_news_model;
import dev.news.goakhabar.Pojo.CategoryWise_new.ShowNewsHomeModel;
import dev.news.goakhabar.Pojo.Category_Home.Category_Home_Model;
import dev.news.goakhabar.Pojo.Menu_Wise_News.Menu_categ_news_model;
import dev.news.goakhabar.R;
import dev.news.goakhabar.Utils.Connectivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static dev.news.goakhabar.MainActivity.iv_logo;
import static dev.news.goakhabar.MainActivity.tv_title;

/**
 * Created by Raghvendra Sahu on 10-Nov-19.
 */
public class FragmentHome extends Fragment implements TabLayout.OnTabSelectedListener{
    LinearLayout ll_news_details1,ll_news_details;
    public TabLayout tabLayout;
    SwipeRefreshLayout pullToRefresh;
    public ArrayList<DrawerItem> List_Item=new ArrayList<>();

    ImageView iv_option;
    TextView txt_breaking;
    RecyclerView recycler_news;
   List<Category_Home_Model> dataArrayList;
    HomeNewsAdapter homeNewsAdapter;
    List<Home_categ_news_model> catByNews = new ArrayList<>();
   //List<Post_Home_Model> postArrayList=new ArrayList<>();
   HomeAdapter homeAdapter;
    ListView postList;
    private ArrayList<ShowNewsHomeModel> showNewsHomeModels=new ArrayList<>();
    List<Object> list;
    Gson gson;
    ProgressDialog progressDialog;
    //ListView postList;
    Map<String,Object> mapPost;
    Map<String,Object> mapTitle;
    int postID;
    String postTitle[];
    int featured_media[];
    public static Integer goa_video_id;
  

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        getActivity().setTitle(R.string.home);
        ll_news_details1=view.findViewById(R.id.ll_news_details1);
        postList = (ListView)view.findViewById(R.id.postList);
        ll_news_details=view.findViewById(R.id.ll_news_details);
        iv_option=view.findViewById(R.id.iv_option);
        tabLayout = (TabLayout) view.findViewById(R.id.tabLayout);
        txt_breaking = view.findViewById(R.id.text);
        recycler_news = view.findViewById(R.id.recycler_news);
       //pullToRefresh = view.findViewById(R.id.pullToRefresh);
        final TextView textViewOptions = view.findViewById(R.id.textViewOptions);
        txt_breaking.setSelected(true);

        try {
        //    txt_breaking.setText("ब्रेकिंग न्यूज़:  "+toPrint);
        }catch (Exception e){

        }

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
       // backpress = (ImageView) view.findViewById(R.id.back_press);

//        backpress.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ((MainActivity)getActivity()).onBackPressed();
//            }
//        });
//
        iv_logo.setVisibility(View.VISIBLE);
        tv_title.setVisibility(View.GONE);

        //****************************************************
        if (Connectivity.isConnected(getContext())){
            getMenuNews("ब्रेकिंग-न्यूज़");
           getCategory();
           // getpost();

        }else {
            Toast.makeText(getActivity(), "Please check Internet", Toast.LENGTH_SHORT).show();
        }


        //*******************************************************
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
        txt_breaking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Fragment fragment2 = new News_Fragment();
                tv_title.setText("ब्रेकिंग-न्यूज़");
                iv_logo.setVisibility(View.GONE);
                tv_title.setVisibility(View.VISIBLE);
                Bundle bundle = new Bundle();
                bundle.putString("Title", "ब्रेकिंग-न्यूज़");
                FragmentManager fragmentManager2 = getActivity().getSupportFragmentManager();
                fragmentManager2.beginTransaction().replace(R.id.frame, fragment2).commit();
                fragment2.setArguments(bundle);

            }
        });

        iv_option.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //creating a popup menu
                PopupMenu popup = new PopupMenu(getActivity(), iv_option);
                //inflating menu from xml resource
                popup.inflate(R.menu.options_menu);
                //adding click listener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.navigation_share:
                                //handle menu1 click
                                ShareNews();
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

        textViewOptions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //creating a popup menu
                PopupMenu popup = new PopupMenu(getActivity(), textViewOptions);
                //inflating menu from xml resource
                popup.inflate(R.menu.options_menu);
                //adding click listener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.navigation_share:
                                //handle menu1 click
                                ShareNews();
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




        ll_news_details1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Fragment  fragment1 = new News_Details_frament();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.frame, fragment1).commit();

            }
        });


        ll_news_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment  fragment1 = new News_Details_frament();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.frame, fragment1).commit();
                fragmentManager.beginTransaction().addToBackStack(null);
            }
        });


        //*********************************************
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                // viewPager.setCurrentItem(tab.getPosition());
                int position = tab.getPosition();
                Integer id = dataArrayList.get(position).getId();

                Log.d("mmm",id.toString());
                String cate_url="http://www.goakhabar.com/wp-json/wp/v2/posts?categories="+id;

                if (Connectivity.isConnected(getActivity())){
                    //GetCategoryNews(id);
                    getpost(cate_url);
                }else Toast.makeText(getActivity(), "Please check Internet", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }

        });


        return view;
    }

    private void getMenuNews(String s) {

        final ProgressDialog progressDialog = new ProgressDialog(getActivity(),R.style.MyGravity);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.show();

        Api_Call apiInterface = ApiClient2.getClient().create(Api_Call.class);

        Call<Menu_categ_news_model> call = apiInterface.GetHeaderNews(s);

        call.enqueue(new Callback<Menu_categ_news_model>() {
            @Override
            public void onResponse(Call<Menu_categ_news_model> call, Response<Menu_categ_news_model> response) {
                progressDialog.dismiss();
                try{

                    if (response!=null){
                        Log.e("breaking_title_res", response.body().getStatus());

                        if (!response.body().getStatus().equalsIgnoreCase("error")){
                            for (int i=0; i<response.body().getPosts().size();i++){
                                Log.e("breaking_title", response.body().getPosts().get(i).getTitle());

                                //String breaking_news= " "+response.body().getPosts().get(i).getTitle();


                                // String result = String.join(", ", response.body().getPosts().get(i).getTitle());
                                String toPrint = "";
                                toPrint += response.body().getPosts().get(i).getTitle() + " , ";
                                Log.e("breaking_result",""+toPrint);

                                txt_breaking.setText("ब्रेकिंग न्यूज़:  "+toPrint);


                            }


                        }else {
                            // Toast.makeText(MainActivity.this, "No news", Toast.LENGTH_SHORT).show();
                        }


                    }
                }catch (Exception e){
                    Log.e("error_breaking_title", e.getMessage());
                }
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<Menu_categ_news_model> call, Throwable t) {
                progressDialog.dismiss();
                Log.e("error_category1",t.getMessage());
                //Toast.makeText(AllCountries.this, ""+t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }

    private void GetCategoryNews(int id) {

        final ProgressDialog progressDialog = new ProgressDialog(getActivity(),R.style.MyGravity);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.show();

        Api_Call apiInterface = APIClient1.getClient().create(Api_Call.class);

        Call<List<Home_categ_news_model>> call = apiInterface.GetCategoryNews(id);

        call.enqueue(new Callback<List<Home_categ_news_model>>() {
            @Override
            public void onResponse(Call<List<Home_categ_news_model>> call, Response<List<Home_categ_news_model>> response) {

                try{
                    catByNews.clear();
                    if (response!=null){
                        catByNews= response.body();
                        for (int i=0; i<catByNews.size();i++){

                            Log.e("main ", " title "+ response.body().get(i).getTitle().getRendered() + " "+
                                    response.body().get(i).getId());

                            String tempdetails =  response.body().get(i).getExcerpt().getRendered().toString();
                            tempdetails = tempdetails.replace("<p>","");
                            tempdetails = tempdetails.replace("</p>","");
                            tempdetails = tempdetails.replace("[&hellip;]","");

                                                                        //image nikalna hai
//                            showNewsHomeModels.add(i, new ShowNewsHomeModel(   response.body().get(i).getTitle().getRendered(),
//                                    tempdetails,
//                                    response.body().get(i).getLinks().getWpFeaturedmedia().get(0).getHref())  );
//

                        }
                       // Log.e("show_news_model",""+showNewsHomeModels.size());


//                        homeNewsAdapter = new HomeNewsAdapter( catByNews, getActivity());
//                        recycler_news.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
//                        recycler_news.setAdapter(homeNewsAdapter);
//                        //recycler_news.setFocusable(false);
//                        homeNewsAdapter.notifyDataSetChanged();

                    }
                }catch (Exception e){
                    Log.e("error_cate1", e.getMessage());
                }
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<List<Home_categ_news_model>> call, Throwable t) {
                progressDialog.dismiss();
                Log.e("error_category1",t.getMessage());
                //Toast.makeText(getActivity(), ""+t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });



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


                       // postList.setAdapter(new ArrayAdapter(getActivity(),android.R.layout.simple_list_item_1,postTitle));

//                        homeNewsAdapter = new HomeNewsAdapter( list, getActivity());
//                        recycler_news.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
//                        recycler_news.setAdapter(homeNewsAdapter);
//                        recycler_news.setFocusable(false);
//                        homeNewsAdapter.notifyDataSetChanged();



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


    private void openOption() {
    }

    private void ShareNews() {

        try {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, "My application name");
            String shareMessage= "\nबजाज ऑटोने गोव्यात सादर केली ऑल न्यू चेतक\n\n";
            shareMessage = shareMessage + "http://www.goakhabar.com/";
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
            startActivity(Intent.createChooser(shareIntent, "choose one"));
        } catch(Exception e) {
            //e.toString();
        }
    }


    private void getCategory() {
        dataArrayList = new ArrayList<>();
        final ProgressDialog progressDialog = new ProgressDialog(getActivity(),R.style.MyGravity);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.show();

        Api_Call apiInterface = APIClient.getClient().create(Api_Call.class);

        Call<List<Category_Home_Model>> call = apiInterface.GetCategory();

        call.enqueue(new Callback<List<Category_Home_Model>>() {
            @Override
            public void onResponse(Call<List<Category_Home_Model>> call, Response<List<Category_Home_Model>> response) {

                try{
                    if (response!=null){
                        dataArrayList= response.body();

                       // Log.e("get_cate1",""+response.body().getLinks().getWpPostType().get(0).getHref());

//                        homeAdapter = new HomeAdapter(dataArrayList, getActivity());
//                        recycler_news.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
//                        recycler_news.setAdapter(homeAdapter);
//                        recycler_news.setFocusable(false);
//                        homeAdapter.notifyDataSetChanged();

                        for (int j = 0; j < dataArrayList.size(); j++) {

                            //Collections.reverse(dataArrayList);

                            if (dataArrayList.get(j).getName().equalsIgnoreCase("IFFI GOA 2017")){
                                dataArrayList.remove(j);
                            }
                            else if (dataArrayList.get(j).getName().equalsIgnoreCase("UNCATEGORIZED")){
                                dataArrayList.remove(j);
                            }
                           // dataArrayList.remove(0);
                            Collections.swap(dataArrayList, 0, j);

                        }

                        for (int j = 0; j < dataArrayList.size(); j++) {


                            if (dataArrayList.get(j).getName().equalsIgnoreCase("IFFI GOA 2017")){
                                dataArrayList.remove(j);
                            }
                            else if (dataArrayList.get(j).getName().equalsIgnoreCase("UNCATEGORIZED")){
                                dataArrayList.remove(j);
                            }
                            // dataArrayList.remove(0);
                            //Collections.swap(dataArrayList, 0, j);

                        }

                        for (int j = 0; j < dataArrayList.size(); j++) {
                            Log.e("get_cate",""+dataArrayList.get(j).getName());
                            tabLayout.addTab(tabLayout.newTab().setText(dataArrayList.get(j).getName()));

                            if (dataArrayList.get(j).getName().equalsIgnoreCase("गोवा खबर व्हिडीओ")){
                                goa_video_id=dataArrayList.get(j).getId();
                                Log.e("goa_video_tab_id",""+goa_video_id);

                            }
                        }

                    }
                }catch (Exception e){
                    Log.e("error_cate", e.getMessage());
                }
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<List<Category_Home_Model>> call, Throwable t) {
                progressDialog.dismiss();
                Log.e("error_category1",t.getMessage());
                Toast.makeText(getActivity(), ""+t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });




    }


    @Override
    public void onTabSelected(TabLayout.Tab tab) {

        int position = tab.getPosition();
        Integer id = dataArrayList.get(position).getId();

        Log.d("mmm",id.toString());
        String cate_url="http://www.goakhabar.com/wp-json/wp/v2/posts?categories="+id;

        if (Connectivity.isConnected(getActivity())){
            //GetCategoryNews(id);
            getpost(cate_url);
        }else Toast.makeText(getActivity(), "Please check Internet", Toast.LENGTH_SHORT).show();


    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
}
