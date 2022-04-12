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
import com.XDCJava.XDC721Client;

import java.io.IOException;
import java.math.BigInteger;
import java.util.concurrent.ExecutionException;

public class TransfertokenXDC20Activity extends AppCompatActivity implements View.OnFocusChangeListener {

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
        setContentView(R.layout.activity_transfertoken_xdc20);

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
        user_wallet = Utility.getProfile(TransfertokenXDC20Activity.this);
        tokenDetail = Utility.gettokeninfo(TransfertokenXDC20Activity.this);
        send_approve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!hasText(edt_receiver_address)) {
                    edt_receiver_address.setError(getResources().getString(R.string.error_empty));
                } else if (!hasText(edt_token_totransfer)) {
                    edt_token_totransfer.setError(getResources().getString(R.string.error_empty));

                } else {


                    if (user_wallet != null && user_wallet.getAccountAddress() != null
                            && user_wallet.getAccountAddress().length() > 0
                            && user_wallet.getPrivateKey() != null) {
                        try {
                            String approve_hash = XDC20Client.getInstance().transferXRC20Token(
                                    tokenDetail.getToken_address(),
                                    user_wallet.getPrivateKey(),
                                    edt_receiver_address.getText().toString(),
                                    edt_token_totransfer.getText().toString(),
                                    new BigInteger(etGasPrice.getText().toString()),
                                    new BigInteger(etGasLimit.getText().toString()));
                            text_transaction_hash.setText(approve_hash);
                            Utility.closeKeyboard(TransfertokenXDC20Activity.this);
                            SharedPreferenceHelper.setSharedPreferenceString(TransfertokenXDC20Activity.this, "transactionhash", approve_hash);
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
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
            new AlertDialog.Builder(TransfertokenXDC20Activity.this)
                    .setMessage(getString(R.string.err_gas_price_limit_edit))
                    .setPositiveButton(android.R.string.ok, (dialog, which) -> {
                        dialog.dismiss();
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }
    }
}