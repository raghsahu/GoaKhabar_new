package dev.news.goakhabarr.Fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

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
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;

import dev.news.goakhabarr.Api_Call.APIClient3;
import dev.news.goakhabarr.Api_Call.Api_Call;
import dev.news.goakhabarr.Activity.MainActivity;
import dev.news.goakhabarr.Pojo.LoginModel.Login_model;
import dev.news.goakhabarr.R;
import dev.news.goakhabarr.Session.SharedPreference;
import dev.news.goakhabarr.Session.SessionManager;
import dev.news.goakhabarr.Utils.Connectivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
    TextInputEditText et_username,et_password;
    TextView tv_login;


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
        et_password=root.findViewById(R.id.et_password);
        et_username=root.findViewById(R.id.et_username);
        tv_login=root.findViewById(R.id.tv_login);

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
                new FacebookCallback<LoginResult>() {
                    @Override
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

                                    SharedPreference.setName(getActivity(),name);
                                    Intent intent=new Intent(getActivity(), MainActivity.class);
                                    startActivity(intent);
                                   // getActivity().finish();

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
                        Log.e("LoginActivity", exception.toString());
                        Log.e("LoginActivity1", exception.getMessage());
                    }
                });


        //*****manual login button
        tv_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String Et_Username=et_username.getText().toString();
                String Et_Password=et_password.getText().toString();

                if (!Et_Password.isEmpty() && !Et_Username.isEmpty()){

                  LoginApi(Et_Password,Et_Username);
                }else {
                    Toast.makeText(getActivity(), "Please enter username and password", Toast.LENGTH_SHORT).show();
                }
            }
        });

    return root;
    }

    private void LoginApi(String et_password, String et_username) {

        final ProgressDialog progressDialog = new ProgressDialog(getActivity(),R.style.MyGravity);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.show();

        Api_Call apiInterface = APIClient3.getClient().create(Api_Call.class);

        Call<Login_model> call = apiInterface.GetLogin(et_username,et_password);

        call.enqueue(new Callback<Login_model>() {
            @Override
            public void onResponse(Call<Login_model> call, Response<Login_model> response) {

                try{

                    if (response!=null){

                        Log.e("login_status",response.body().getStatus());
                        if (response.body().getStatus().equalsIgnoreCase("ok")){
                            SharedPreference.setName(getActivity(),response.body().getUser().getFirstname());
                            SharedPreference.setUser_Id(getActivity(), String.valueOf(response.body().getUser().getId()));
                            Toast.makeText(getActivity(), "Login success", Toast.LENGTH_SHORT).show();

                            sessionManager.setLogin(true);
                            Intent intent=new Intent(getActivity(), MainActivity.class);
                            startActivity(intent);
                            getActivity().finish();

                        }


                        }



                }catch (Exception e){
                    Log.e("error_cate1", e.getMessage());
                }
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<Login_model> call, Throwable t) {
                progressDialog.dismiss();
                Log.e("error_category1",t.getMessage());
                //Toast.makeText(AllCountries.this, ""+t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

            }
        });
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

            SharedPreference.setName(getActivity(),social_name);
            SharedPreference.setEmail(getActivity(),social_email);

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
