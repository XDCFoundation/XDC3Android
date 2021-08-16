package com.sdk_android;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.math.BigInteger;

import com.XDCAndroid.XDC20Client;

public class TransferAmount extends AppCompatActivity {

    EditText edt_private_key, edt_gaslimit, edt_gasprice, edt_sender_address, edt_receiver_address, edt_token_totransfer;
    Button button_send;
    TextView text_transaction_hash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer_amount);

        edt_private_key = (EditText) findViewById(R.id.private_key);
        edt_sender_address = (EditText) findViewById(R.id.sender_address);
        edt_receiver_address = (EditText) findViewById(R.id.receiver_address);
        edt_token_totransfer = (EditText) findViewById(R.id.value);
        edt_gasprice = (EditText) findViewById(R.id.edt_gasprice);
        edt_gaslimit = (EditText) findViewById(R.id.edt_gaslimit);
        button_send = (Button) findViewById(R.id.send);
        text_transaction_hash = (TextView) findViewById(R.id.text_transaction_hash);

        button_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!hasText(edt_private_key)) {
                    edt_private_key.setError(getResources().getString(R.string.error_empty));
                } else if (!hasText(edt_sender_address)) {
                    edt_sender_address.setError(getResources().getString(R.string.error_empty));
                } else if (!hasText(edt_receiver_address)) {
                    edt_receiver_address.setError(getResources().getString(R.string.error_empty));
                } else if (!hasText(edt_token_totransfer)) {
                    edt_token_totransfer.setError(getResources().getString(R.string.error_empty));
                } else if (!hasText(edt_gasprice)) {
                    edt_gasprice.setError(getResources().getString(R.string.error_empty));
                } else if (!hasText(edt_gaslimit)) {
                    edt_gaslimit.setError(getResources().getString(R.string.error_empty));
                } else {


                    String trasaction_hash = XDC20Client.getInstance().TransferXdc(edt_private_key.getText().toString(), edt_sender_address.getText().toString(), edt_receiver_address.getText().toString(), new BigInteger(String.valueOf(edt_token_totransfer.getText())), Long.parseLong(edt_gasprice.getText().toString()), Long.parseLong(edt_gaslimit.getText().toString()));

                    text_transaction_hash.setText(trasaction_hash);

                }
            }


        });
    }


    public static boolean hasText(EditText s) {
        if (s.getText().toString().trim().equalsIgnoreCase(""))
            return false;
        else
            return true;
    }
}