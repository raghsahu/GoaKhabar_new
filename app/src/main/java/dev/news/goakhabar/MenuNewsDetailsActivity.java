package dev.news.goakhabar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

public class MenuNewsDetailsActivity extends AppCompatActivity {

    ImageView back_press,web_img;
    TextView title;
    WebView content;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_news_details);
        context=MenuNewsDetailsActivity.this;

        back_press=findViewById(R.id.back_press);
        title = (TextView) findViewById(R.id.title);
        content = (WebView)findViewById(R.id.content);
        web_img = findViewById(R.id.web_img);

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
                String news_image=getIntent().getStringExtra("news_image");

                title.setText(news_title);

                content.getSettings().setLoadWithOverviewMode(true);
                content.getSettings().setUseWideViewPort(true);
                content.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.TEXT_AUTOSIZING);
                content.loadData(news_content,"text/html","UTF-8");


                if(news_image!=null && !news_image.isEmpty()){

                    Glide.with(context)
                            .load(news_image)
                            .override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                            .error(R.drawable.goakhabar)
                            .listener(new RequestListener<Drawable>() {
                                @Override
                                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                    return false;
                                }

                                @Override
                                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                    return false;
                                }
                            }).into(web_img);
                }else {
                    web_img.setVisibility(View.GONE);
                }
            }


        }catch (Exception e){

        }

        WebSettings webSettings = content.getSettings();
        webSettings.setJavaScriptEnabled(true);


        content.getSettings().setJavaScriptEnabled(true);
        content.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        content.getSettings().setPluginState(WebSettings.PluginState.ON_DEMAND);
        content.getSettings().setMediaPlaybackRequiresUserGesture(false);
        content.getSettings().setSupportZoom(true);
        content.getSettings().setBuiltInZoomControls(true);


        if (Build.VERSION.SDK_INT >= 21) {
            content.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
            CookieManager.getInstance().setAcceptThirdPartyCookies(content, true);
        }

        if (android.os.Build.VERSION.SDK_INT < 16) {
            content.setBackgroundColor(0x00000000);
        } else {
            content.setBackgroundColor(Color.argb(1, 0, 0, 0));
        }

        //***********************web crome client*******************
        content.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress) {
                //progressBar.setProgress(progress);
            }

            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
                return super.onJsAlert(view, url, message, result);
            }

            @Override
            public boolean onJsConfirm(WebView view, String url, String message, JsResult result) {
                return super.onJsConfirm(view, url, message, result);
            }

            @Override
            public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, JsPromptResult result) {
                return super.onJsPrompt(view, url, message, defaultValue, result);
            }

            @Override
            public boolean onJsBeforeUnload(WebView view, String url, String message, JsResult result) {
                return super.onJsBeforeUnload(view, url, message, result);
            }

        });

        //webview client
        content.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    view.loadUrl(request.getUrl().toString());
                }
                return super.shouldOverrideUrlLoading(view, request);
            }

            @Override
            public void onPageStarted(WebView webview, String url, Bitmap favicon) {
                super.onPageStarted(webview, url, favicon);
                webview.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onPageFinished(WebView webview, String url) {

                webview.setVisibility(View.VISIBLE);
                super.onPageFinished(webview, url);

            }
        });
        content.setWebChromeClient(new WebChromeClient());
        content.getSettings().setUserAgentString("Mozilla/5.0 (Linux; U; Android 2.0; en-us; Droid Build/ESD20) AppleWebKit/530.17 (KHTML, like Gecko) Version/4.0 Mobile Safari/530.17");



    }



    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
