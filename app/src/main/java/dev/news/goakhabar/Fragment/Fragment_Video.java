package dev.news.goakhabar.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import dev.news.goakhabar.CommentActivity;
import dev.news.goakhabar.R;
import dev.news.goakhabar.VideoPlayActivity;

/**
 * Created by Raghvendra Sahu on 10-Nov-19.
 */
public class Fragment_Video extends Fragment {


    LinearLayout ll_vid_play;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.video_fragment, container, false);

        getActivity().setTitle(R.string.home);
        // backpress = (ImageView) view.findViewById(R.id.back_press);
         ll_vid_play =  view.findViewById(R.id.ll_vid_play);


//        backpress.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ((MainActivity)getActivity()).onBackPressed();
//            }
//        });
        ll_vid_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(getActivity(), VideoPlayActivity.class);
                startActivity(intent);

            }
        });
//


        return view;
    }
}
