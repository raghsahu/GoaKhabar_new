package dev.news.goakhabar.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import dev.news.goakhabar.R;

/**
 * Created by Raghvendra Sahu on 01-Dec-19.
 */
public class SignupFragment extends Fragment {

    public SignupFragment() {
// Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
// Inflate the layout for this fragment
        return inflater.inflate(R.layout.signup_fragment, container, false);
    }
}
