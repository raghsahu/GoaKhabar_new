package dev.news.goakhabar.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.PopupMenu;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.tabs.TabLayout;

import dev.news.goakhabar.R;

import static dev.news.goakhabar.MainActivity.iv_logo;
import static dev.news.goakhabar.MainActivity.tv_title;

/**
 * Created by Raghvendra Sahu on 10-Nov-19.
 */
public class News_Fragment extends Fragment {

        LinearLayout ll_news_details1,ll_news_details;
    private TabLayout tabLayout;

        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

            View view = inflater.inflate(R.layout.fragment_home, container, false);

            getActivity().setTitle(R.string.home);
            ll_news_details1=view.findViewById(R.id.ll_news_details1);
            ll_news_details=view.findViewById(R.id.ll_news_details);
            tabLayout = (TabLayout) view.findViewById(R.id.tabLayout);
            final TextView textViewOptions = view.findViewById(R.id.textViewOptions);
            TextView txt = view.findViewById(R.id.text);
            txt.setSelected(true);
            // backpress = (ImageView) view.findViewById(R.id.back_press);

//        backpress.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ((MainActivity)getActivity()).onBackPressed();
//            }
//        });


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
            //**************************
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


            iv_logo.setVisibility(View.GONE);
            tv_title.setVisibility(View.VISIBLE);
            tabLayout.setVisibility(View.GONE);

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

            return view;
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
