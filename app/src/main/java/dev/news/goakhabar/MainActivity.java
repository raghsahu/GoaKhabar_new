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

import dev.news.goakhabar.Fragment.FragmentHome;
import dev.news.goakhabar.Fragment.Fragment_Video;
import dev.news.goakhabar.Fragment.News_Fragment;
import dev.news.goakhabar.Fragment.Photo_Shoot_Fragment;

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
                    ft_home.commit();
                    return true;
                case R.id.navigation_dashboard:

                    Intent intent=new Intent(MainActivity.this,E_PaperActivity.class);
                    startActivity(intent);

                    return true;
                case R.id.navigation_notifications:
                    // mTextMessage.setText(R.string.title_notifications);

//                    Intent intent=new Intent(MainActivity.this, Invite_friend_Activity.class);
//                    startActivity(intent);

                    Fragment fragment=new Fragment_Video();
                    android.app.FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.frame, fragment);
                    // ft.addToBackStack(null);
                    ft.commit();


                    return true;

                case R.id.navigation_photo:
                    // mTextMessage.setText(R.string.title_notifications);

//                    Intent intent=new Intent(MainActivity.this, Invite_friend_Activity.class);
//                    startActivity(intent);

                    Fragment fragment1=new Photo_Shoot_Fragment();
                    FragmentTransaction ft1 = getSupportFragmentManager().beginTransaction();
                    ft1.replace(R.id.frame, fragment1);
                    // ft.addToBackStack(null);
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
        toggle.syncState();

        View header = getLayoutInflater().inflate(R.layout.nav_header_activity_navigation, null);
        ll_nav_header = header.findViewById(R.id.ll_nav_header);
        nav_img_profile = header.findViewById(R.id.nav_img_profile);
        iv_setting = header.findViewById(R.id.iv_setting);
        nav_tv_name = header.findViewById(R.id.nav_tv_name);
        nav_tv_viewProfile = header.findViewById(R.id.nav_tv_viewProfile);


        mDrawerList.addHeaderView(header);

        clickListner();
        //Drawer Item
        DrawerItem();
        //Setup Drawer
        SetupDrawer();

        Fragment  fragment1 = new FragmentHome();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.frame, fragment1).commit();
        mDrawerLayout.closeDrawer(mDrawerList);


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

        }else if (Item_Name.equals("डिजिटल")) {
            tv_title.setText("डिजिटल");
            iv_logo.setVisibility(View.GONE);
            //titletxt.setText("Home");
            Fragment  fragment1 = new News_Fragment();
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
        List_Item.add(new DrawerItem(R.drawable.ic_home_black_24dp, getString(R.string.goa), R.drawable.ic_expand));
        List_Item.add(new DrawerItem(R.drawable.ic_home_black_24dp, getString(R.string.desh), R.drawable.ic_expand));
        List_Item.add(new DrawerItem(R.drawable.ic_home_black_24dp, getString(R.string.videsh), R.drawable.ic_expand));
        List_Item.add(new DrawerItem(R.drawable.ic_home_black_24dp, getString(R.string.rajkaran), R.drawable.ic_expand));
        List_Item.add(new DrawerItem(R.drawable.ic_home_black_24dp, getString(R.string.business), R.drawable.ic_expand));
        List_Item.add(new DrawerItem(R.drawable.ic_home_black_24dp, getString(R.string.manoranjan), R.drawable.ic_expand));
        List_Item.add(new DrawerItem(R.drawable.ic_home_black_24dp, getString(R.string.krida), R.drawable.ic_expand));
        List_Item.add(new DrawerItem(R.drawable.ic_home_black_24dp, getString(R.string.sampadkiya), R.drawable.ic_expand));
        List_Item.add(new DrawerItem(R.drawable.ic_home_black_24dp, getString(R.string.english_khabar), R.drawable.ic_expand));
        List_Item.add(new DrawerItem(R.drawable.ic_home_black_24dp, getString(R.string.digital), R.drawable.ic_expand));
        List_Item.add(new DrawerItem(R.drawable.ic_home_black_24dp, getString(R.string.foram), R.drawable.ic_expand));
        List_Item.add(new DrawerItem(R.drawable.ic_home_black_24dp, getString(R.string.sampark), R.drawable.ic_expand));
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
                intent = new Intent(MainActivity.this, SignupActivity.class);
                startActivity(intent);

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
