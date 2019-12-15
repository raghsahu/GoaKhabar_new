package dev.news.goakhabar;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

public class MenuNewsDetailsActivity extends AppCompatActivity {

    ImageView back_press;
    TextView title;
    WebView content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_news_details);


        back_press=findViewById(R.id.back_press);
        title = (TextView) findViewById(R.id.title);
        content = (WebView)findViewById(R.id.content);

        content.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }
        });
        WebSettings webSettings = content.getSettings();
        webSettings.setJavaScriptEnabled(true);

        back_press.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });



        try {
            if (getIntent()!=null){
                String news_title=getIntent().getStringExtra("news_title");
                String news_content=getIntent().getStringExtra("news_Discription");


                title.setText(news_title);
                content.loadData(news_content,"text/html","UTF-8");
            }


        }catch (Exception e){

        }
    }



    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
