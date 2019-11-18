package dev.news.goakhabar.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.PopupMenu;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

import dev.news.goakhabar.BuildConfig;
import dev.news.goakhabar.DrawerItem;
import dev.news.goakhabar.MainActivity;
import dev.news.goakhabar.R;

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


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        getActivity().setTitle(R.string.home);
        ll_news_details1=view.findViewById(R.id.ll_news_details1);
        ll_news_details=view.findViewById(R.id.ll_news_details);
        iv_option=view.findViewById(R.id.iv_option);
        tabLayout = (TabLayout) view.findViewById(R.id.tabLayout);
        txt = view.findViewById(R.id.text);
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

        //**************************

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


}
