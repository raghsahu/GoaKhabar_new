package dev.news.goakhabar.Fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import dev.news.goakhabar.Adapter.HomeNewsAdapter;
import dev.news.goakhabar.Api_Call.ApiClient2;
import dev.news.goakhabar.Api_Call.Api_Call;
import dev.news.goakhabar.Pojo.Menu_Wise_News.Menu_categ_news_model;
import dev.news.goakhabar.R;
import dev.news.goakhabar.Utils.Connectivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static dev.news.goakhabar.Fragment.FragmentHome.md5;
import static dev.news.goakhabar.Activity.MainActivity.iv_logo;
import static dev.news.goakhabar.Activity.MainActivity.tv_title;

/**
 * Created by Raghvendra Sahu on 10-Nov-19.
 */
public class News_Fragment extends Fragment {
    RecyclerView recycler_news;
    private AdView mAdView;
    private AdView mAdView1;
    AdRequest adRequest;


    @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

            View view = inflater.inflate(R.layout.menu_news_fragment, container, false);


            // backpress = (ImageView) view.findViewById(R.id.back_press);
            recycler_news = view.findViewById(R.id.recycler_news);

//        backpress.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ((MainActivity)getActivity()).onBackPressed();
//            }
//        });

            iv_logo.setVisibility(View.GONE);
            tv_title.setVisibility(View.VISIBLE);

            //Bundle bundle = this.getArguments();
            if (getArguments() != null) {
                String news_title = getArguments().getString("Title");
                Log.e("news_title_",news_title);

                getActivity().setTitle(news_title);

                if (Connectivity.isConnected(getActivity())){
                    getMenuNews(news_title);

                }else {
                    Toast.makeText(getActivity(), "Please check Internet", Toast.LENGTH_SHORT).show();
                }
            }

            //**********
            //banner ads initialize
            MobileAds.initialize(getActivity(), new OnInitializationCompleteListener() {
                @Override
                public void onInitializationComplete(InitializationStatus initializationStatus) {}
            });


            mAdView = (AdView)view.findViewById(R.id.adView);
            mAdView1 = (AdView)view.findViewById(R.id.adView1);
        adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        mAdView1.loadAd(adRequest);

            //***************1st ads
            mAdView = new AdView(getActivity());
            mAdView.setAdSize(AdSize.SMART_BANNER);
            mAdView.setAdUnitId("ca-app-pub-5014601384589531/1947070900");
            AdRequest.Builder adRequest = new AdRequest.Builder();

            String ANDROID_ID = Settings.Secure.getString(getContext().getContentResolver(),
                    Settings.Secure.ANDROID_ID);
            String deviceId = md5(ANDROID_ID).toUpperCase();
            Log.e("ANDROID_ID", ""+ANDROID_ID);
            Log.e("deviceId", ""+deviceId);

            adRequest.addTestDevice(deviceId);


            AdRequest request = adRequest.build();
            mAdView.loadAd(request);

            mAdView.setAdListener(new AdListener() {
                @Override
                public void onAdLoaded() {
                    Log.e("ban_adsload", "Banner");
                    // Toast.makeText(getActivity(), "Banner ads", Toast.LENGTH_SHORT).show();
                    // Code to be executed when an ad finishes loading.
                }

                @Override
                public void onAdFailedToLoad(int errorCode) {
                    Log.e("ban_ads", ""+errorCode);
                    //Toast.makeText(getActivity(), "Banner fail", Toast.LENGTH_SHORT).show();
                    // Code to be executed when an ad request fails.
                }

                @Override
                public void onAdOpened() {
                    Log.e("ban_adsopen", "Banner");
                    // Toast.makeText(getActivity(), "Banner open", Toast.LENGTH_SHORT).show();
                    // Code to be executed when an ad opens an overlay that
                    // covers the screen.
                }

                @Override
                public void onAdClicked() {
                    Log.e("ban_adsclick", "Banner");
                    //Toast.makeText(getActivity(), "Banner click", Toast.LENGTH_SHORT).show();
                    // Code to be executed when the user clicks on an ad.
                }

                @Override
                public void onAdLeftApplication() {
                    Log.e("ban_adsleft", "Banner");
                    //  Toast.makeText(getActivity(), "Banner left", Toast.LENGTH_SHORT).show();
                    // Code to be executed when the user has left the app.
                }

                @Override
                public void onAdClosed() {
                    Log.e("ban_adsclose", "Banner");
                    //Toast.makeText(getActivity(), "Banner close", Toast.LENGTH_SHORT).show();
                    // Code to be executed when the user is about to return
                    // to the app after tapping on an ad.
                }
            });

