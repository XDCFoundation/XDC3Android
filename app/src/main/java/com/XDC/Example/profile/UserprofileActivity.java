package com.XDC.Example.profile;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.XDC.Example.XDCmethods.TransferXDCActivity;
import com.XDC.Example.utils.SharedPreferenceHelper;
import com.XDC.Example.utils.Utility;
import com.XDC.Example.wallet.CreateAccount;
import com.XDC.R;
import com.XDCJava.Model.WalletData;
import com.XDCJava.XDC20Client;
import com.google.gson.Gson;


public class UserprofileActivity extends AppCompatActivity {

    TextView text_accountaddress,text_XDC,transfer_XDC;
    WalletData user_wallet;
    ImageView img_copy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userprofile);

        text_accountaddress = findViewById(R.id.text_accountaddress);
        img_copy = findViewById(R.id.img_copy);
        text_XDC = findViewById(R.id.text_XDC);
        transfer_XDC = findViewById(R.id.transfer_XDC);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        user_wallet = Utility.getProfile(UserprofileActivity.this);
        text_accountaddress.setText(user_wallet.getAccountAddress());
        Log.e("walletAddress",user_wallet.getAccountAddress());
        getXDCBalance();


        img_copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("wallet Address", user_wallet.getAccountAddress());
                clipboard.setPrimaryClip(clip);
            }
        });

        transfer_XDC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                Intent intent = new Intent(UserprofileActivity.this, TransferXDCActivity.class);
                startActivity(intent);
            }
        });


    }

    private void getXDCBalance()
    {
        text_XDC.setText(XDC20Client.getInstance().getXdcBalance(user_wallet.getAccountAddress()));
    }

    @Override
    protected void onResume() {
        super.onResume();
        getXDCBalance();
    }
}