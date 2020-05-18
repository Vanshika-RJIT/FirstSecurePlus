package com.example.firstsecureplus;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class DashBoard extends AppCompatActivity {
    Button logout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        logout=findViewById(R.id.button7);


    }

    public void press1(View view) {
        SharedPreferences preferences= getSharedPreferences("checkbox",MODE_PRIVATE);
        SharedPreferences.Editor editor= preferences.edit();
        editor.putString("remember","false");
        editor.apply();
        finish();
    }
}
