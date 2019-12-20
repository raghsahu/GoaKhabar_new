package dev.news.goakhabar;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
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

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.Map;

public class NewsDetailsActivity extends AppCompatActivity {
    ImageView back_press,web_img;
    TextView tv_comment,tv_share;
    TextView title;
    WebView content;
    ProgressDialog progressDialog;
    Gson gson;
    Map<String, Object> mapPost;
    Map<String, Object> mapTitle;
    Map<String, Object> mapContent;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_details);

        back_press=findViewById(R.id.back_press);
        web_img=findViewById(R.id.web_img);
        title = (TextView) findViewById(R.id.title);
        content = (WebView)findViewById(R.id.content);

        //*********url hit
        back_press.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        try {
           String video=getIntent().getStringExtra("video");

           if (video.equalsIgnoreCase("video")){
               web_img.setVisibility(View.GONE);
           }

        }catch (Exception e){

        }

        try {
            final String id = getIntent().getStringExtra("id");
            final String img_url = getIntent().getStringExtra("featured_media");
            //getIntent().getExtras().getString("id");
            String url = "http://www.goakhabar.com/wp-json/wp/v2/posts/"+id+"?fields=title,content";

            Log.e("url_detail",""+url);

            GetNewsDetails(url);
            FindImage(img_url);


        }catch (Exception e){

        }



//        content.setWebViewClient(new WebViewClient() {
//            @Override
//            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                return false;
//            }
//        });
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

        //******************************************
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

    private void FindImage(String img_url) {
        final StringRequest stringRequest = new StringRequest(Request.Method.GET, img_url,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        // progressDialog.dismiss();
                        Log.d("kkkk", s.toString());
                        gson = new Gson();
//                        list1 = (List) gson.fromJson(s, List.class);

                        try {
                            JSONObject object = new JSONObject(s);
                            JSONObject jsonObject=object.getJSONObject("guid");
                            String rendered=jsonObject.getString("rendered");
                            Log.d("img_media", rendered);


                            Glide.with(NewsDetailsActivity.this)
                                    .load(rendered)
                                    .override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                                    //.error(R.drawable.glide_app_img_loader)
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

                        }catch (Exception e){

                        }


                    }
                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("k_error", error.toString());
                        // progressDialog.dismiss();
                        // Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();

                        NetworkResponse response = error.networkResponse;
                        if (error instanceof ServerError && response != null) {
                            try {
                                String res = new String(response.data,
                                        HttpHeaderParser.parseCharset(response.headers, "utf-8"));
                                // Now you can use any deserializer to make sense of data
                                // JSONObject obj = new JSONObject(res);
                            } catch (UnsupportedEncodingException e1) {
                                // Couldn't properly decode data to string
                                e1.printStackTrace();
                            }
                        }



                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(NewsDetailsActivity.this);
        //adding the string request to request queue
        requestQueue.add(stringRequest);
    }

    private void GetNewsDetails(String url) {
        final ProgressDialog progressDialog = new ProgressDialog(NewsDetailsActivity.this,R.style.MyGravity);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.show();
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                gson = new Gson();
                mapPost = (Map<String, Object>) gson.fromJson(s, Map.class);
                mapTitle = (Map<String, Object>) mapPost.get("title");
                mapContent = (Map<String, Object>) mapPost.get("content");

                title.setText(mapTitle.get("rendered").toString());

                content.getSettings().setLoadWithOverviewMode(true);
                content.getSettings().setUseWideViewPort(true);
                content.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.TEXT_AUTOSIZING);

               content.loadData(mapContent.get("rendered").toString(),"text/html","UTF-8");


                progressDialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                progressDialog.dismiss();
                // Toast.makeText(Post.this, id, Toast.LENGTH_LONG).show();
            }
        });

        RequestQueue rQueue = Volley.newRequestQueue(NewsDetailsActivity.this);
        rQueue.add(request);

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
