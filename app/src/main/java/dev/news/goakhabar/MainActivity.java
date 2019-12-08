package dev.news.goakhabar;

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
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

import dev.news.goakhabar.Api_Call.APIClient;
import dev.news.goakhabar.Api_Call.Api_Call;
import dev.news.goakhabar.Fragment.Breaking_new_fragment;
import dev.news.goakhabar.Fragment.FragmentHome;
import dev.news.goakhabar.Fragment.Fragment_Video;
import dev.news.goakhabar.Fragment.News_Fragment;
import dev.news.goakhabar.Fragment.Photo_Shoot_Fragment;
import dev.news.goakhabar.Pojo.Category_Home.Category_Home_Model;
import dev.news.goakhabar.Session.AppPreference;
import dev.news.goakhabar.Session.SessionManager;
import dev.news.goakhabar.Utils.Connectivity;
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
    public static List<Category_Home_Model> dataArrayList;
    SessionManager sessionManager;


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
                case R.id.navigation_dashboard:

                    Intent intent=new Intent(MainActivity.this,E_PaperActivity.class);
                    startActivity(intent);

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

                    Fragment fragment1=new Photo_Shoot_Fragment();
                    FragmentTransaction ft1 = getSupportFragmentManager().beginTransaction();
                    ft1.replace(R.id.frame, fragment1);
                    ft1.addToBackStack(null);
                    ft1.commit();

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

        mDrawerLayout = findViewById(R.id.drawer_layout);
        mDrawerList = findViewById(R.id.left_drawer);
        iv_drawer = findViewById(R.id.iv_drawer);
        iv_search = findViewById(R.id.iv_search);
        iv_logo = findViewById(R.id.iv_logo);
        tv_title = findViewById(R.id.tv_title);


        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(toggle);
        //mDrawerLayout.setDrawerListener(toggle);
      // actionbar.setDisplayHomeAsUpEnabled(true)
        toggle.syncState();

        View header = getLayoutInflater().inflate(R.layout.nav_header_activity_navigation, null);
        ll_nav_header = header.findViewById(R.id.ll_nav_header);
        nav_img_profile = header.findViewById(R.id.nav_img_profile);
        iv_setting = header.findViewById(R.id.iv_setting);
        nav_tv_name = header.findViewById(R.id.nav_tv_name);
        nav_tv_viewProfile = header.findViewById(R.id.nav_tv_viewProfile);


        mDrawerList.addHeaderView(header);
        nav_tv_name.setText(AppPreference.getName(MainActivity.this));

        clickListner();
        //Drawer Item
        DrawerItem();
        //Setup Drawer
        SetupDrawer();

        Fragment  fragment1 = new FragmentHome();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.frame, fragment1).commit();
        mDrawerLayout.closeDrawer(mDrawerList);


        //****************************************************
        if (Connectivity.isConnected(MainActivity.this)){

            getCategory();
            // getpost();

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
                        Log.e("get_cate",""+response.body().get(0).getName());
                        // Log.e("get_cate1",""+response.body().getLinks().getWpPostType().get(0).getHref());

//                        homeAdapter = new HomeAdapter(dataArrayList, getActivity());
//                        recycler_news.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
//                        recycler_news.setAdapter(homeAdapter);
//                        recycler_news.setFocusable(false);
//                        homeAdapter.notifyDataSetChanged();


//                        try {
//                            for (int j = 0; j < dataArrayList.size(); j++) {
//
//                                tabLayout.addTab(tabLayout.newTab().setText(dataArrayList.get(j).getName()));
//                            }
//                        }catch (Exception e){
//
//                        }

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
                //Toast.makeText(AllCountries.this, ""+t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

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
        Log.e("Position......", drawerItem.getItemName());
        //Call Fragment on a listview click listner
        if (Item_Name.equals("होम")) {

            //titletxt.setText("Home");
          Fragment  fragment1 = new FragmentHome();
            // imgheader.setVisibility(View.GONE);
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.frame, fragment1).commit();
            mDrawerLayout.closeDrawer(mDrawerList);

        } else if (Item_Name.equals("गोवा")) {
            tv_title.setText("गोवा");
            iv_logo.setVisibility(View.GONE);
            tv_title.setVisibility(View.VISIBLE);
            Fragment  fragment1 = new News_Fragment();
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.frame, fragment1).commit();
            mDrawerLayout.closeDrawer(mDrawerList);

        } else if (Item_Name.equals("देश")) {
            tv_title.setText("देश");
            iv_logo.setVisibility(View.GONE);
            //titletxt.setText("Home");
            Fragment  fragment1 = new News_Fragment();
            // imgheader.setVisibility(View.GONE);
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.frame, fragment1).commit();
            mDrawerLayout.closeDrawer(mDrawerList);

        }else if (Item_Name.equals("विदेश")) {
            tv_title.setText("विदेश");
            iv_logo.setVisibility(View.GONE);
            //titletxt.setText("Home");
            Fragment  fragment1 = new News_Fragment();
            // imgheader.setVisibility(View.GONE);
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.frame, fragment1).commit();
            mDrawerLayout.closeDrawer(mDrawerList);

        }else if (Item_Name.equals("राजकारण")) {
            tv_title.setText("राजकारण");
            iv_logo.setVisibility(View.GONE);
            //titletxt.setText("Home");
            Fragment  fragment1 = new News_Fragment();
            // imgheader.setVisibility(View.GONE);
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.frame, fragment1).commit();
            mDrawerLayout.closeDrawer(mDrawerList);

        }else if (Item_Name.equals("बिज़नेस")) {
            tv_title.setText("बिज़नेस");
            iv_logo.setVisibility(View.GONE);
            //titletxt.setText("Home");
            Fragment  fragment1 = new News_Fragment();
            // imgheader.setVisibility(View.GONE);
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.frame, fragment1).commit();
            mDrawerLayout.closeDrawer(mDrawerList);

        }else if (Item_Name.equals("मनोरंजन")) {
            tv_title.setText("मनोरंजन");
            iv_logo.setVisibility(View.GONE);
            //titletxt.setText("Home");
            Fragment  fragment1 = new News_Fragment();
            // imgheader.setVisibility(View.GONE);
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.frame, fragment1).commit();
            mDrawerLayout.closeDrawer(mDrawerList);

        }else if (Item_Name.equals("क्रीड़ा")) {
            tv_title.setText("क्रीड़ा");
            iv_logo.setVisibility(View.GONE);
            //titletxt.setText("Home");
            Fragment  fragment1 = new News_Fragment();
            // imgheader.setVisibility(View.GONE);
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.frame, fragment1).commit();
            mDrawerLayout.closeDrawer(mDrawerList);

        }else if (Item_Name.equals("संपादकीय")) {
            tv_title.setText("संपादकीय");
            iv_logo.setVisibility(View.GONE);
            //titletxt.setText("Home");
            Fragment  fragment1 = new News_Fragment();
            // imgheader.setVisibility(View.GONE);
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.frame, fragment1).commit();
            mDrawerLayout.closeDrawer(mDrawerList);

        }else if (Item_Name.equals("ंग्लिश खबर")) {
            tv_title.setText("ंग्लिश खबर");
            iv_logo.setVisibility(View.GONE);
            //titletxt.setText("Home");
//            Fragment  fragment1 = new English_Fragment();
//            // imgheader.setVisibility(View.GONE);
//            FragmentManager fragmentManager = getSupportFragmentManager();
//            fragmentManager.beginTransaction().replace(R.id.frame, fragment1).commit();
//            mDrawerLayout.closeDrawer(mDrawerList);

        }else if (Item_Name.equals("ब्रेकिंग खबर")) {
            tv_title.setText("ब्रेकिंग खबर");
            iv_logo.setVisibility(View.GONE);
            //titletxt.setText("Home");
            Fragment  fragment1 = new Breaking_new_fragment();
            // imgheader.setVisibility(View.GONE);
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.frame, fragment1).commit();
            mDrawerLayout.closeDrawer(mDrawerList);

        }else if (Item_Name.equals("फोरम")) {
//            //titletxt.setText("Home");
//            Fragment  fragment1 = new News_Fragment();
//            // imgheader.setVisibility(View.GONE);
//            FragmentManager fragmentManager = getSupportFragmentManager();
//            fragmentManager.beginTransaction().replace(R.id.frame, fragment1).commit();
//            mDrawerLayout.closeDrawer(mDrawerList);

        }else if (Item_Name.equals("संपर्क")) {
//            //titletxt.setText("Home");
//            Fragment  fragment1 = new News_Fragment();
//            // imgheader.setVisibility(View.GONE);
//            FragmentManager fragmentManager = getSupportFragmentManager();
//            fragmentManager.beginTransaction().replace(R.id.frame, fragment1).commit();
//            mDrawerLayout.closeDrawer(mDrawerList);

        }



    }

    //Drawer Item Array
    public void DrawerItem() {

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
       // List_Item.add(new DrawerItem(R.drawable.ic_home_black_24dp, getString(R.string.digital), R.drawable.ic_expand));
      //  List_Item.add(new DrawerItem(R.drawable.ic_home_black_24dp, getString(R.string.foram), R.drawable.ic_expand));
       // List_Item.add(new DrawerItem(R.drawable.ic_home_black_24dp, getString(R.string.sampark), R.drawable.ic_expand));
        List_Item.add(new DrawerItem(R.drawable.ic_home_black_24dp, getString(R.string.brand), R.drawable.ic_expand));
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


//    @Override
//    public void onBackPressed() {
//        super.onBackPressed();
//        Log.e("pos", "" + ScreenPos);
//        DrawerLayout drawer = findViewById(R.id.drawer_layout);
//        if (drawer.isDrawerOpen(GravityCompat.START)) {
//            drawer.closeDrawer(GravityCompat.START);
//
//        } else if (ScreenPos == 0) {
//
//            if (doubleBackToExitPressedOnce) {
//                super.onBackPressed();
//                return;
//            }
//
//            this.doubleBackToExitPressedOnce = true;
//            Toast.makeText(this, "Please Press Back again to exit the app", Toast.LENGTH_SHORT).show();
//
//            new Handler().postDelayed(new Runnable() {
//
//                @Override
//                public void run() {
//
//                    doubleBackToExitPressedOnce = false;
//                }
//            }, 2000);
//
//        } else {
//
//            //Call home fragment
//            SelectOption(0);
//
//        }
   // }
}
