package dev.news.goakhabar.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import dev.news.goakhabar.R;

/**
 * Created by Raghvendra Sahu on 10-Nov-19.
 */
public class Photo_Shoot_Fragment extends Fragment {


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.photo_shoot_fragment, container, false);

       // getActivity().setTitle(R.string.home);
        // backpress = (ImageView) view.findViewById(R.id.back_press);


//        backpress.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ((MainActivity)getActivity()).onBackPressed();
//            }
//        });
//


        return view;
    }

}
