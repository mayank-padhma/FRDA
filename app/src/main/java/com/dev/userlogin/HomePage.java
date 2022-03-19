package com.dev.userlogin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

public class HomePage extends AppCompatActivity {
    RecyclerView recyclerView;
    TextView loadText;
    String phone;
    ArrayList<UserHelperClass> usersList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        loadText = findViewById(R.id.loadText);
        usersList = new ArrayList<>();
        phone = getUser();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    String name = dataSnapshot.child("username").getValue(String.class);
                    String phonenum = dataSnapshot.child("phoneNo").getValue(String.class);
                    String country = dataSnapshot.child("country").getValue(String.class);
                    String cc = dataSnapshot.child("cc").getValue(String.class);
                    usersList.add(new UserHelperClass(name, phonenum, country, cc));
                }
                RecAdapter recAdapter;
                recAdapter = new RecAdapter(usersList);
                recyclerView.setAdapter(recAdapter);
                recyclerView.setVisibility(View.VISIBLE);
                loadText.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
    public void logout(View view){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
        reference.child(phone).child("isLogged").setValue("false");
        recyclerView.setAdapter(null);
        overWrite();
        Intent intent = new Intent(HomePage.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    public String getUser(){
        SharedPreferences sharedPreferences = getSharedPreferences("userData", MODE_PRIVATE);
        return sharedPreferences.getString("num", null);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }
    public void overWrite(){
        SharedPreferences sharedPreferences = getSharedPreferences("userData", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("num", null);
        editor.apply();
    }
}