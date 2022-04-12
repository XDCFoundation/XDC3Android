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

import com.XDC.Example.utils.SharedPreferenceHelper;
import com.XDC.Example.utils.Utility;
import com.XDC.R;
import com.XDCJava.Model.TokenDetailsResponse;
import com.XDCJava.Model.WalletData;
import com.XDCJava.XDC20Client;

import java.math.BigInteger;

public class IncreaseAllowanceXDC20Activity extends AppCompatActivity implements View.OnFocusChangeListener {

    EditText edt_receiver_address, edt_token_totransfer;
    Button send_approve;
    TextView text_transaction_hash;
    WalletData user_wallet;
    ImageView back_txdc;
    TokenDetailsResponse tokenDetail;

    private AppCompatEditText etGasPrice, etGasLimit;
    private BigInteger gasPrice, gasLimit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_increseallowance_xdc20);

        gasPrice = XDC20Client.getInstance().getGasPrice();
        gasLimit = XDC20Client.getInstance().getGasLimit();

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

        send_approve = (Button) findViewById(R.id.send_approve);
        back_txdc = findViewById(R.id.back_txdc);
        text_transaction_hash = (TextView) findViewById(R.id.text_transaction_hash);
        user_wallet = Utility.getProfile(IncreaseAllowanceXDC20Activity.this);
        tokenDetail = Utility.gettokeninfo(IncreaseAllowanceXDC20Activity.this);
        send_approve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!hasText(edt_receiver_address)) {
                    edt_receiver_address.setError(getResources().getString(R.string.error_empty));
                } else if (!hasText(edt_token_totransfer)) {
                    edt_token_totransfer.setError(getResources().getString(R.string.error_empty));

                } else {


                    if (user_wallet != null && user_wallet.getAccountAddress() != null && user_wallet.getAccountAddress().length() > 0 && user_wallet.getPrivateKey() != null) {
                        String    approve_hash = null;
                        try {
                            approve_hash = XDC20Client.getInstance().increaseAllownce(
                                    user_wallet.getAccountAddress(),
                                    edt_receiver_address.getText().toString(),
                                    user_wallet.getPrivateKey(),
                                    edt_token_totransfer.getText().toString() ,
                                    tokenDetail.getToken_address(),
                                    new BigInteger(etGasPrice.getText().toString()),
                                    new BigInteger(etGasLimit.getText().toString()));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        text_transaction_hash.setText(approve_hash);
                        Utility.closeKeyboard(IncreaseAllowanceXDC20Activity.this);
                        SharedPreferenceHelper.setSharedPreferenceString(IncreaseAllowanceXDC20Activity.this, "transactionhash", approve_hash);
                    }


                }
            }


        });

        back_txdc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }


    public static boolean hasText(EditText s) {
        if (s.getText().toString().trim().equalsIgnoreCase(""))
            return false;
        else
            return true;
    }

    @Override
    public void onFocusChange(View view, boolean hasFocus) {
        if (hasFocus) {
            new AlertDialog.Builder(IncreaseAllowanceXDC20Activity.this)
                    .setMessage(getString(R.string.err_gas_price_limit_edit))
                    .setPositiveButton(android.R.string.ok, (dialog, which) -> {
                        dialog.dismiss();
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }
    }
}