package dev.news.goakhabar;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class ProfileActivity extends AppCompatActivity {

    ImageView back_press;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        back_press=findViewById(R.id.back_press);

        back_press.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
