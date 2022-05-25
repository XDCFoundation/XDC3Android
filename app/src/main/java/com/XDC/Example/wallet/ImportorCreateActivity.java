package com.XDC.Example.wallet;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.XDC.R;


public class ImportorCreateActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_importaccount);


        TextView btn_createaccount = (TextView) findViewById(R.id.btn_createaccount);
        TextView btn_importaccount = (TextView) findViewById(R.id.btn_importaccount);


        btn_createaccount.setOnClickListener(v -> {
            Intent intent = new Intent(ImportorCreateActivity.this, CreateAccount.class);
            startActivity(intent);
        });

        btn_importaccount.setOnClickListener(v -> {
            Intent intent = new Intent(ImportorCreateActivity.this, Importwallet.class);
            startActivity(intent);
        });


    }


}