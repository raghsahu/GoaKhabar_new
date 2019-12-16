package dev.news.goakhabar;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import dev.news.goakhabar.Api_Call.APIClient;
import dev.news.goakhabar.Api_Call.APIClient3;
import dev.news.goakhabar.Api_Call.Api_Call;
import dev.news.goakhabar.Pojo.CategoryWise_new.Home_categ_news_model;
import dev.news.goakhabar.Pojo.Profile_model;
import dev.news.goakhabar.Session.AppPreference;
import dev.news.goakhabar.Utils.Connectivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchActivity extends AppCompatActivity {

    ImageView back_press;
    AutoCompleteTextView search;
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

                   // getNewsSearch(s);

                }else {
                    Toast.makeText(SearchActivity.this, "Please check Internet", Toast.LENGTH_SHORT).show();
                }
            }
        });




        search.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Integer selectedItem= (Integer) search.getAdapter().getItem(position);

                String id1=catByNews.get(selectedItem).getId().toString();
                Log.e("search_id",id1.toString());

            }
        });


        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    //     state_list.clear();
                    //  stateed.setAdapter(null);
                    //   StateAdapter.notifyDataSetChanged();
                    if(catByNews.size() <=0) {
                       // getNewsSearch(s.toString());
                    }else {
                        //Toast.makeText(S.this, "done", Toast.LENGTH_SHORT).show();
                    }
                }catch (Exception e)
                {
                    e.printStackTrace();
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

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

//                        ArrayAdapter<String> NewsAdapter = new ArrayAdapter<String>(SearchActivity.this,
//                                android.R.layout.simple_spinner_dropdown_item, news_list);
//                        // CountryAdapter.setDropDownViewResource(android.R.layout.select_dialog_item);
//                        search.setThreshold(1);
//                        search.setAdapter(NewsAdapter);
//                        search.showDropDown();

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
