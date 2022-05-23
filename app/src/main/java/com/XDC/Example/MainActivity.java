package com.XDC.Example;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.XDC.Example.XDC20methods.TransferXDCActivity;
import com.XDC.Example.wallet.CreateAccount;
import com.XDC.R;
import com.XDCJava.Model.TokenDetailsResponse;
import com.XDCJava.XDC20Client;
import com.XDCJava.callback.TokenDetailCallback;

import java.io.Serializable;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    private String token_address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView enterXdcAddress = findViewById(R.id.enter_xdc_address);
        Button btn_createaccount = (Button) findViewById(R.id.btn_createaccount);
        token_address = enterXdcAddress.getText().toString();
        Button transfer_amount = findViewById(R.id.transfer_amount);
        Button btn_test721 = (Button) findViewById(R.id.btn_test721);
        transfer_amount.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, TransferXDCActivity.class);
            startActivity(intent);
        });


        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);


        initUI();

        btn_test721.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, XDC721MainActivity.class);

            startActivity(intent);
        });

        Button submit_button = findViewById(R.id.submit);

        submit_button.setOnClickListener(v -> XDC20Client.getInstance().getTokenoinfo(token_address, new TokenDetailCallback() {
            @Override
            public void success(TokenDetailsResponse tokenDetailsResponse) {
                Intent intent = new Intent(MainActivity.this, Details.class);
                intent.putExtra("tokendetail", (Serializable) tokenDetailsResponse);
                startActivity(intent);
            }

            @Override
            public void failure(Throwable t) {
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void failure(String message) {
                Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show();
            }
        }));


        btn_createaccount.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, CreateAccount.class);
            startActivity(intent);
        });


    }


    private void initUI() {
        //UI reference of textView
        AutoCompleteTextView tokenAutoTV = findViewById(R.id.customerTextView);

        // create list of customer
        ArrayList<String> customerList = getCustomerList();

        //Create adapter
        ArrayAdapter<String> adapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_spinner_item, customerList);

        //Set adapter
        tokenAutoTV.setAdapter(adapter);

        tokenAutoTV.setOnItemClickListener((parent, view, position, id) -> token_address = adapter.getItem(position));


    }

    private ArrayList<String> getCustomerList() {
        ArrayList<String> address = new ArrayList<>();

        address.add("0xc4bd1127a5227659b5ef3070092b6740046f3c3a");
        address.add("0xce20035eceecd1f94ac4a828de5e3f9b7d2c7898");
        address.add("0x847aefb3d207e69749e970f8574743a4f388b6f2");
        return address;
    }


}