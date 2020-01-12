package dev.news.goakhabarr.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

import dev.news.goakhabarr.Api_Call.APIClient;
import dev.news.goakhabarr.Api_Call.Api_Call;
import dev.news.goakhabarr.Fragment.FragmentHome;
import dev.news.goakhabarr.Fragment.Fragment_Video;
import dev.news.goakhabarr.Fragment.News_Fragment;
import dev.news.goakhabarr.Adapter.LeftDrawerAdapter;
import dev.news.goakhabarr.Pojo.Category_Home.Category_Home_Model;
import dev.news.goakhabarr.Pojo.DrawerItem;
import dev.news.goakhabarr.R;
import dev.news.goakhabarr.Session.SharedPreference;
import dev.news.goakhabarr.Session.SessionManager;
import dev.news.goakhabarr.Utils.Connectivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    DrawerLayout mDrawerLayout;
    Toolbar toolbar;
    private ImageView iv_drawer,nav_img_profile;
    private ListView mDrawerList;
    LeftDrawerAdapter leftDrawerAdapter;
    public ArrayList<DrawerItem> List_Item=new ArrayList<>();
    public int ScreenPos = 0;
    DrawerItem drawerItem;
    boolean doubleBackToExitPressedOnce = false;
    public LinearLayout ll_nav_header;
    private TextView nav_tv_name, nav_tv_viewProfile;
    ImageView iv_search,iv_setting;
    public static TextView tv_title;
    public static ImageView iv_logo;
    public List<Category_Home_Model> dataArrayList;
    SessionManager sessionManager;
    public static Integer goa_video_id;
    public static AdRequest adRequestMain;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    Fragment fragment_home=new FragmentHome();
                    android.app.FragmentManager fragmentManager1 = getFragmentManager();
                    FragmentTransaction ft_home = getSupportFragmentManager().beginTransaction();
                    ft_home.replace(R.id.frame, fragment_home);
                    ft_home.addToBackStack(null);
                    ft_home.commit();
                    return true;

                case R.id.navigation_video:

                    Fragment fragment=new Fragment_Video();
                    android.app.FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.frame, fragment);
                   ft.addToBackStack(null);
                    ft.commit();

                    return true;

                case R.id.navigation_photo:

                    Fragment fragment2 = new News_Fragment();
                    tv_title.setText("ब्रेकिंग खबर");
                    iv_logo.setVisibility(View.GONE);
                    tv_title.setVisibility(View.VISIBLE);
                    Bundle bundle = new Bundle();
                    bundle.putString("Title", "ब्रेकिंग-न्यूज़");
                    FragmentManager fragmentManager2 = getSupportFragmentManager();
                    fragmentManager2.beginTransaction().replace(R.id.frame, fragment2).commit();
                    fragment2.setArguments(bundle);

                    return true;

            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sessionManager =new SessionManager(MainActivity.this);

        Log.e("tokennnnnn",sessionManager.getTokenId());

        mDrawerLayout = findViewById(R.id.drawer_layout);
        mDrawerList = findViewById(R.id.left_drawer);
        iv_drawer = findViewById(R.id.iv_drawer);
        iv_search = findViewById(R.id.iv_search);
        iv_logo = findViewById(R.id.iv_logo);
        tv_title = findViewById(R.id.tv_title);

        //banner ads initialize
        MobileAds.initialize(MainActivity.this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {}
        });

        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(toggle);

        toggle.syncState();

        View header = getLayoutInflater().inflate(R.layout.nav_header_activity_navigation, null);
        ll_nav_header = header.findViewById(R.id.ll_nav_header);
        nav_img_profile = header.findViewById(R.id.nav_img_profile);
        iv_setting = header.findViewById(R.id.iv_setting);
        nav_tv_name = header.findViewById(R.id.nav_tv_name);
        nav_tv_viewProfile = header.findViewById(R.id.nav_tv_viewProfile);


        mDrawerList.addHeaderView(header);
        nav_tv_name.setText(SharedPreference.getName(MainActivity.this));

        clickListner();
        //Drawer Item
       DrawerItem();
        SetupDrawer();

        //***set default open fragment
        Fragment  fragment1 = new FragmentHome();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.frame, fragment1).commit();
        mDrawerLayout.closeDrawer(mDrawerList);


        //****************************************************
        if (Connectivity.isConnected(MainActivity.this)){
            getCategory();//find video news id
        }else {
            Toast.makeText(MainActivity.this, "Please check Internet", Toast.LENGTH_SHORT).show();
        }

    }



    private void getCategory() {
        dataArrayList = new ArrayList<>();
        final ProgressDialog progressDialog = new ProgressDialog(MainActivity.this,R.style.MyGravity);
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

                        for (int j = 0; j < dataArrayList.size(); j++) {
                            Log.e("get_cate",""+dataArrayList.get(j).getName());

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
                Toast.makeText(MainActivity.this, ""+t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });




    }


    private void clickListner() {

        iv_drawer.setOnClickListener(this);
        ll_nav_header.setOnClickListener(this);
        nav_img_profile.setOnClickListener(this);
        iv_search.setOnClickListener(this);
        iv_setting.setOnClickListener(this);
    }

    private void SetupDrawer() {
        //Drawer Adapter

        leftDrawerAdapter = new LeftDrawerAdapter(MainActivity.this, List_Item);

        //Set Adapter
        mDrawerList.setAdapter(leftDrawerAdapter);

        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {

                int position = pos - 1;
                if (position >= 0) {
                    //Call open Fragment
                    SelectOption(position);
                }
            }
        });
    }

    private void SelectOption(int pos) {

        ScreenPos = pos;

        //Selected Value Highlighted
        leftDrawerAdapter.setSelectedIndex(pos);

        //Get List Item
        drawerItem = List_Item.get(pos);
        Log.e("Position......", String.valueOf(pos));
        String Item_Name = drawerItem.getItemName();
        Log.e("Position......", Item_Name);
        //-********************************

        //Call Fragment on a listview click listner
        if (Item_Name.equals("होम")) {
                    //titletxt.setText("Home");
                    Fragment fragment1 = new FragmentHome();
                    // imgheader.setVisibility(View.GONE);
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.frame, fragment1).commit();
                    mDrawerLayout.closeDrawer(mDrawerList);

        } else if (Item_Name.equals("गोवा")) {
                    Fragment  fragment2 = new News_Fragment();
                    tv_title.setText("गोवा-खबर");
                    iv_logo.setVisibility(View.GONE);
                    tv_title.setVisibility(View.VISIBLE);
                    Bundle bundle = new Bundle();
                    bundle.putString("Title", "गोवा-खबर");
                    FragmentManager fragmentManager2 = getSupportFragmentManager();
                    fragmentManager2.beginTransaction().replace(R.id.frame, fragment2).commit();
                    fragment2.setArguments(bundle);
                    mDrawerLayout.closeDrawer(mDrawerList);

        } else if (Item_Name.equals("देश")) {
                    Fragment  fragment2 = new News_Fragment();
                    tv_title.setText("देश-खबर");
                    iv_logo.setVisibility(View.GONE);
                    tv_title.setVisibility(View.VISIBLE);
                    Bundle bundle = new Bundle();
                    bundle.putString("Title", "देश-खबर");
                    FragmentManager fragmentManager2 = getSupportFragmentManager();
                    fragmentManager2.beginTransaction().replace(R.id.frame, fragment2).commit();
                    fragment2.setArguments(bundle);
                    mDrawerLayout.closeDrawer(mDrawerList);

        }else if (Item_Name.equals("विदेश")) {
            Fragment  fragment2 = new News_Fragment();
            tv_title.setText("विदेश-खबर");
            iv_logo.setVisibility(View.GONE);
            tv_title.setVisibility(View.VISIBLE);
            Bundle bundle = new Bundle();
            bundle.putString("Title", "विदेश-खबर");
            FragmentManager fragmentManager2 = getSupportFragmentManager();
            fragmentManager2.beginTransaction().replace(R.id.frame, fragment2).commit();
            fragment2.setArguments(bundle);
            mDrawerLayout.closeDrawer(mDrawerList);

        }else if (Item_Name.equals("राजकारण")) {
            Fragment  fragment2 = new News_Fragment();
            tv_title.setText("राजकारण-खबर");
            iv_logo.setVisibility(View.GONE);
            tv_title.setVisibility(View.VISIBLE);
            Bundle bundle = new Bundle();
            bundle.putString("Title", "राजकारण-खबर");
            FragmentManager fragmentManager2 = getSupportFragmentManager();
            fragmentManager2.beginTransaction().replace(R.id.frame, fragment2).commit();
            fragment2.setArguments(bundle);
            mDrawerLayout.closeDrawer(mDrawerList);

        }else if (Item_Name.equals("बिझनेस")) {
            Fragment  fragment2 = new News_Fragment();
            tv_title.setText("बिझनेस-खबर");
            iv_logo.setVisibility(View.GONE);
            tv_title.setVisibility(View.VISIBLE);
            Bundle bundle = new Bundle();
            bundle.putString("Title", "बिझनेस-खबर");
            FragmentManager fragmentManager2 = getSupportFragmentManager();
            fragmentManager2.beginTransaction().replace(R.id.frame, fragment2).commit();
            fragment2.setArguments(bundle);
            mDrawerLayout.closeDrawer(mDrawerList);

        }else if (Item_Name.equals("मनोरंजन")) {
            Fragment  fragment2 = new News_Fragment();
            tv_title.setText("मनोरंजन-खबर");
            iv_logo.setVisibility(View.GONE);
            tv_title.setVisibility(View.VISIBLE);
            Bundle bundle = new Bundle();
            bundle.putString("Title", "मनोरंजन-खबर");
            FragmentManager fragmentManager2 = getSupportFragmentManager();
            fragmentManager2.beginTransaction().replace(R.id.frame, fragment2).commit();
            fragment2.setArguments(bundle);
            mDrawerLayout.closeDrawer(mDrawerList);

        }else if (Item_Name.equals("क्रीडा")) {
            Fragment  fragment2 = new News_Fragment();
            tv_title.setText("क्रीडा-खबर");
            iv_logo.setVisibility(View.GONE);
            tv_title.setVisibility(View.VISIBLE);
            Bundle bundle = new Bundle();
            bundle.putString("Title", "क्रीडा-खबर");
            FragmentManager fragmentManager2 = getSupportFragmentManager();
            fragmentManager2.beginTransaction().replace(R.id.frame, fragment2).commit();
            fragment2.setArguments(bundle);
            mDrawerLayout.closeDrawer(mDrawerList);

        }else if (Item_Name.equals("संपादकीय")) {
            Fragment  fragment2 = new News_Fragment();
            tv_title.setText("संपादकीय");
            iv_logo.setVisibility(View.GONE);
            tv_title.setVisibility(View.VISIBLE);
            Bundle bundle = new Bundle();
            bundle.putString("Title", "संपादकीय");
            FragmentManager fragmentManager2 = getSupportFragmentManager();
            fragmentManager2.beginTransaction().replace(R.id.frame, fragment2).commit();
            fragment2.setArguments(bundle);
            mDrawerLayout.closeDrawer(mDrawerList);

        }else if (Item_Name.equals("इंग्लिश खबर")) {
            Fragment  fragment2 = new News_Fragment();
            tv_title.setText("इंग्लिश-खबर");
            iv_logo.setVisibility(View.GONE);
            tv_title.setVisibility(View.VISIBLE);
            Bundle bundle = new Bundle();
            bundle.putString("Title", "इंग्लिश-खबर");
            FragmentManager fragmentManager2 = getSupportFragmentManager();
            fragmentManager2.beginTransaction().replace(R.id.frame, fragment2).commit();
            fragment2.setArguments(bundle);
            mDrawerLayout.closeDrawer(mDrawerList);

        }else if (Item_Name.equals("ब्रेकिंग खबर")) {
            Fragment fragment2 = new News_Fragment();
            tv_title.setText("ब्रेकिंग खबर");
            iv_logo.setVisibility(View.GONE);
            tv_title.setVisibility(View.VISIBLE);
            Bundle bundle = new Bundle();
            bundle.putString("Title", "ब्रेकिंग-न्यूज़");
            FragmentManager fragmentManager2 = getSupportFragmentManager();
            fragmentManager2.beginTransaction().replace(R.id.frame, fragment2).commit();
            fragment2.setArguments(bundle);
            mDrawerLayout.closeDrawer(mDrawerList);
        }

            else if (Item_Name.equals("क्राइम खबर")) {
            Fragment fragment2 = new News_Fragment();
            tv_title.setText("क्राइम-खबर");
            iv_logo.setVisibility(View.GONE);
            tv_title.setVisibility(View.VISIBLE);
            Bundle bundle = new Bundle();
            bundle.putString("Title", "क्राइम-खबर");
            FragmentManager fragmentManager2 = getSupportFragmentManager();
            fragmentManager2.beginTransaction().replace(R.id.frame, fragment2).commit();
            fragment2.setArguments(bundle);
            mDrawerLayout.closeDrawer(mDrawerList);
        }
             else if (Item_Name.equals("महाराष्ट्र खबर")) {
            Fragment fragment2 = new News_Fragment();
            tv_title.setText("महाराष्ट्र-खबर");
            iv_logo.setVisibility(View.GONE);
            tv_title.setVisibility(View.VISIBLE);
            Bundle bundle = new Bundle();
            bundle.putString("Title", "महाराष्ट्र-खबर");
            FragmentManager fragmentManager2 = getSupportFragmentManager();
            fragmentManager2.beginTransaction().replace(R.id.frame, fragment2).commit();
            fragment2.setArguments(bundle);
            mDrawerLayout.closeDrawer(mDrawerList);

        }
            else if (Item_Name.equals("ब्रांड कनेक्ट")) {
            Fragment  fragment2 = new News_Fragment();
            tv_title.setText("ब्रांड कनेक्ट");
            iv_logo.setVisibility(View.GONE);
            tv_title.setVisibility(View.VISIBLE);
            Bundle bundle = new Bundle();
            bundle.putString("Title", "ब्रांड-कनेक्ट");
            FragmentManager fragmentManager2 = getSupportFragmentManager();
            fragmentManager2.beginTransaction().replace(R.id.frame, fragment2).commit();
            fragment2.setArguments(bundle);
            mDrawerLayout.closeDrawer(mDrawerList);

        }
    }

    //Drawer Item Array
    public void DrawerItem() {

        List_Item.clear();

        List_Item.add(new DrawerItem(R.drawable.gk, getResources().getString(R.string.home), R.drawable.ic_expand));
        List_Item.add(new DrawerItem(R.drawable.gk, getResources().getString(R.string.breaking_news), R.drawable.ic_expand));
        List_Item.add(new DrawerItem(R.drawable.gk, getString(R.string.goa), R.drawable.ic_expand));
        List_Item.add(new DrawerItem(R.drawable.gk, getString(R.string.desh), R.drawable.ic_expand));
        List_Item.add(new DrawerItem(R.drawable.gk, getString(R.string.videsh), R.drawable.ic_expand));
        List_Item.add(new DrawerItem(R.drawable.gk, getString(R.string.crime_news), R.drawable.ic_expand));
        List_Item.add(new DrawerItem(R.drawable.gk, getString(R.string.maharatra_news), R.drawable.ic_expand));
        List_Item.add(new DrawerItem(R.drawable.gk, getString(R.string.rajkaran), R.drawable.ic_expand));
        List_Item.add(new DrawerItem(R.drawable.gk, getString(R.string.business), R.drawable.ic_expand));
        List_Item.add(new DrawerItem(R.drawable.gk, getString(R.string.manoranjan), R.drawable.ic_expand));
        List_Item.add(new DrawerItem(R.drawable.gk, getString(R.string.krida), R.drawable.ic_expand));
        List_Item.add(new DrawerItem(R.drawable.gk, getString(R.string.sampadkiya), R.drawable.ic_expand));
        List_Item.add(new DrawerItem(R.drawable.gk, getString(R.string.english_khabar), R.drawable.ic_expand));
       // List_Item.add(new DrawerItem(R.drawable.ic_home_black_24dp, getString(R.string.digital), R.drawable.ic_expand));
      //  List_Item.add(new DrawerItem(R.drawable.ic_home_black_24dp, getString(R.string.foram), R.drawable.ic_expand));
       // List_Item.add(new DrawerItem(R.drawable.ic_home_black_24dp, getString(R.string.sampark), R.drawable.ic_expand));
        List_Item.add(new DrawerItem(R.drawable.gk, getString(R.string.brand), R.drawable.ic_expand));
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
            case R.id.iv_drawer:
                if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {

                    mDrawerLayout.closeDrawer(GravityCompat.START);
                } else {

                    mDrawerLayout.openDrawer(GravityCompat.START);
                }
                break;

            case R.id.iv_search:
                intent = new Intent(MainActivity.this, SearchActivity.class);
                startActivity(intent);

                break;

        case R.id.iv_setting:
                intent = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(intent);

                break;
        case R.id.nav_img_profile:
            if (sessionManager.isLoggedIn()){
                intent = new Intent(MainActivity.this, ProfileActivity.class);
                startActivity(intent);

            }else {
                intent = new Intent(MainActivity.this, SignupActivity.class);
                startActivity(intent);
            }


                break;


        }
    }


    @Override
    public void onBackPressed() {
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.nav_view);
        int seletedItemId = bottomNavigationView.getSelectedItemId();
        if (R.id.navigation_home != seletedItemId) {
            setHomeItem(MainActivity.this);
        } else {
            super.onBackPressed();
        }

    }

    private void setHomeItem(MainActivity mainActivity) {
        BottomNavigationView bottomNavigationView = (BottomNavigationView)
                mainActivity.findViewById(R.id.nav_view);
        bottomNavigationView.setSelectedItemId(R.id.navigation_home);

    }
}
