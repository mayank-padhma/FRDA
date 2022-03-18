package com.dev.userlogin;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.telephony.TelephonyManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Locale;


public class SignupFragment extends Fragment {

    TextInputLayout phonenum, username, password2, country;
    TextInputLayout password;
    FirebaseDatabase database;
    DatabaseReference reference;
    Button signupbutton;
    ProgressBar progressBar;
    String countryCode;
    String code;
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
        View view =  inflater.inflate(R.layout.fragment_signup, container, false);
        phonenum = view.findViewById(R.id.phone);
        username = view.findViewById(R.id.username);
        password = view.findViewById(R.id.password);
        password2 = view.findViewById(R.id.password2);
        country = view.findViewById(R.id.country);
        signupbutton = view.findViewById(R.id.signupButton);
        progressBar = view.findViewById(R.id.progressBar);
        signupbutton = view.findViewById(R.id.signupButton);
        if (getCountry()!=null){
            country.getEditText().setText(countryCode);
            country.getEditText().setEnabled(false);
        }
        signupbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                database = FirebaseDatabase.getInstance();
                reference = database.getReference("users");
                String name = username.getEditText().getText().toString();
                String passWord = password.getEditText().getText().toString();
                String passWord2 = password2.getEditText().getText().toString();
                String phoneNum = phonenum.getEditText().getText().toString();
                String countryName = country.getEditText().getText().toString();
                if (!validateName(name) | !validateCountry(countryName) | !validatePhone(phoneNum) | !validatePass(passWord) | !validatePass2(passWord2, passWord)){
                    return;
                }else {
                    setVis();
                    UserHelperClass userHelperClass = new UserHelperClass(name, phoneNum, passWord, countryName, "http://www.geognos.com/api/en/countries/flag/"+code.toUpperCase()+".png");
                    reference.child(phoneNum).setValue(userHelperClass);
                    reference.child(phoneNum).child("isLogged").setValue("true");
                    Intent intent = new Intent(getActivity(), HomePage.class);
                    saveUser();
                    startActivity(intent);
                }
            }
        });
        return view;

    }
    public void setVis(){
        if(signupbutton.getVisibility()==View.VISIBLE){
            signupbutton.setVisibility(View.GONE);
            progressBar.setVisibility(View.VISIBLE);
        }else{
            signupbutton.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
        }
    }
    public boolean validateName(String name){
        if (name.isEmpty()){
            username.setError("This field can't be empty");
            return false;
        }else{
            username.setError(null);
            username.setErrorEnabled(false);
            return true;
        }
    }
    public boolean validatePhone(String phone){
        String noWhiteSpace = ".*\\S+.*";
        if(phone.isEmpty()){
            phonenum.setError("This field can't be empty");
            return false;
        }else if(!phone.matches(noWhiteSpace)){
            phonenum.setError("No white spaces allowed");
            return false;
        }else if(phone.length()<10){
            phonenum.setError("Please enter correct phone number");
            return false;
        }else{
            phonenum.setError(null);
            phonenum.setErrorEnabled(false);
            return true;
        }
    }
    public boolean validatePass(String pass){
        String noWhiteSpace = ".*\\S+.*";
        if(pass.isEmpty()){
            password.setError("This field can't be empty");
            return false;
        }else if (!pass.matches(noWhiteSpace)){
            password.setError("No white spaces allowed");
            return false;
        }else if(pass.length()<8){
            password.setError("Minimum 8 characters needed");
            return false;
        }else{
            password.setError(null);
            password.setErrorEnabled(false);
            return true;
        }
    }
    public boolean validatePass2(String pass2,String pass1){
        if(pass2.isEmpty()){
            password2.setError("This field can't be empty");
            return false;
        }else if(!pass1.equals(pass2)){
            password2.setError("Passwords do not match");
            return false;
        }
        else{
            password2.setError(null);
            password2.setErrorEnabled(false);
            return true;
        }
    }
    public boolean validateCountry(String countryN){
        if (countryN.isEmpty()){
            country.setError("This field can't be empty");
            return false;
        }else{
            country.setError(null);
            country.setErrorEnabled(false);
            return true;
        }
    }
    public void saveUser(){
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("userData", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("num", phonenum.getEditText().getText().toString().trim());
        editor.apply();
    }
    public String getCountry(){
        TelephonyManager telephonyManager = (TelephonyManager) getActivity().getSystemService(Context.TELEPHONY_SERVICE);
        code = telephonyManager.getNetworkCountryIso();
        Locale l = new Locale("", code);
        countryCode = l.getDisplayCountry();
        return countryCode;
    }
}