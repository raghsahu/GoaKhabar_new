package dev.news.goakhabar.Fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputEditText;

import dev.news.goakhabar.Api_Call.APIClient3;
import dev.news.goakhabar.Api_Call.Api_Call;
import dev.news.goakhabar.MainActivity;
import dev.news.goakhabar.Pojo.LoginModel.Login_model;
import dev.news.goakhabar.Pojo.Signup_model;
import dev.news.goakhabar.R;
import dev.news.goakhabar.Session.AppPreference;
import dev.news.goakhabar.Session.SessionManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Raghvendra Sahu on 01-Dec-19.
 */
public class SignupFragment extends Fragment {

    TextInputEditText et_username,et_email,et_pw;
    TextView tv_submit;
    SessionManager sessionManager;

    public SignupFragment() {
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
        View root= inflater.inflate(R.layout.signup_fragment, container, false);
        sessionManager=new SessionManager(getActivity());
        et_username=root.findViewById(R.id.et_username);
        et_email=root.findViewById(R.id.et_email);
        et_pw=root.findViewById(R.id.et_pw);
        tv_submit=root.findViewById(R.id.tv_submit);

        //*****manual login button
        tv_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
        String Et_Username=et_username.getText().toString();
        String Et_Password=et_pw.getText().toString();
        String Et_Email=et_email.getText().toString();

        if (!Et_Password.isEmpty() && !Et_Username.isEmpty() && !Et_Email.isEmpty()){

            SignupApi(Et_Password,Et_Username,Et_Email);
        }else {
            Toast.makeText(getActivity(), "Please enter all fields", Toast.LENGTH_SHORT).show();
        }

            }
        });

        return  root;
    }

    private void SignupApi(String et_password, String et_username, String et_email) {


        final ProgressDialog progressDialog = new ProgressDialog(getActivity(),R.style.MyGravity);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.show();

        Api_Call apiInterface = APIClient3.getClient().create(Api_Call.class);

        Call<Signup_model> call = apiInterface.GetSignup(et_username,et_password,et_email,"get_nonce","both",
                "json","controller","method");

        call.enqueue(new Callback<Signup_model>() {
            @Override
            public void onResponse(Call<Signup_model> call, Response<Signup_model> response) {

                try{

                    if (response!=null){

                        Log.e("login_status",response.body().getStatus());
                        if (response.body().getStatus().equalsIgnoreCase("ok")){
                           // AppPreference.setName(getActivity(),response.body().getUser().getFirstname());
                            //AppPreference.setUser_Id(getActivity(), String.valueOf(response.body().getUser().getId()));
                            Toast.makeText(getActivity(), ""+response.body().getStatus(), Toast.LENGTH_SHORT).show();

                            sessionManager.setLogin(true);
//                            Intent intent=new Intent(getActivity(), MainActivity.class);
//                            startActivity(intent);
//                            getActivity().finish();

                        }


                    }



                }catch (Exception e){
                    Log.e("error_cate1", e.getMessage());
                }
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<Signup_model> call, Throwable t) {
                progressDialog.dismiss();
                Log.e("error_category1",t.getMessage());
                //Toast.makeText(AllCountries.this, ""+t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }
}
