package com.XDC.Example.XDC721methods;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.XDC.Example.utils.Utility;
import com.XDC.R;
import com.XDCJava.XDC721Client;

public class OwnerOfXDC721Activity extends AppCompatActivity {

    private EditText token_address, token_index;
    private TextView text_transaction_hash;

    public static boolean hasText(EditText s) {
        return !s.getText().toString().trim().equalsIgnoreCase("");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ownerof_xdc721);

        token_address = (EditText) findViewById(R.id.token_address);
        token_index = (EditText) findViewById(R.id.value);
        Button send_approve = (Button) findViewById(R.id.send_approve);
        ImageView back_txdc = findViewById(R.id.back_txdc);
        text_transaction_hash = (TextView) findViewById(R.id.text_transaction_hash);

        send_approve.setOnClickListener(v -> {
            if (!hasText(token_address)) {
                token_address.setError(getResources().getString(R.string.error_empty));
            } else if (!hasText(token_index)) {
                token_index.setError(getResources().getString(R.string.error_empty));
            } else {
                try {
                    String hash = null;
                    try {
                        hash = XDC721Client.getInstance().getOwnerof(token_address.getText().toString(), token_index.getText().toString());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    text_transaction_hash.setText(hash);
                    Utility.closeKeyboard(OwnerOfXDC721Activity.this);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        back_txdc.setOnClickListener(v -> onBackPressed());
    }
}