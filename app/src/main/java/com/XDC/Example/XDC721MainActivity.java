package com.XDC.Example;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;

import com.XDC.R;
import com.XDCJava.Model.Token721DetailsResponse;
import com.XDCJava.XDC721Client;
import com.XDCJava.callback.Token721DetailCallback;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Objects;


public class XDC721MainActivity extends AppCompatActivity implements View.OnFocusChangeListener {

    private TextView enterXdcAddress;
    private AppCompatEditText etGasPrice, etGasLimit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main721);

        BigInteger gasPrice = XDC721Client.getInstance().getGasPrice();
        BigInteger gasLimit = XDC721Client.getInstance().getGasLimit();

        enterXdcAddress = findViewById(R.id.enter_xdc_address);
        etGasPrice = findViewById(R.id.etGasPrice);
        etGasLimit = findViewById(R.id.etGasLimit);
        if (gasPrice != null) {
            etGasPrice.setText(gasPrice + "");
        }
        if (gasLimit != null) {
            etGasLimit.setText(gasLimit + "");
        }
        etGasPrice.setOnFocusChangeListener(this);
        etGasLimit.setOnFocusChangeListener(this);
        Button submit_button = findViewById(R.id.submit);
        Button deploy_contract = findViewById(R.id.deploy_contract);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        submit_button.setOnClickListener(v -> {

            if (enterXdcAddress.getText().toString().length() > 0) {
                XDC721Client.getInstance().getTokenoinfo(enterXdcAddress.getText().toString(), new Token721DetailCallback() {
                    @Override
                    public void success(Token721DetailsResponse tokenDetailsResponse) {
                        Intent intent = new Intent(XDC721MainActivity.this, Details721.class);
                        intent.putExtra("tokendetail", (Serializable) tokenDetailsResponse);
                        startActivity(intent);
                    }

                    @Override
                    public void success(String message) {

                    }

                    @Override
                    public void failure(Throwable t) {

                    }

                    @Override
                    public void failure(String message) {

                    }
                });
            } else {
                Toast.makeText(XDC721MainActivity.this, "Please Enter Token Address", Toast.LENGTH_LONG).show();
            }


        });


        deploy_contract.setOnClickListener(v -> {
            try {
                XDC721Client.getInstance().deploy_NFT("5724b3006f227d6a8efac9d9310beec7874cf0dcdc12e5d0c4890d2ba497b9c6", new Token721DetailCallback() {
                            @Override
                            public void success(Token721DetailsResponse tokenApiModel) {

                            }

                            @Override
                            public void success(String message) {
                                Log.e("message", message);
                                enterXdcAddress.setText(message);
                            }

                            @Override
                            public void failure(Throwable t) {

                            }

                            @Override
                            public void failure(String message) {

                            }
                        }, new BigInteger(Objects.requireNonNull(etGasPrice.getText()).toString()),
                        new BigInteger(Objects.requireNonNull(etGasLimit.getText()).toString()));
            } catch (Exception e) {
                e.printStackTrace();
            }

           /* try {
                //XDC721Client.getInstance().mintToken("0x0c0d7D6a5c174Da1CE0912836028f347bD4C2157");
            }
            */


        });


    }


    @Override
    public void onFocusChange(View view, boolean hasFocus) {
        if (hasFocus) {
            new AlertDialog.Builder(XDC721MainActivity.this)
                    .setMessage(getString(R.string.err_gas_price_limit_edit))
                    .setPositiveButton(android.R.string.ok, (dialog, which) -> dialog.dismiss())
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }
    }
}