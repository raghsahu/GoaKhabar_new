package dev.news.goakhabar.Api_Call;


import java.util.List;

import dev.news.goakhabar.Pojo.Category_Home.Home_categ_news_model;
import dev.news.goakhabar.Pojo.Category_Home_Model;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Api_Call {

    @GET(Base_Url.categories)
    Call<List<Category_Home_Model>> GetCategory();


    @GET("posts")
    Call<List<Home_categ_news_model>> GetCategoryNews(@Query("categories=") String id);


//    @FormUrlEncoded
//    @POST(Base_Url.get_community_detail)
//    Call<CommunityDeailsModel> GetCommunityDetails(
//            @Field("id") String community_id) ;


//
//    @GET(Base_Url.get_countries)
//    Call<CountryDeailsModel> GetAllCountry();


}
