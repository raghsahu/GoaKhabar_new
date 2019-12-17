package dev.news.goakhabar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import dev.news.goakhabar.Fragment.About_us_fragment;
import dev.news.goakhabar.Fragment.News_Fragment;

public class SettingsActivity extends AppCompatActivity {
    ImageView back_press;
    CardView card_share,card_rateus,card_aboutus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);


        back_press=findViewById(R.id.back_press);
        card_share=findViewById(R.id.card_share);
        card_rateus=findViewById(R.id.card_rateus);
        card_aboutus=findViewById(R.id.card_aboutus);

        back_press.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        card_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                share_app();
            }
        });

        card_rateus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RateusApp();
            }
        });
        card_aboutus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            Intent intent=new Intent(SettingsActivity.this, About_us_fragment.class);
            startActivity(intent);

            }
        });

    }

    private void RateusApp() {
        Uri uri = Uri.parse("market://details?id=" + SettingsActivity.this.getPackageName());
        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
        // To count with Play market backstack, After pressing back button,
        // to taken back to our application, we need to add following flags to intent.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                    Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                    Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        }
        try {
            startActivity(goToMarket);
        } catch (ActivityNotFoundException e) {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://play.google.com/store/apps/details?id=" + SettingsActivity.this.getPackageName())));
        }

    }


    private void share_app() {

            try {
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Goa khabar");
                String shareMessage= "\nLet me recommend you this application\n\n";
                shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID +"\n\n";
                shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                startActivity(Intent.createChooser(shareIntent, "choose one"));
            } catch(Exception e) {
                //e.toString();
            }
        }



    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
