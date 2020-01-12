package dev.news.goakhabarr.Fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
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

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import dev.news.goakhabarr.Adapter.HomeNewsAdapter;
import dev.news.goakhabarr.Api_Call.ApiClient2;
import dev.news.goakhabarr.Api_Call.Api_Call;
import dev.news.goakhabarr.Pojo.DrawerItem;
import dev.news.goakhabarr.Pojo.Menu_Wise_News.Menu_categ_news_model;
import dev.news.goakhabarr.R;
import dev.news.goakhabarr.Utils.Connectivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static dev.news.goakhabarr.Activity.MainActivity.iv_logo;
import static dev.news.goakhabarr.Activity.MainActivity.tv_title;

/**
 * Created by Raghvendra Sahu on 10-Nov-19.
 */
public class FragmentHome extends Fragment {
    public TabLayout tabLayout;
    public ArrayList<DrawerItem> List_Item=new ArrayList<>();

    ImageView iv_option;
    TextView txt_breaking;
    RecyclerView recycler_news;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        getActivity().setTitle(R.string.home);

        iv_option=view.findViewById(R.id.iv_option);
        tabLayout = (TabLayout) view.findViewById(R.id.tabLayout);
        txt_breaking = view.findViewById(R.id.text);
        recycler_news = view.findViewById(R.id.recycler_news);
        txt_breaking.setSelected(true);

        //**********************************************************
       // tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        iv_logo.setVisibility(View.VISIBLE);
        tv_title.setVisibility(View.GONE);

        //****************************************************
        if (Connectivity.isConnected(getContext())){
            getMenuNews("ब्रेकिंग-न्यूज़");
            getMenuTabNews("गोवा-खबर");//set default new in recycler view home page
        }else {
            Toast.makeText(getActivity(), "Please check Internet", Toast.LENGTH_SHORT).show();
        }


        SetMenuOption();//**header menu tab add item

//***********************************************************
        txt_breaking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Fragment fragment2 = new News_Fragment();
                tv_title.setText("ब्रेकिंग खबर");
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
                                //ShareNews();
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

        //*********************************************
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();

                Log.e("tab_id_home",List_Item.get(position).getItem_id());
                String item_id=List_Item.get(position).getItem_id();

                if (Connectivity.isConnected(getActivity())){
                    getMenuTabNews(item_id);
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

    private void getMenuTabNews(String item_id) {

        final ProgressDialog progressDialog = new ProgressDialog(getActivity(),R.style.MyGravity);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.show();

        Api_Call apiInterface = ApiClient2.getClient().create(Api_Call.class);

        Call<Menu_categ_news_model> call = apiInterface.GetHeaderNews(item_id);

        call.enqueue(new Callback<Menu_categ_news_model>() {
            @Override
            public void onResponse(Call<Menu_categ_news_model> call, Response<Menu_categ_news_model> response) {
                progressDialog.dismiss();
                try{

                    if (response!=null){
                        Log.e("menu_new_res", response.body().getStatus());

                        if (!response.body().getStatus().equalsIgnoreCase("error")){
                            for (int i=0; i<response.body().getPosts().size();i++){
                                Log.e("menu_new_title", response.body().getPosts().get(i).getTitle());

                            }

                            HomeNewsAdapter  homeNewsAdapter = new HomeNewsAdapter( response.body().getPosts(), getActivity());
                            recycler_news.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
                            recycler_news.setAdapter(homeNewsAdapter);
                            //recycler_news.setFocusable(false);
                            homeNewsAdapter.notifyDataSetChanged();

                        }else {
                            Toast.makeText(getActivity(), "No news", Toast.LENGTH_SHORT).show();
                        }


                    }
                }catch (Exception e){
                    Log.e("error_cate1", e.getMessage());
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

    private void SetMenuOption() {
        List_Item.clear();
        List_Item.add(new DrawerItem("गोवा-खबर", getString(R.string.goa)));
        List_Item.add(new DrawerItem("महाराष्ट्र-खबर", getString(R.string.maharatra_news)));
        List_Item.add(new DrawerItem("क्राइम-खबर", getString(R.string.crime_news)));
        List_Item.add(new DrawerItem("क्रीडा-खबर", getString(R.string.krida)));
        List_Item.add(new DrawerItem("देश-खबर", getString(R.string.desh)));
        List_Item.add(new DrawerItem("विदेश-खबर", getString(R.string.videsh)));
        List_Item.add(new DrawerItem("राजकारण-खबर", getString(R.string.rajkaran)));
        List_Item.add(new DrawerItem("बिझनेस-खबर", getString(R.string.business)));
        List_Item.add(new DrawerItem("मनोरंजन-खबर", getString(R.string.manoranjan)));
        List_Item.add(new DrawerItem("संपादकीय", getString(R.string.sampadkiya)));
        List_Item.add(new DrawerItem("इंग्लिश-खबर", getString(R.string.english_khabar)));
        List_Item.add(new DrawerItem("ब्रांड-कनेक्ट", getString(R.string.brand)));

        for (int j = 0; j < List_Item.size(); j++) {
            Log.e("get_cate", "" + List_Item.get(j).getItemName());

            tabLayout.addTab(tabLayout.newTab().setText(List_Item.get(j).getItemName()));

        }


    }

    public static String md5(String md5) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
            byte[] array = md.digest(md5.getBytes());
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < array.length; ++i) {
                sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1,3));
            }
            return sb.toString();
        } catch (java.security.NoSuchAlgorithmException e) {
        }
        return null;
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

                                String toPrint = "";
                                toPrint += response.body().getPosts().get(i).getTitle() + " , ";
                                Log.e("breaking_result",""+toPrint);

                                txt_breaking.setText("ब्रेकिंग खबर:  "+toPrint);

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


}
