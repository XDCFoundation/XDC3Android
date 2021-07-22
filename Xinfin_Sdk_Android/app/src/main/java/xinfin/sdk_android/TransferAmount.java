package xinfin.sdk_android;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;



import java.math.BigInteger;

import xinfin.sdk.Model.TokenTransferResponse;
import xinfin.sdk.Web.Web3jClass;
import xinfin.sdk.callback.TokenTransferCallback;
import xinfin.sdk_android.utils.Utility;


public class TransferAmount extends AppCompatActivity {

    EditText edt_private_key, edt_gaslimit, edt_gasprice, edt_sender_address, edt_receiver_address, edt_token_totransfer;
    Button button_send;
    TextView text_transaction_hash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer_amount);

        edt_private_key = findViewById(R.id.private_key);
        edt_sender_address = findViewById(R.id.sender_address);
        edt_receiver_address = findViewById(R.id.receiver_address);
        edt_token_totransfer = findViewById(R.id.value);
        edt_gasprice = findViewById(R.id.edt_gasprice);
        edt_gaslimit = findViewById(R.id.edt_gaslimit);
        button_send = findViewById(R.id.send);
        text_transaction_hash = findViewById(R.id.text_transaction_hash);

        button_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!hasText(edt_private_key))
                {
                    edt_private_key.setError(getResources().getString(R.string.error_empty));
                }

                else if (!hasText(edt_sender_address))
                {
                    edt_sender_address.setError(getResources().getString(R.string.error_empty));
                }

                else if (!hasText(edt_receiver_address))
                {
                    edt_receiver_address.setError(getResources().getString(R.string.error_empty));
                }

                else if (!hasText(edt_token_totransfer))
                {
                    edt_token_totransfer.setError(getResources().getString(R.string.error_empty));
                }

                else if (!hasText(edt_gasprice))
                {
                    edt_gasprice.setError(getResources().getString(R.string.error_empty));
                }

                else if (!hasText(edt_gaslimit))
                {
                    edt_gaslimit.setError(getResources().getString(R.string.error_empty));
                }

                else {


                   Utility.showProcess(TransferAmount.this);

                    Web3jClass.getInstance().TransferXdc(edt_private_key.getText().toString(), edt_sender_address.getText().toString(), edt_receiver_address.getText().toString(), new BigInteger(String.valueOf(edt_token_totransfer.getText())), Long.parseLong(edt_gasprice.getText().toString()), Long.parseLong(edt_gaslimit.getText().toString()), new TokenTransferCallback() {


                        @Override
                        public void success(TokenTransferResponse tokenTransferResponse)
                        {
                            Utility.dismissProcess();


                        Toast.makeText(TransferAmount.this,"Token has been transfered and Transation has been approved",Toast.LENGTH_LONG).show();

                            text_transaction_hash.setText(tokenTransferResponse.getTransactionHash());

                        }

                        @Override
                        public void success(String message)
                        {
                            Utility.dismissProcess();
                            Toast.makeText(TransferAmount.this,"Token has been transfered and Transation has been approved",Toast.LENGTH_LONG).show();

                            text_transaction_hash.setText(message);
                        }

                        @Override
                        public void failure(Throwable t)
                        {
                            Utility.dismissProcess();
                            Toast.makeText(TransferAmount.this,t.getMessage(),Toast.LENGTH_LONG).show();


                        }

                        @Override
                        public void failure(String message)
                        {
                            Utility.dismissProcess();
                            Toast.makeText(TransferAmount.this,message,Toast.LENGTH_LONG).show();


                        }

                    });

                }
            }


        });
    }


    public static boolean hasText(EditText s) {
        if (s.getText().toString().trim().equalsIgnoreCase(""))
            return false;
        else
            return true;
    }
}