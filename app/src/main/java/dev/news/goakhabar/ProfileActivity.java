package dev.news.goakhabar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
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

import dev.news.goakhabar.Session.AppPreference;
import dev.news.goakhabar.Session.SessionManager;

public class ProfileActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener{

    ImageView back_press;
    TextView tv_logout;

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
                    //disconnectFromFacebook();
                } else{
                    Log.e("logout_app", "local login logout");
                    // not signed in. Show the "sign in" button and explanation.
                    //disconnectFromFacebook();
                    manager.logoutUser();
                    AppPreference.setName(ProfileActivity.this, "");
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
