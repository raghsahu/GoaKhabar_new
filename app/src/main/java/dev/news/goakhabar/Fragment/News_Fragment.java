package dev.news.goakhabar.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
            // backpress = (ImageView) view.findViewById(R.id.back_press);

//        backpress.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ((MainActivity)getActivity()).onBackPressed();
//            }
//        });
//
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


    }
