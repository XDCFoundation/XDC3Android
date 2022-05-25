package com.XDC.Example.XDC721methods;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;

import com.XDC.Example.utils.SharedPreferenceHelper;
import com.XDC.Example.utils.Utility;
import com.XDC.R;
import com.XDCJava.Model.Token721DetailsResponse;
import com.XDCJava.Model.WalletData;
import com.XDCJava.XDC721Client;

import java.math.BigInteger;

public class ApproveXDC721Activity extends AppCompatActivity implements View.OnFocusChangeListener {

    private EditText edt_receiver_address, edt_token_totransfer;
    private TextView text_transaction_hash;
    private WalletData user_wallet;
    private Token721DetailsResponse tokenDetail;

    private AppCompatEditText etGasPrice, etGasLimit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_approve_xdc721);

        BigInteger gasPrice = XDC721Client.getInstance().getGasPrice();
        BigInteger gasLimit = XDC721Client.getInstance().getGasLimit();

        edt_receiver_address = (EditText) findViewById(R.id.receiver_address);
        edt_token_totransfer = (EditText) findViewById(R.id.value);

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

        Button send_approve = (Button) findViewById(R.id.send_approve);
        ImageView back_txdc = findViewById(R.id.back_txdc);
        text_transaction_hash = (TextView) findViewById(R.id.text_transaction_hash);
        user_wallet = Utility.getProfile(ApproveXDC721Activity.this);
        tokenDetail = Utility.getnftinfo(ApproveXDC721Activity.this);
        send_approve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!hasText(edt_receiver_address)) {
                    edt_receiver_address.setError(getResources().getString(R.string.error_empty));
                } else if (!hasText(edt_token_totransfer)) {
                    edt_token_totransfer.setError(getResources().getString(R.string.error_empty));

                } else {
                    if (user_wallet != null && user_wallet.getAccountAddress() != null && user_wallet.getAccountAddress().length() > 0 && user_wallet.getPrivateKey() != null) {
                        try {
                            String hash = null;
                            try {
                                hash = XDC721Client.getInstance().approve(
                                        tokenDetail.getTokenAddress(),
                                        user_wallet.getPrivateKey(),
                                        edt_token_totransfer.getText().toString(),
                                        edt_receiver_address.getText().toString(),
                                        new BigInteger(etGasPrice.getText().toString()),
                                        new BigInteger(etGasLimit.getText().toString()));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            text_transaction_hash.setText(hash);
                            Utility.closeKeyboard(ApproveXDC721Activity.this);
                            SharedPreferenceHelper.setSharedPreferenceString(ApproveXDC721Activity.this, "nfthash", hash);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }


                }
            }


        });

        back_txdc.setOnClickListener(v -> onBackPressed());
    }


    public static boolean hasText(EditText s) {
        return !s.getText().toString().trim().equalsIgnoreCase("");
    }

    @Override
    public void onFocusChange(View view, boolean hasFocus) {
        if (hasFocus) {
            new AlertDialog.Builder(ApproveXDC721Activity.this)
                    .setMessage(getString(R.string.err_gas_price_limit_edit))
                    .setPositiveButton(android.R.string.ok, (dialog, which) -> {
                        dialog.dismiss();
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }
    }
}