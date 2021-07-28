package xinfin.sdk;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.xinfin.Model.TokenDetailsResponse;
import com.xinfin.XinfinClient;
import com.xinfin.XinfinClient_721;
import com.xinfin.callback.TokenDetailCallback;

import java.io.Serializable;
import java.util.ArrayList;


public class XDC721MainActivity extends AppCompatActivity {

    private Button submit_button;
    String token_address;
    TextView enterXdcAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        enterXdcAddress = findViewById(R.id.enter_xdc_address);
        submit_button = findViewById(R.id.submit);

        submit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

              //  Utility.showProcess(MainActivity.this);
                 XinfinClient_721.getInstance().getsupportInterface();
              //  Web3jClass.getInstance().TransferTokenEvent();




            }
        });




    }




}