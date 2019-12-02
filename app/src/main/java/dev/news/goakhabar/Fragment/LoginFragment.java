package dev.news.goakhabar.Fragment;

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

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;

import dev.news.goakhabar.R;
import dev.news.goakhabar.Session.AppPreference;
import dev.news.goakhabar.Session.SessionManager;
import dev.news.goakhabar.Utils.Connectivity;

/**
 * Created by Raghvendra Sahu on 01-Dec-19.
 */
public class LoginFragment extends Fragment implements GoogleApiClient.OnConnectionFailedListener{
     GoogleApiClient googleApiClient;
    //CallbackManager callbackManager;
    SignInButton sign_in_button;
    private static final int RC_SIGN_IN = 1;
    private String social_name = "", social_id = "", social_email = "", social_img = "";
    SessionManager sessionManager;


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

        sign_in_button=root.findViewById(R.id.g_sign_in_button);

        GoogleSignInOptions gso =  new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        googleApiClient=new GoogleApiClient.Builder(getActivity())
                .enableAutoManage(getActivity(),this)
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

    return root;
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
       // callbackManager.onActivityResult(requestCode, resultCode, data);
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

}
