package dev.news.goakhabar.Api_Call;


import java.util.List;

import dev.news.goakhabar.Pojo.CategoryWise_new.Home_categ_news_model;
import dev.news.goakhabar.Pojo.Category_Home.Category_Home_Model;
import dev.news.goakhabar.Pojo.Header_menu.HeaderMenuModel;
import dev.news.goakhabar.Pojo.LoginModel.Login_model;
import dev.news.goakhabar.Pojo.Menu_Wise_News.Menu_categ_news_model;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Api_Call {

    @GET(Base_Url.categories)
    Call<List<Category_Home_Model>> GetCategory();

    @GET("posts")
    Call<List<Home_categ_news_model>> GetCategoryNews(@Query("categories=") int id);

    @GET("wp-api-menus/v2/menus/4")
    Call<HeaderMenuModel> GetMenu();

    @GET("generate_auth_cookie/")
    Call<Login_model> GetLogin( @Query("username")String et_username,  @Query("password")String et_password);

    @GET("get_category_posts")
    Call<Menu_categ_news_model> GetHeaderNews(@Query("slug") String news_title);


//    @FormUrlEncoded
//    @POST(Base_Url.get_community_detail)
//    Call<CommunityDeailsModel> GetCommunityDetails(
//            @Field("id") String community_id) ;


//
//    @GET(Base_Url.get_countries)
//    Call<CountryDeailsModel> GetAllCountry();


}
