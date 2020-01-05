package dev.news.goakhabarr;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class CommentActivity extends AppCompatActivity {

    ImageView back_press;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

        back_press=findViewById(R.id.back_press);


        back_press.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

//        AAAAuttC6kg:APA91bEfCtvNF_vXSExCgxnIpXkr1wpgw6PLr9D2tlE-RVX5hMyXjKS92mimg8XiqzA4T6htFCwnejP4sQZEK_xWiy-DrRAkAzYNLiMS3PVXz4gmTSiUds8d4rhCdoiLPL3TKO07FjwD

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
