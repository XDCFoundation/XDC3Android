package xinfin.sdk;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.xinfin.Model.TokenResponse;
import com.xinfin.Web.Web3jClass;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    private Button submit_button;
    String token_address, xdcAddress;
    ImageView transfer_amount;
    AutoCompleteTextView tokenAutoTV;
    TextView enterXdcAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        token_address = "0x847aefb3d207e69749e970f8574743a4f388b6f2";
        enterXdcAddress = findViewById(R.id.enter_xdc_address);
        enterXdcAddress.setText(token_address.replace("0x", "xdc"));

        transfer_amount = findViewById(R.id.transfer_amount);

        transfer_amount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, TransferAmount.class);
                startActivity(intent);
            }
        });


        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);





        initUI();



        submit_button = findViewById(R.id.submit);

        submit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                TokenResponse tokenResponse =   Web3jClass.getInstance().getTokenoinfo(token_address);

               // Web3jClass.getInstance().TransferTokenEvent();
                Intent intent = new Intent(MainActivity.this, Details.class);
                intent.putExtra("tokendetail",(Serializable) tokenResponse);
                startActivity(intent);



            }
        });


    }


    private void initUI() {
        //UI reference of textView
        tokenAutoTV = findViewById(R.id.customerTextView);

        // create list of customer
        ArrayList<String> customerList = getCustomerList();

        //Create adapter
        ArrayAdapter<String> adapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_spinner_item, customerList);

        //Set adapter
        tokenAutoTV.setAdapter(adapter);
    }

    private ArrayList<String> getCustomerList() {
        ArrayList<String> address = new ArrayList<>();
        address.add("xdc847aefb3d207e69749e970f8574743a4f388b6f2");
        address.add("xdce7c09f7c38156eaff9864d5d2dc83723b804f927");
        address.add("xdcce20035eceecd1f94ac4a828de5e3f9b7d2c7898");
        return address;
    }


}