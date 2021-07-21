package xinfin.sdk;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.xinfin.Model.TokenDetailsResponse;
import com.xinfin.Model.TokenTransferResponse;
import com.xinfin.Web.Web3jClass;
import com.xinfin.Web.Web3jTokenTransfer;
import com.xinfin.callback.TokenDetailCallback;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;

import xinfin.sdk.utils.Utility;


public class MainActivity extends AppCompatActivity {

    private Button submit_button,btn_createaccount;
    String token_address, xdcAddress;
    Button transfer_amount;
    AutoCompleteTextView tokenAutoTV;
    TextView enterXdcAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        token_address = "0xd3d1ea96362d2660d38c749c196370b5619a3620";
        enterXdcAddress = findViewById(R.id.enter_xdc_address);
        btn_createaccount = (Button)findViewById(R.id.btn_createaccount);
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


               /*  try {
                    Web3jTokenTransfer.getInstance().decreaseAllownce();
                } catch (Exception e) {
                    e.printStackTrace();
                }*/


              /*   try {
                    Web3jClass.getInstance().approve();
                } catch (Exception e) {
                    e.printStackTrace();
                }
*/
               /* try {
                    Web3jClass.getInstance().tranferfrom();
                } catch (Exception e) {
                    e.printStackTrace();
                }*/

             Utility.showProcess(MainActivity.this);
                 Web3jClass.getInstance().getTokenoinfo(token_address, new TokenDetailCallback() {
                    @Override
                    public void success(TokenDetailsResponse tokenDetailsResponse)
                    {
                        Utility.dismissProcess();
                        Intent intent = new Intent(MainActivity.this, Details.class);
                        intent.putExtra("tokendetail",(Serializable) tokenDetailsResponse);
                        startActivity(intent);
                    }

                    @Override
                    public void failure(Throwable t)
                    {
                        Utility.dismissProcess();
                        Toast.makeText(MainActivity.this,t.getMessage(),Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void failure(String message)
                    {
                        Utility.dismissProcess();
                        Toast.makeText(MainActivity.this,message,Toast.LENGTH_LONG).show();
                    }
                });

              //  Web3jClass.getInstance().TransferTokenEvent();




            }
        });


        btn_createaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CreateAccount.class);
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

        tokenAutoTV.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id)
            {
                /*Toast.makeText(MainActivity.this,
                        adapter.getItem(position).toString(),
                        Toast.LENGTH_SHORT).show();*/


                token_address = adapter.getItem(position).toString();

            }
        });


    }

    private ArrayList<String> getCustomerList() {
        ArrayList<String> address = new ArrayList<>();

        address.add("0xc4bd1127a5227659b5ef3070092b6740046f3c3a");
        address.add("0xce20035eceecd1f94ac4a828de5e3f9b7d2c7898");
        address.add("0x847aefb3d207e69749e970f8574743a4f388b6f2");
        return address;
    }


}