package com.XDC.Example.XDC721methods;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.XDC.Example.utils.SharedPreferenceHelper;
import com.XDC.Example.utils.Utility;
import com.XDC.R;
import com.XDCJava.Model.Token721DetailsResponse;
import com.XDCJava.Model.WalletData;
import com.XDCJava.XDC721Client;

import java.io.IOException;
import java.math.BigInteger;
import java.util.concurrent.ExecutionException;

public class SafeTransferfromXDC721Activity extends AppCompatActivity {

    private EditText edt_receiver_address, edt_token_totransfer, edt_safetrans_gas_price,
            edt_safetrans_gas_limit, caller_address, caller_privatekey;
    private TextView text_transaction_hash;
    private WalletData user_wallet;
    private Token721DetailsResponse tokenDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_safetransferfromtoken_xdc721);

        edt_receiver_address = (EditText) findViewById(R.id.receiver_address);
        edt_token_totransfer = (EditText) findViewById(R.id.value);
        edt_safetrans_gas_price = (EditText) findViewById(R.id.edt_safetrans_gas_price);
        edt_safetrans_gas_limit = (EditText) findViewById(R.id.edt_safetrans_gas_limit);
        Button send_approve = (Button) findViewById(R.id.send_approve);
        caller_address = findViewById(R.id.caller_address);
        caller_privatekey = findViewById(R.id.caller_privatekey);
        ImageView back_txdc = findViewById(R.id.back_txdc);
        text_transaction_hash = (TextView) findViewById(R.id.text_transaction_hash);
        user_wallet = Utility.getProfile(SafeTransferfromXDC721Activity.this);
        tokenDetail = Utility.getnftinfo(SafeTransferfromXDC721Activity.this);
        send_approve.setOnClickListener(v -> {
            if (!hasText(caller_address)) {
                caller_address.setError(getResources().getString(R.string.error_empty));
            } else if (!hasText(caller_privatekey)) {
                caller_privatekey.setError(getResources().getString(R.string.error_empty));
            } else if (!hasText(edt_receiver_address)) {
                edt_receiver_address.setError(getResources().getString(R.string.error_empty));

            } else if (!hasText(edt_token_totransfer)) {
                edt_token_totransfer.setError(getResources().getString(R.string.error_empty));

            } else {


                if (user_wallet != null && user_wallet.getAccountAddress() != null && user_wallet.getAccountAddress().length() > 0 && user_wallet.getPrivateKey() != null) {
                    try {
                        String hash = XDC721Client.getInstance().
                                safeTransferFrom(tokenDetail.getTokenAddress(),
                                        caller_privatekey.getText().toString(),
                                        edt_receiver_address.getText().toString(),
                                        edt_token_totransfer.getText().toString(),
                                        new BigInteger(edt_safetrans_gas_price.getText().toString()),
                                        new BigInteger(edt_safetrans_gas_limit.getText().toString()));
                        text_transaction_hash.setText(hash);
                        Utility.closeKeyboard(SafeTransferfromXDC721Activity.this);
                        SharedPreferenceHelper.setSharedPreferenceString(SafeTransferfromXDC721Activity.this, "nfthash", hash);
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }


            }
        });

        back_txdc.setOnClickListener(v -> onBackPressed());
    }


    public static boolean hasText(EditText s) {
        return !s.getText().toString().trim().equalsIgnoreCase("");
    }
}