package dev.news.goakhabar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

import dev.news.goakhabar.Api_Call.APIClient3;
import dev.news.goakhabar.Api_Call.Api_Call;
import dev.news.goakhabar.Pojo.LoginModel.Login_model;
import dev.news.goakhabar.Pojo.Profile_model;
import dev.news.goakhabar.Session.AppPreference;
import dev.news.goakhabar.Session.SessionManager;
import dev.news.goakhabar.Utils.Connectivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener{

    ImageView back_press;
    TextView tv_logout,tv_name,tv_nickname,tv_email;

    private GoogleApiClient googleApiClient;
    private GoogleSignInOptions gso;
    SessionManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        manager=new SessionManager(ProfileActivity.this);

        back_press=findViewById(R.id.back_press);
        tv_logout=findViewById(R.id.tv_logout);
        tv_name=findViewById(R.id.tv_name);
        tv_nickname=findViewById(R.id.tv_nickname);
        tv_email=findViewById(R.id.tv_email);


        try {
            tv_name.setText(AppPreference.getName(ProfileActivity.this));
            tv_nickname.setText(AppPreference.getName(ProfileActivity.this));
            tv_email.setText(AppPreference.getEmail(ProfileActivity.this));

        }catch (Exception e){

        }


        if (Connectivity.isConnected(this)){
            getprofile();
        }else {
            Toast.makeText(this, "Please check internet", Toast.LENGTH_SHORT).show();
        }

        gso =  new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        googleApiClient=new GoogleApiClient.Builder(this)
                .enableAutoManage(this,this)
                .addApi(Auth.GOOGLE_SIGN_IN_API,gso)
                .build();

        back_press.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        tv_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout_user();

            }
        });
    }

    private void getprofile() {
        final ProgressDialog progressDialog = new ProgressDialog(ProfileActivity.this,R.style.MyGravity);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.show();

        Api_Call apiInterface = APIClient3.getClient().create(Api_Call.class);

        Call<Profile_model> call = apiInterface.GetProfile(AppPreference.getUser_Id(ProfileActivity.this));

        call.enqueue(new Callback<Profile_model>() {
            @Override
            public void onResponse(Call<Profile_model> call, Response<Profile_model> response) {

                try{

                    if (response!=null){

                        Log.e("login_status",response.body().getStatus());
                        if (response.body().getStatus().equalsIgnoreCase("ok")){
                        tv_name.setText(response.body().getFirstname()+" "+response.body().getLastname());
                        tv_nickname.setText(response.body().getNickname());


                        }


                    }



                }catch (Exception e){
                    Log.e("error_cate1", e.getMessage());
                }
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<Profile_model> call, Throwable t) {
                progressDialog.dismiss();
                Log.e("error_category1",t.getMessage());
                Toast.makeText(ProfileActivity.this, ""+t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void logout_user() {

        final AlertDialog.Builder dialog = new AlertDialog.Builder(ProfileActivity.this).setTitle("GoaKhabar")
                .setMessage("Are you sure, you want to logout this app");

        dialog.setNegativeButton("no", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        dialog.setPositiveButton("yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int whichButton) {

                if (googleApiClient != null && googleApiClient.isConnected()) {
                    // signed in. Show the "sign out" button and explanation.
                    google_logout();
                   disconnectFromFacebook();
                } else{
                    Log.e("logout_app", "local login logout");
                    // not signed in. Show the "sign in" button and explanation.
                    disconnectFromFacebook();
                    manager.logoutUser();
                    AppPreference.setName(ProfileActivity.this, "");
                    AppPreference.setUser_Id(ProfileActivity.this, "");
                    AppPreference.setEmail(ProfileActivity.this, "");
                    Intent intent=new Intent(ProfileActivity.this, SignupActivity.class);
                    startActivity(intent);
                    finish();

                }


            }

        });
        final AlertDialog alert = dialog.create();
        alert.show();

    }

    private void disconnectFromFacebook() {
        Log.e("logout_app_fb", "fb login logout");
        if (AccessToken.getCurrentAccessToken() == null) {
            return; // already logged out
        }

        new GraphRequest(AccessToken.getCurrentAccessToken(), "/me/permissions/",
                null, HttpMethod.DELETE, new GraphRequest
                .Callback() {
            @Override
            public void onCompleted(GraphResponse graphResponse) {

                LoginManager.getInstance().logOut();
                manager.logoutUser();
                AppPreference.setName(ProfileActivity.this, "");
                AppPreference.setUser_Id(ProfileActivity.this, "");
                AppPreference.setEmail(ProfileActivity.this, "");
                Intent intent=new Intent(ProfileActivity.this, SignupActivity.class);
                startActivity(intent);
                finish();

            }
        }).executeAsync();
    }

    private void google_logout() {
        Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        if (status.isSuccess()){
                            Toast.makeText(ProfileActivity.this, "logout success", Toast.LENGTH_SHORT).show();
                            Log.e("logout_app_gmail", "google login logout");
                            manager.logoutUser();
                            AppPreference.setName(ProfileActivity.this, "");
                            AppPreference.setUser_Id(ProfileActivity.this, "");
                            AppPreference.setEmail(ProfileActivity.this, "");
                            Intent intent=new Intent(ProfileActivity.this,SignupActivity.class);
                            startActivity(intent);
                            finish();
                        }else{
                            Toast.makeText(getApplicationContext(),"Session not close",Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
