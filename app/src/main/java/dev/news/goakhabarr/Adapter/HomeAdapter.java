package dev.news.goakhabarr.Adapter;

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
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import dev.news.goakhabarr.Api_Call.APIClient1;
import dev.news.goakhabarr.Api_Call.Api_Call;
import dev.news.goakhabarr.Pojo.CategoryWise_new.Home_categ_news_model;
import dev.news.goakhabarr.Pojo.Category_Home.Category_Home_Model;
import dev.news.goakhabarr.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Raghvendra Sahu on 25-Nov-19.
 */
public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> {
    private List<Category_Home_Model> category_home_models;
    private Context context;
    //private Session session;
    private String user_id;
    HomeNewsAdapter homeNewsAdapter;
    List<Home_categ_news_model> dataArrayList = new ArrayList<>();

    public HomeAdapter(List<Category_Home_Model> category_home_models1, Context context) {
        this.category_home_models = category_home_models1;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_adater_item, parent, false);
        /*session = new Session(context);
        user_id = session.getUser().user_id;*/
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        if (category_home_models.size() > 0) {
           final Category_Home_Model categoryHomeModel = category_home_models.get(position);
            holder.tv_heading_categori.setText(categoryHomeModel.getName());

           // for (int i=0; i<category_home_models.size();i++){

                Log.e("href_links",""+categoryHomeModel.getId());

               // getParticularCategoryNews(holder.recycler_news_new,categoryHomeModel.getId());
          //  }

//            homeNewsAdapter = new HomeAdapter(categoryHomeModel.getLinks().get, context);
//            holder.recycler_news_new.setLayoutManager(new LinearLayoutManager(context, RecyclerView.VERTICAL, false));
//            holder.recycler_news_new.setAdapter(homeNewsAdapter);
//            holder.recycler_news_new.setFocusable(false);
//            homeNewsAdapter.notifyDataSetChanged();

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

    private void getParticularCategoryNews(final RecyclerView recycler_news_new, Integer id) {
        final ProgressDialog progressDialog = new ProgressDialog(context,R.style.MyGravity);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.show();

        Api_Call apiInterface = APIClient1.getClient().create(Api_Call.class);

        Call<List<Home_categ_news_model>> call = apiInterface.GetCategoryNews(id);

        call.enqueue(new Callback<List<Home_categ_news_model>>() {
            @Override
            public void onResponse(Call<List<Home_categ_news_model>> call, Response<List<Home_categ_news_model>> response) {

                try{
                    if (response!=null){
                        dataArrayList= response.body();
                        for (int i=0; i<response.body().size();i++){

                            Log.e("get_cate_news",""+response.body().get(i).getTitle().getRendered());
                        }

                        // Log.e("get_cate1",""+response.body().getLinks().getWpPostType().get(0).getHref());

//                        homeNewsAdapter = new HomeNewsAdapter(response.body(), context);
//                        recycler_news_new.setLayoutManager(new LinearLayoutManager(context, RecyclerView.HORIZONTAL, false));
//                        recycler_news_new.setAdapter(homeNewsAdapter);
//                        recycler_news_new.setFocusable(false);
//                        homeNewsAdapter.notifyDataSetChanged();

                    }
                }catch (Exception e){
                    Log.e("error_cate1", e.getMessage());
                }
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<List<Home_categ_news_model>> call, Throwable t) {
                progressDialog.dismiss();
                Log.e("error_category1",t.getMessage());
                //Toast.makeText(AllCountries.this, ""+t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

            }
        });


    }

    @Override
    public int getItemCount() {
        return category_home_models.size();
        //return rattingReviewList.size();

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_heading_categori;
        public ImageView iv_post_image;
        public LinearLayout ll_item;
        RecyclerView recycler_news_new;


        public ViewHolder(View parent) {
            super(parent);
            tv_heading_categori = parent.findViewById(R.id.tv_heading_categori);
            recycler_news_new = parent.findViewById(R.id.recycler_news_new);
           // iv_post_image = parent.findViewById(R.id.iv_post_image);


        }
    }
}
