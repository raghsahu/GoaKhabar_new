package dev.news.goakhabarr.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.os.Handler;
import android.util.Base64;
import android.util.Log;
import android.view.WindowManager;
import android.widget.ImageView;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.github.florent37.viewanimator.ViewAnimator;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import dev.news.goakhabarr.R;

public class SplashActivity extends AppCompatActivity {

    ImageView splash_image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_splash);
        splash_image=findViewById(R.id.splash_image);

        printHashKey();

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

                Intent intent1=new Intent(SplashActivity.this, MainActivity.class);
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

    private void printHashKey() {

        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "dev.news.goakhabarr",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
                Log.e("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));

            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }
    }


}
