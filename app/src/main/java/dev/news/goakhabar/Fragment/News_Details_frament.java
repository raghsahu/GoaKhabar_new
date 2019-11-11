package dev.news.goakhabar.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import dev.news.goakhabar.CommentActivity;
import dev.news.goakhabar.R;

/**
 * Created by Raghvendra Sahu on 10-Nov-19.
 */
public class News_Details_frament extends Fragment {

    TextView tv_comment;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.news_detail_frament, container, false);

        getActivity().setTitle(R.string.home);
        // backpress = (ImageView) view.findViewById(R.id.back_press);
        tv_comment =  view.findViewById(R.id.tv_comment);

//        backpress.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ((MainActivity)getActivity()).onBackPressed();
//            }
//        });
//
        tv_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(getActivity(), CommentActivity.class);
                startActivity(intent);

            }
        });

        return view;
    }
}