            //******************2nd ads****************************************
        mAdView1 = new AdView(getActivity());
        mAdView1.setAdSize(AdSize.SMART_BANNER);
        mAdView1.setAdUnitId("ca-app-pub-5014601384589531/1947070900");


        adRequest.addTestDevice(deviceId);

        mAdView1.loadAd(request);

        mAdView1.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                Log.e("ban_adsload", "Banner");
                // Toast.makeText(getActivity(), "Banner ads", Toast.LENGTH_SHORT).show();
                // Code to be executed when an ad finishes loading.
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                Log.e("ban_ads", ""+errorCode);
                //Toast.makeText(getActivity(), "Banner fail", Toast.LENGTH_SHORT).show();
                // Code to be executed when an ad request fails.
            }

            @Override
            public void onAdOpened() {
                Log.e("ban_adsopen", "Banner");
                // Toast.makeText(getActivity(), "Banner open", Toast.LENGTH_SHORT).show();
                // Code to be executed when an ad opens an overlay that
                // covers the screen.
            }

            @Override
            public void onAdClicked() {
                Log.e("ban_adsclick", "Banner");
                //Toast.makeText(getActivity(), "Banner click", Toast.LENGTH_SHORT).show();
                // Code to be executed when the user clicks on an ad.
            }

            @Override
            public void onAdLeftApplication() {
                Log.e("ban_adsleft", "Banner");
                //  Toast.makeText(getActivity(), "Banner left", Toast.LENGTH_SHORT).show();
                // Code to be executed when the user has left the app.
            }

            @Override
            public void onAdClosed() {
                Log.e("ban_adsclose", "Banner");
                //Toast.makeText(getActivity(), "Banner close", Toast.LENGTH_SHORT).show();
                // Code to be executed when the user is about to return
                // to the app after tapping on an ad.
            }
        });



            return view;
        }

    private void getMenuNews(String news_title) {
        final ProgressDialog progressDialog = new ProgressDialog(getActivity(),R.style.MyGravity);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.show();

        Api_Call apiInterface = ApiClient2.getClient().create(Api_Call.class);

        Call<Menu_categ_news_model> call = apiInterface.GetHeaderNews(news_title);

        call.enqueue(new Callback<Menu_categ_news_model>() {
            @Override
            public void onResponse(Call<Menu_categ_news_model> call, Response<Menu_categ_news_model> response) {
                progressDialog.dismiss();
                try{

                    if (response!=null){
                        Log.e("menu_new_res", response.body().getStatus());

                        if (!response.body().getStatus().equalsIgnoreCase("error")){
                            for (int i=0; i<response.body().getPosts().size();i++){
                                Log.e("menu_new_title", response.body().getPosts().get(i).getTitle());

                            }

                            HomeNewsAdapter  homeNewsAdapter = new HomeNewsAdapter( response.body().getPosts(), getActivity());
                            recycler_news.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
                            recycler_news.setAdapter(homeNewsAdapter);
                            //recycler_news.setFocusable(false);
                            homeNewsAdapter.notifyDataSetChanged();

                        }else {
                            Toast.makeText(getActivity(), "No news", Toast.LENGTH_SHORT).show();
                        }


                    }
                }catch (Exception e){
                    Log.e("error_cate1", e.getMessage());
                }
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<Menu_categ_news_model> call, Throwable t) {
                progressDialog.dismiss();
                Log.e("error_category1",t.getMessage());
                //Toast.makeText(AllCountries.this, ""+t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }


}
