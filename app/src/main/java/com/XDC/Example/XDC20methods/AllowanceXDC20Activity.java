package com.XDC.Example.XDC20methods;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.XDC.Example.utils.Utility;
import com.XDC.R;
import com.XDCJava.Model.TokenDetailsResponse;
import com.XDCJava.Model.WalletData;
import com.XDCJava.XDC20Client;

public class AllowanceXDC20Activity extends AppCompatActivity {

    private EditText edt_receiver_address;
    private TextView text_transaction_hash;
    private WalletData user_wallet;
    private TokenDetailsResponse tokenDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_allowance_xdc20);

        edt_receiver_address = (EditText) findViewById(R.id.receiver_address);
        Button send_approve = (Button) findViewById(R.id.send_approve);
        ImageView back_txdc = findViewById(R.id.back_txdc);
        text_transaction_hash = (TextView) findViewById(R.id.text_transaction_hash);
        user_wallet = Utility.getProfile(AllowanceXDC20Activity.this);
        tokenDetail = Utility.gettokeninfo(AllowanceXDC20Activity.this);
        send_approve.setOnClickListener(v -> {
            if (!hasText(edt_receiver_address)) {
                edt_receiver_address.setError(getResources().getString(R.string.error_empty));
            } else {


                if (user_wallet != null && user_wallet.getAccountAddress() != null && user_wallet.getAccountAddress().length() > 0 && user_wallet.getPrivateKey() != null) {
                    String approve_hash = XDC20Client.getInstance().getAllowance(tokenDetail.getToken_address(),user_wallet.getAccountAddress(),edt_receiver_address.getText().toString());
                    text_transaction_hash.setText(approve_hash);
                    Utility.closeKeyboard(AllowanceXDC20Activity.this);

                   // SharedPreferenceHelper.setSharedPreferenceString(AllowanceXDC20Activity.this, "transactionhash", approve_hash);
                }


            }
        });

        back_txdc.setOnClickListener(v -> onBackPressed());
    }


    public static boolean hasText(EditText s) {
        return !s.getText().toString().trim().equalsIgnoreCase("");
    }
}