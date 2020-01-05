package dev.news.goakhabarr;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class E_PaperActivity extends AppCompatActivity {

    ImageView back_press;
    LinearLayout ll_epaper_details;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_e__paper);



        back_press=findViewById(R.id.back_press);
        ll_epaper_details=findViewById(R.id.ll_epaper_details);

        back_press.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        ll_epaper_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(E_PaperActivity.this,E_paper_detailsActivity.class);
                startActivity(intent);


            }
        });
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
