package dev.news.goakhabar.Adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import dev.news.goakhabar.Api_Call.APIClient1;
import dev.news.goakhabar.Api_Call.Api_Call;
import dev.news.goakhabar.Pojo.Category_Home.Home_categ_news_model;
import dev.news.goakhabar.Pojo.Category_Home_Model;
import dev.news.goakhabar.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Raghvendra Sahu on 25-Nov-19.
 */
public class HomeNewsAdapter extends RecyclerView.Adapter<HomeNewsAdapter.ViewHolder> {
    private List<Home_categ_news_model> category_home_models;
    private Context context;


    public HomeNewsAdapter(List<Home_categ_news_model> category_home_models1, Context context) {
        this.category_home_models = category_home_models1;
        this.context = context;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recommended_news, parent, false);
        /*session = new Session(context);
        user_id = session.getUser().user_id;*/
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        if (category_home_models.size() > 0) {
            final Home_categ_news_model categoryHomeModel = category_home_models.get(position);
            holder.tv_title.setText(categoryHomeModel.getTitle().getRendered());

            for (int i=0; i<category_home_models.size();i++){

//                Log.e("href_links",categoryHomeModel.getLinks().getWpPostType().get(i).getHref());


            }



//            if (!post_communityModels.get(position).image.equalsIgnoreCase(null)){
//
//                Picasso.with(context).load(CommuImage_BaseUrl+post_communityModels.get(position).image)
//                        .fit().centerCrop()
//                        //.placeholder(R.drawable.doctor)
//                        // .error(R.drawable.doctor)
//                        .into(holder.iv_post_image);
//            }

        }
    }



    @Override
    public int getItemCount() {
        return category_home_models.size();
        //return rattingReviewList.size();

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_title;
        public ImageView iv_post_image;
        public LinearLayout ll_item;
        RecyclerView recycler_news_new;


        public ViewHolder(View parent) {
            super(parent);
            tv_title = parent.findViewById(R.id.tv_title);
            // iv_post_image = parent.findViewById(R.id.iv_post_image);


        }
    }
}
