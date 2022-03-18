package com.dev.userlogin;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


public class LoginFragment extends Fragment {
    TextInputLayout phoneNum , pass;
    ProgressBar progressBar;
    Button loginButton;
    DatabaseReference reference;
    String userEPhone;
    String userEPass;
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
        View view =  inflater.inflate(R.layout.fragment_login, container, false);
        phoneNum = view.findViewById(R.id.PHONE);
        pass = view.findViewById(R.id.password);
        progressBar  = view.findViewById(R.id.progressBar);
        loginButton = view.findViewById(R.id.loginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!validatepass() | !validatephone()){
                    return;
                }else{
                    setVis();
                    isUser();
                }
            }
        });
        return view;
    }

    public boolean validatephone() {
        String ph = phoneNum.getEditText().getText().toString();
        if(ph.isEmpty()){
            phoneNum.setError("Enter phone number");
            phoneNum.requestFocus();
            return false;
        }else{
            phoneNum.setError(null);
            phoneNum.setErrorEnabled(false);
            return true;
        }
    }
    public boolean validatepass() {
        String passW = pass.getEditText().getText().toString();
        if(passW.isEmpty()){
            pass.setError("Enter password");
            pass.requestFocus();
            return false;
        }else{
            pass.setError(null);
            pass.setErrorEnabled(false);
            return true;
        }
    }
    public void setVis(){
        if(loginButton.getVisibility()==View.VISIBLE){
            loginButton.setVisibility(View.GONE);
            progressBar.setVisibility(View.VISIBLE);
        }else{
            loginButton.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
        }
    }
    public void isUser(){
        userEPhone = phoneNum.getEditText().getText().toString().trim();
        userEPass = pass.getEditText().getText().toString().trim();
        reference = FirebaseDatabase.getInstance().getReference("users");
        Query checkUser = reference.orderByChild("phoneNo").equalTo(userEPhone);
        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    pass.setError(null);
                    pass.setErrorEnabled(false);
                    phoneNum.setError(null);
                    phoneNum.setErrorEnabled(false);
                    String passwordDb = snapshot.child(userEPhone).child("password").getValue(String.class);
                    if (passwordDb.equals(userEPass)){
                        reference.child(userEPhone).child("isLogged").setValue("true");
                        saveUser();
                        Intent intent = new Intent(getActivity(), HomePage.class);
                        startActivity(intent);
                    }else{
                        pass.setError("Wrong password");
                        pass.requestFocus();
                        setVis();
                    }
                }else{
                    phoneNum.setError("Wrong phone number!");
                    phoneNum.requestFocus();
                    setVis();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void saveUser(){
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("userData", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("num", userEPhone);
        editor.apply();
    }

}