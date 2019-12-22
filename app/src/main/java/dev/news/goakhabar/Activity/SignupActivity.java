package dev.news.goakhabar.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.facebook.CallbackManager;
import com.google.android.material.tabs.TabLayout;

import dev.news.goakhabar.Fragment.LoginFragment;
import dev.news.goakhabar.Fragment.SignupFragment;
import dev.news.goakhabar.R;

public class SignupActivity extends AppCompatActivity {
    ImageView back_press;
    FrameLayout simpleFrameLayout;
    TabLayout tabLayout;
    CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        callbackManager = CallbackManager.Factory.create();
        back_press=findViewById(R.id.back_press);
        simpleFrameLayout = (FrameLayout) findViewById(R.id.simpleFrameLayout);
        tabLayout = (TabLayout) findViewById(R.id.simpleTabLayout);

        back_press.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        TabLayout.Tab firstTab = tabLayout.newTab();
        firstTab.setText("Login");
        tabLayout.addTab(firstTab); // add  the tab at in the TabLayout
        TabLayout.Tab secondTab = tabLayout.newTab();
        secondTab.setText("Signup");
        tabLayout.addTab(secondTab);

        Fragment fragment = new LoginFragment();
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.simpleFrameLayout, fragment);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        ft.commit();

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
// get the current selected tab's position and replace the fragment accordingly
                Fragment fragment = null;
                switch (tab.getPosition()) {
                    case 0:
                        fragment = new LoginFragment();
                        break;
                    case 1:
                        fragment = new SignupFragment();
                        break;

                }
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.simpleFrameLayout, fragment);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                ft.commit();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
