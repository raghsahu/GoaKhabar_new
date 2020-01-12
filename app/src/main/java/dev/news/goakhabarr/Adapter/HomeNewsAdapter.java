package dev.news.goakhabarr.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.util.List;
import java.util.Map;

import dev.news.goakhabarr.Activity.MenuNewsDetailsActivity;
import dev.news.goakhabarr.Pojo.Menu_Wise_News.MenuPostNews;
import dev.news.goakhabarr.R;

/**
 * Created by Raghvendra Sahu on 25-Nov-19.
 */
public class HomeNewsAdapter extends RecyclerView.Adapter<HomeNewsAdapter.ViewHolder> {
    private List<MenuPostNews> category_home_models;
    Context context;

    public HomeNewsAdapter(List<MenuPostNews> category_home_models1, Context context) {
        this.category_home_models = category_home_models1;
        this.context = context;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
       // if (category_home_models.size() > 0) {
            final MenuPostNews categoryHomeModel = category_home_models.get(position);
            holder.tv_title.setText(categoryHomeModel.getTitle());


            try {

                Log.e("catg-size",""+category_home_models.size());
            if (!category_home_models.get(position).getAttachments().get(0).getUrl().equalsIgnoreCase(null)) {

                Glide.with(context)
                        .load(category_home_models.get(position).getAttachments().get(0).getUrl())
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
                        }).into(holder.iv_post_image);


            }

            }catch (Exception e){

                }

            //*******3 dot menu share option
        holder.textViewOptions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //creating a popup menu
                PopupMenu popup = new PopupMenu(context, holder.textViewOptions);
                //inflating menu from xml resource
                popup.inflate(R.menu.options_menu);
                //adding click listener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.navigation_share:
                                //handle menu1 click
                                try {
                                    String links=categoryHomeModel.getUrl();
                                    Log.e("img_url",links);
                                    ShareNews(links,categoryHomeModel.getTitle(),category_home_models.get(position).getAttachments().get(0).getUrl());


                                }catch (Exception e){

                                }

                                return true;


                            default:
                                return false;
                        }
                    }
                });
                //displaying the popup
                popup.show();

            }
        });


            //****************************************
        holder.ll_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    String news_image=category_home_models.get(position).getAttachments().get(0).getUrl();
                    String news_title=category_home_models.get(position).getTitle();
                    String news_Discription=category_home_models.get(position).getContent();

                    Intent intent = new Intent(context, MenuNewsDetailsActivity.class);
                    intent.putExtra("news_title", news_title);
                    intent.putExtra("news_Discription", news_Discription);
                    intent.putExtra("news_image", news_image);
                    context.startActivity(intent);
                }catch (Exception e){

                    String news_title=category_home_models.get(position).getTitle();
                    String news_Discription=category_home_models.get(position).getContent();

                    Intent intent = new Intent(context, MenuNewsDetailsActivity.class);
                    intent.putExtra("news_title", news_title);
                    intent.putExtra("news_Discription", news_Discription);
                    intent.putExtra("news_image", "");
                    context.startActivity(intent);

                }


            }
        });


        }

    private void ShareNews(String links, String title, String url) {
        try {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, "GoaKhabar");
            shareIntent.putExtra(Intent.EXTRA_TEXT, links);
            context.startActivity(Intent.createChooser(shareIntent, "choose one"));

        } catch(Exception e) {
            //e.toString();
        }


    }


    @Override
    public int getItemCount() {
        return category_home_models.size();

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_title,textViewOptions;
        public ImageView iv_post_image;
        LinearLayout ll_details;


        public ViewHolder(View parent) {
            super(parent);
            tv_title = parent.findViewById(R.id.tv_title);
            iv_post_image = parent.findViewById(R.id.news1);
            textViewOptions = parent.findViewById(R.id.textViewOptions);
            ll_details = parent.findViewById(R.id.ll_details);

        }
    }
}
