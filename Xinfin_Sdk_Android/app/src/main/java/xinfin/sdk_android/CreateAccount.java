package xinfin.sdk_android;

import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import java.io.File;

import xinfin.sdk.Model.WalletData;

import xinfin.sdk.XDC20Client;
import xinfin.sdk.callback.CreateAccountCallback;

public class CreateAccount extends AppCompatActivity {
    EditText edt_password;
    Button btn_createacc;
    TextView txt_info;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        edt_password = (EditText) findViewById(R.id.edt_password);
        btn_createacc = (Button) findViewById(R.id.btn_createacc);
        txt_info = (TextView) findViewById(R.id.txt_info);

        btn_createacc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edt_password.getText().toString().length() > 0) {
                    try {

                        File path = getExternalFilesDir(Environment.DIRECTORY_PICTURES.toString() +
                                File.separator + "web3j");
                        path.mkdir();

                        XDC20Client.getInstance().generateWallet(path, edt_password.getText().toString(), new CreateAccountCallback() {
                            @Override
                            public void success(WalletData walletData) {

                                txt_info.setText("Account address: " + walletData.getAccountAddress() + "\n" + "privateKey: " + walletData.getPrivateKey());
                            }

                            @Override
                            public void failure(Throwable t) {
                                txt_info.setText(t.getMessage());
                            }

                            @Override
                            public void failure(String message) {
                                txt_info.setText(message);

                            }
                        });
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(CreateAccount.this, "Please Enter Password", Toast.LENGTH_LONG).show();
                }

            }
        });
    }
}