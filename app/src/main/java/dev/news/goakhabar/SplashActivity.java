package dev.news.goakhabar;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.github.florent37.viewanimator.ViewAnimator;

public class SplashActivity extends AppCompatActivity {

    ImageView splash_image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_splash);
        splash_image=findViewById(R.id.splash_image);

        ViewAnimator
                .animate(splash_image)
                .translationY(-1000, 0)
                .alpha(0, 1)
                .thenAnimate(splash_image)
                .scale(1f, 0.5f, 1f)
                .accelerate()
                .duration(1000)
                .start();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                Animatoo.animateZoom(SplashActivity.this);

                Intent intent1=new Intent(SplashActivity.this,MainActivity.class);
                        startActivity(intent1);
                        finish();

//                if (session.isLoggedIn()) {
//
//                    if (session.getUserId()!= null && !session.getUserId().equalsIgnoreCase("")) {
//                        Log.e("sess_logi", ""+session.isLoggedIn());
//                        Intent intent1=new Intent(ActivitySplash.this,ActivityNavigation.class);
//                        startActivity(intent1);
//                        finish();
//
//                        Animatoo.animateFade(ActivitySplash.this);
//                    } else
//                        Toast.makeText(ActivitySplash.this, "User not exist", Toast.LENGTH_SHORT).show();
//
//                } else {
//                    Log.e("sess_logi", ""+session.isLoggedIn());
//                    Intent intent1 = new Intent(ActivitySplash.this, ActivityWelcome.class);
//                    startActivity(intent1);
//                    finish();
//                    Animatoo.animateFade(ActivitySplash.this);
//                }



            }
        }, 5000);
    }




}
