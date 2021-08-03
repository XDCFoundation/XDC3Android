package xinfin.sdk;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.XDCJava.Model.Token721DetailsResponse;
import com.XDCJava.XDC721Client;
import com.XDCJava.callback.Token721DetailCallback;

import java.io.Serializable;


public class XDC721MainActivity extends AppCompatActivity {

    private Button submit_button;
    TextView enterXdcAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main721);

        enterXdcAddress = findViewById(R.id.enter_xdc_address);
        submit_button = findViewById(R.id.submit);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        submit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                if(enterXdcAddress.getText().toString()!=null && enterXdcAddress.getText().toString().length()>0)
                {
                    XDC721Client.getInstance().getTokenoinfo(enterXdcAddress.getText().toString(), new Token721DetailCallback() {
                        @Override
                        public void success(Token721DetailsResponse tokenDetailsResponse) {
                            Intent intent = new Intent(XDC721MainActivity.this, Details721.class);
                            intent.putExtra("tokendetail",(Serializable) tokenDetailsResponse);
                            startActivity(intent);
                        }

                        @Override
                        public void failure(Throwable t) {

                        }

                        @Override
                        public void failure(String message) {

                        }
                    });
                }
                else
                {
                    Toast.makeText(XDC721MainActivity.this, "Please Enter Token Address", Toast.LENGTH_LONG).show();
                }





            }
        });




    }




}