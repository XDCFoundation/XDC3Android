package com.XDC.Example.XDC20methods;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;

import com.XDC.Example.utils.Utility;
import com.XDC.R;
import com.XDCJava.Model.WalletData;
import com.XDCJava.XDC20Client;
import com.XDCJava.XDC721Client;
import com.XDCJava.callback.EventCallback;
import com.google.android.material.snackbar.Snackbar;

import java.math.BigInteger;
import java.util.Objects;

public class TransferXDCActivity extends AppCompatActivity implements View.OnFocusChangeListener {

    private EditText edt_receiver_address, edt_token_totransfer;
    private Button button_send;
    private TextView text_transaction_hash;
    private WalletData user_wallet;
    private AppCompatEditText etGasPrice, etGasLimit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer_xdc);

        BigInteger gasPrice = XDC20Client.getInstance().getGasPrice();
        BigInteger gasLimit = XDC20Client.getInstance().getGasLimit();

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
        button_send = (Button) findViewById(R.id.send);
        ImageView back_txdc = findViewById(R.id.back_txdc);
        text_transaction_hash = (TextView) findViewById(R.id.text_transaction_hash);
        user_wallet = Utility.getProfile(TransferXDCActivity.this);
        button_send.setOnClickListener(v -> {
            if (!hasText(edt_receiver_address)) {
                edt_receiver_address.setError(getResources().getString(R.string.error_empty));
            } else if (!hasText(edt_token_totransfer)) {
                edt_token_totransfer.setError(getResources().getString(R.string.error_empty));
            } else {
                if (user_wallet != null && user_wallet.getAccountAddress() != null
                        && user_wallet.getAccountAddress().length() > 0
                        && user_wallet.getPrivateKey() != null) {
                    XDC20Client.getInstance().TransferXdc(user_wallet.getPrivateKey(),
                            user_wallet.getAccountAddress(),
                            edt_receiver_address.getText().toString(),
                            edt_token_totransfer.getText().toString(), new EventCallback() {
                                @Override
                                public void success(String hash) {
                                    text_transaction_hash.setText(hash);
                                    Utility.closeKeyboard(TransferXDCActivity.this);
                                }

                                @Override
                                public void failure(Throwable t) {

                                }

                                @Override
                                public void failure(String message) {
                                    Snackbar snackbar = Snackbar
                                            .make(button_send, message, Snackbar.LENGTH_LONG);
                                    snackbar.show();
                                }
                            }, new BigInteger(Objects.requireNonNull(etGasPrice.getText()).toString()),
                            new BigInteger(Objects.requireNonNull(etGasLimit.getText()).toString()));
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
            new AlertDialog.Builder(TransferXDCActivity.this)
                    .setMessage(getString(R.string.err_gas_price_limit_edit))
                    .setPositiveButton(android.R.string.ok, (dialog, which) -> dialog.dismiss())
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }
    }
}