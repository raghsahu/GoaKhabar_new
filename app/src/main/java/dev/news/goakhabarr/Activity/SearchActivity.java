package dev.news.goakhabarr.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import dev.news.goakhabarr.Api_Call.APIClient;
import dev.news.goakhabarr.Api_Call.Api_Call;
import dev.news.goakhabarr.Pojo.CategoryWise_new.Home_categ_news_model;
import dev.news.goakhabarr.R;
import dev.news.goakhabarr.Utils.Connectivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchActivity extends AppCompatActivity {

    ImageView back_press;
    EditText search;
    TextView tv_search;
    ArrayList<String> news_list=new ArrayList<>();
    List<Home_categ_news_model> catByNews = new ArrayList<>();
    ListView postList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        back_press=findViewById(R.id.back_press);
        search=findViewById(R.id.search);
        tv_search=findViewById(R.id.tv_search);
        postList = (ListView)findViewById(R.id.postList);

        back_press.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        tv_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s=search.getText().toString();
                String search_url= "http://www.goakhabar.com/wp-json/wp/v2/posts?filter[category_name]=होम&per_page=30&order=asc&search="+s;
                if (Connectivity.isConnected(SearchActivity.this)){

                    getNewsSearch(s);

                }else {
                    Toast.makeText(SearchActivity.this, "Please check Internet", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //*******************************************************
        postList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(SearchActivity.this, NewsDetailsActivity.class);
                intent.putExtra("id", ""+catByNews.get(position).getId());
                startActivity(intent);
            }
        });

    }

    private void getNewsSearch(String s) {

        final ProgressDialog progressDialog = new ProgressDialog(SearchActivity.this,R.style.MyGravity);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.show();

        Api_Call apiInterface = APIClient.getClient().create(Api_Call.class);

        Call<List<Home_categ_news_model>> call = apiInterface.GetSearchNews("होम","30","asc",s);

        call.enqueue(new Callback<List<Home_categ_news_model>>() {
            @Override
            public void onResponse(Call<List<Home_categ_news_model>> call, Response<List<Home_categ_news_model>> response) {

                try{
                    catByNews.clear();
                    if (response!=null){
                        catByNews= response.body();

                        Log.e("login_status",response.body().get(0).getTitle().getRendered());

                        for (int i=0;i<response.body().size();i++)
                        {
                            news_list.add(response.body().get(i).getTitle().getRendered());
                        }

                        postList.setAdapter(new ArrayAdapter(SearchActivity.this,android.R.layout.simple_list_item_1,news_list));


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
                Toast.makeText(SearchActivity.this, ""+t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
