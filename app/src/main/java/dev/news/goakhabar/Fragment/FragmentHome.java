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
import android.widget.ArrayAdapter;
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
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import dev.news.goakhabar.Adapter.HomeAdapter;
import dev.news.goakhabar.Api_Call.APIClient;
import dev.news.goakhabar.Api_Call.Api_Call;
import dev.news.goakhabar.Api_Call.Base_Url;
import dev.news.goakhabar.DrawerItem;
import dev.news.goakhabar.NewsDetailsActivity;
import dev.news.goakhabar.Pojo.Category_Home_Model;
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
public class FragmentHome extends Fragment {
    LinearLayout ll_news_details1,ll_news_details;
    private TabLayout tabLayout;
    public ArrayList<DrawerItem> List_Item=new ArrayList<>();
    ImageView iv_option;
    TextView txt;
    RecyclerView recycler_news;
   List<Category_Home_Model> dataArrayList;
   //List<Post_Home_Model> postArrayList=new ArrayList<>();
   HomeAdapter homeAdapter;
    ListView postList;

    List<Object> list;
    Gson gson;
    ProgressDialog progressDialog;
    //ListView postList;
    Map<String,Object> mapPost;
    Map<String,Object> mapTitle;
    int postID;
    String postTitle[];

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
        txt = view.findViewById(R.id.text);
        recycler_news = view.findViewById(R.id.recycler_news);
        final TextView textViewOptions = view.findViewById(R.id.textViewOptions);
        txt.setSelected(true);

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

          // getCategory();
            getpost();

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
        txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Fragment  fragment1 = new Breaking_new_fragment();
                tv_title.setText(R.string.breaking_news);
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.frame, fragment1)
                        .commit();
                fragmentManager.beginTransaction().addToBackStack(null);

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


        List_Item.clear();

        List_Item.add(new DrawerItem(R.drawable.ic_home_black_24dp, getResources().getString(R.string.home), R.drawable.ic_expand));
        List_Item.add(new DrawerItem(R.drawable.ic_home_black_24dp, getResources().getString(R.string.breaking_news), R.drawable.ic_expand));
        List_Item.add(new DrawerItem(R.drawable.ic_home_black_24dp, getString(R.string.goa), R.drawable.ic_expand));
        List_Item.add(new DrawerItem(R.drawable.ic_home_black_24dp, getString(R.string.desh), R.drawable.ic_expand));
        List_Item.add(new DrawerItem(R.drawable.ic_home_black_24dp, getString(R.string.videsh), R.drawable.ic_expand));
        List_Item.add(new DrawerItem(R.drawable.ic_home_black_24dp, getString(R.string.crime_news), R.drawable.ic_expand));
        List_Item.add(new DrawerItem(R.drawable.ic_home_black_24dp, getString(R.string.maharatra_news), R.drawable.ic_expand));
        List_Item.add(new DrawerItem(R.drawable.ic_home_black_24dp, getString(R.string.rajkaran), R.drawable.ic_expand));
        List_Item.add(new DrawerItem(R.drawable.ic_home_black_24dp, getString(R.string.business), R.drawable.ic_expand));
        List_Item.add(new DrawerItem(R.drawable.ic_home_black_24dp, getString(R.string.manoranjan), R.drawable.ic_expand));
        List_Item.add(new DrawerItem(R.drawable.ic_home_black_24dp, getString(R.string.krida), R.drawable.ic_expand));
        List_Item.add(new DrawerItem(R.drawable.ic_home_black_24dp, getString(R.string.sampadkiya), R.drawable.ic_expand));
        List_Item.add(new DrawerItem(R.drawable.ic_home_black_24dp, getString(R.string.english_khabar), R.drawable.ic_expand));
      //  List_Item.add(new DrawerItem(R.drawable.ic_home_black_24dp, getString(R.string.digital), R.drawable.ic_expand));
       // List_Item.add(new DrawerItem(R.drawable.ic_home_black_24dp, getString(R.string.foram), R.drawable.ic_expand));
       // List_Item.add(new DrawerItem(R.drawable.ic_home_black_24dp, getString(R.string.sampark), R.drawable.ic_expand));
        List_Item.add(new DrawerItem(R.drawable.ic_home_black_24dp, getString(R.string.brand), R.drawable.ic_expand));



        for (int j = 0; j < List_Item.size(); j++) {

            tabLayout.addTab(tabLayout.newTab().setText(List_Item.get(j).getItemName()));
        }

        return view;
    }

    private void getpost() {
        final ProgressDialog progressDialog = new ProgressDialog(getActivity(),R.style.MyGravity);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, Base_Url.BaseUrl_Post,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        progressDialog.dismiss();
                        Log.d("kkkk", s.toString());

                        gson = new Gson();
                        list = (List) gson.fromJson(s, List.class);
                        postTitle = new String[list.size()];

                        for(int i=0;i<list.size();++i){
                            mapPost = (Map<String,Object>)list.get(i);
                            mapTitle = (Map<String, Object>) mapPost.get("title");
                            postTitle[i] = (String) mapTitle.get("rendered");
                        }

                        postList.setAdapter(new ArrayAdapter(getActivity(),android.R.layout.simple_list_item_1,postTitle));
                        progressDialog.dismiss();



                    }
                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("k_error", error.toString());
                        progressDialog.dismiss();
                        Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
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
                        Log.e("get_cate",""+response.body().get(0).getName());
                       // Log.e("get_cate1",""+response.body().getLinks().getWpPostType().get(0).getHref());


                        homeAdapter = new HomeAdapter(dataArrayList, getActivity());
                        recycler_news.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
                        recycler_news.setAdapter(homeAdapter);
                        recycler_news.setFocusable(false);
                        homeAdapter.notifyDataSetChanged();


                    }
                }catch (Exception e){
                    Log.e("error_cate", e.getMessage());
                }
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<List<Category_Home_Model>> call, Throwable t) {
                progressDialog.dismiss();
                Log.e("error_category",t.getMessage());
                //Toast.makeText(AllCountries.this, ""+t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

            }
        });




    }


}
