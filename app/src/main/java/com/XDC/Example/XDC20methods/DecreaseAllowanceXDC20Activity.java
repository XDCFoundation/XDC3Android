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
import java.util.Objects;

public class DecreaseAllowanceXDC20Activity extends AppCompatActivity implements View.OnFocusChangeListener {

    private EditText edt_receiver_address, edt_token_totransfer;
    private TextView text_transaction_hash;
    private WalletData user_wallet;
    private TokenDetailsResponse tokenDetail;

    private AppCompatEditText etGasPrice, etGasLimit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_decreseallowance_xdc20);

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

        Button send_approve = (Button) findViewById(R.id.send_approve);
        ImageView back_txdc = findViewById(R.id.back_txdc);
        text_transaction_hash = (TextView) findViewById(R.id.text_transaction_hash);
        user_wallet = Utility.getProfile(DecreaseAllowanceXDC20Activity.this);
        tokenDetail = Utility.gettokeninfo(DecreaseAllowanceXDC20Activity.this);
        send_approve.setOnClickListener(v -> {
            if (!hasText(edt_receiver_address)) {
                edt_receiver_address.setError(getResources().getString(R.string.error_empty));
            } else if (!hasText(edt_token_totransfer)) {
                edt_token_totransfer.setError(getResources().getString(R.string.error_empty));

            } else {


                if (user_wallet != null && user_wallet.getAccountAddress() != null && user_wallet.getAccountAddress().length() > 0 && user_wallet.getPrivateKey() != null) {

                    String approve_hash = null;
                    try {
                        approve_hash = XDC20Client.getInstance().decreaseAllownce(
                                user_wallet.getAccountAddress(),
                                edt_receiver_address.getText().toString(),
                                user_wallet.getPrivateKey(),
                                edt_token_totransfer.getText().toString() ,
                                tokenDetail.getToken_address(),
                                new BigInteger(Objects.requireNonNull(etGasPrice.getText()).toString()),
                                new BigInteger(Objects.requireNonNull(etGasLimit.getText()).toString()));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    text_transaction_hash.setText(approve_hash);
                    Utility.closeKeyboard(DecreaseAllowanceXDC20Activity.this);
                    SharedPreferenceHelper.setSharedPreferenceString(DecreaseAllowanceXDC20Activity.this, "transactionhash", approve_hash);
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
            new AlertDialog.Builder(DecreaseAllowanceXDC20Activity.this)
                    .setMessage(getString(R.string.err_gas_price_limit_edit))
                    .setPositiveButton(android.R.string.ok, (dialog, which) -> dialog.dismiss())
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }
    }
}