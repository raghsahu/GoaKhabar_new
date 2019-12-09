package dev.news.goakhabar.Fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;

import dev.news.goakhabar.MainActivity;
import dev.news.goakhabar.ProfileActivity;
import dev.news.goakhabar.R;
import dev.news.goakhabar.Session.AppPreference;
import dev.news.goakhabar.Session.SessionManager;
import dev.news.goakhabar.SignupActivity;
import dev.news.goakhabar.Utils.Connectivity;

/**
 * Created by Raghvendra Sahu on 01-Dec-19.
 */
public class LoginFragment extends Fragment implements GoogleApiClient.OnConnectionFailedListener{
     GoogleApiClient googleApiClient;
    CallbackManager callbackManager;
    SignInButton sign_in_button;
    private static final int RC_SIGN_IN = 1;
    private String social_name = "", social_id = "", social_email = "", social_img = "";
    SessionManager sessionManager;
    LoginButton fb_login_button;


    public LoginFragment() {
// Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
// Inflate the layout for this fragment
        View root= inflater.inflate(R.layout.login_fragment, container, false);
        sessionManager=new SessionManager(getActivity());
        callbackManager = CallbackManager.Factory.create();
        sign_in_button=root.findViewById(R.id.g_sign_in_button);
        fb_login_button=root.findViewById(R.id.fb_login_button);

        fb_login_button.setPermissions("email", "public_profile");

        GoogleSignInOptions gso =  new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        googleApiClient=new GoogleApiClient.Builder(getActivity())
                .enableAutoManage(getActivity(),0,this)
                .addApi(Auth.GOOGLE_SIGN_IN_API,gso)
                .build();

        sign_in_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Connectivity.isConnected(getActivity())){
                    Intent intent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
                    startActivityForResult(intent,RC_SIGN_IN);

                }else {
                    Toast.makeText(getActivity(), "Please check Internet", Toast.LENGTH_SHORT).show();
                }
            }
        });


        // fb login
        fb_login_button.registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {@Override
                public void onSuccess(LoginResult loginResult) {

                    System.out.println("onSuccess");

                    String accessToken = loginResult.getAccessToken()
                            .getToken();
                    Log.i("accessToken", accessToken);

                    GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(),
                            new GraphRequest.GraphJSONObjectCallback() {@Override
                            public void onCompleted(JSONObject object, GraphResponse response) {

                                Log.i("LoginActivity",response.toString());
                                try {
                                    String id = object.getString("id");
                                    try {
                                        URL profile_pic = new URL(
                                                "http://graph.facebook.com/" + id + "/picture?type=large");
                                        Log.i("profile_pic",profile_pic + "");

                                    } catch (MalformedURLException e) {
                                        e.printStackTrace();
                                    }
                                    String name = object.getString("name");
                                    String email = object.getString("email");
                                    //  String gender = object.getString("gender");
                                    //String birthday = object.getString("birthday");

                                    AppPreference.setName(getActivity(),name);
                                    Intent intent=new Intent(getActivity(), MainActivity.class);
                                    startActivity(intent);
                                    getActivity().finish();

                                    sessionManager.setLogin(true);
                                    Toast.makeText(getActivity(), "successful login", Toast.LENGTH_SHORT).show();


                                    if (Connectivity.isConnected(getActivity())){
                                        //gotohomeFacebook(id,email,name);
                                    }else {
                                        Toast.makeText(getActivity(), "Please check Internet", Toast.LENGTH_SHORT).show();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            });
                    Bundle parameters = new Bundle();
                    parameters.putString("fields",
                            "id,name,email,gender, birthday");
                    request.setParameters(parameters);
                    request.executeAsync();
                }

                    @Override
                    public void onCancel() {
                        System.out.println("onCancel");
                        Toast.makeText(getActivity(), "cancel", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        System.out.println("onError");
                        Toast.makeText(getActivity(), "fail", Toast.LENGTH_SHORT).show();
                        Log.v("LoginActivity", exception.getCause().toString());
                    }
                });

    return root;
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
       callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==RC_SIGN_IN){
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }


    //***********
    private void handleSignInResult(GoogleSignInResult result){
        if(result.isSuccess()){

            Toast.makeText(getActivity(), "successful login", Toast.LENGTH_SHORT).show();
            GoogleSignInAccount acct = result.getSignInAccount();
            assert acct != null;
            social_name = acct.getDisplayName();
            social_email = acct.getEmail();

            AppPreference.setName(getActivity(),social_name);

            sessionManager.setLogin(true);
            Intent intent=new Intent(getActivity(), MainActivity.class);
            startActivity(intent);
            getActivity().finish();

            if (acct.getPhotoUrl() != null) {
                social_img = acct.getPhotoUrl().toString();
            } else {
                social_img = "";
            }
            Log.e("social_img ", " " + social_img);
            social_id = acct.getId();

            acct.getId();
            Log.e("GoogleResult", social_id + "------" + social_name + "------" + social_email);

            if (Connectivity.isConnected(getActivity())){
                //gotoHome();
            }else {
                Toast.makeText(getActivity(), "Please check Internet", Toast.LENGTH_SHORT).show();
            }

        }else{
            Toast.makeText(getActivity(),"Sign in cancel",Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        googleApiClient.stopAutoManage(getActivity());
        googleApiClient.disconnect();
    }
    @Override
    public void onStop() {
        super.onStop();
        if (googleApiClient != null && googleApiClient.isConnected()) {
            googleApiClient.stopAutoManage( getActivity());
            googleApiClient.disconnect();
        }
    }
}
