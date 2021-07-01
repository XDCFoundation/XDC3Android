package xinfin.sdk;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.Web3ClientVersion;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.gas.DefaultGasProvider;

import java.math.BigInteger;
import java.util.ArrayList;

import xinfin.sdk.constants.AppConstants;
import xinfin.sdk.contracts.src.main.java.org.web3j.contracts.eip20.generated.ERC20;


public class MainActivity extends AppCompatActivity {

    private Button submit_button;
    Web3j web3;
     String token_address,xdcAddress;
     String hex_to_dec;
    ERC20 javaToken;
    BigInteger allowance,decimal,totalSupply,balance;
    String symbol,name;
    AutoCompleteTextView tokenAutoTV;
    TextView enterXdcAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        token_address = "0x847aefb3d207e69749e970f8574743a4f388b6f2";
        hex_to_dec = "1000000000000000000";
//        xdcAddress = token_address.replace("0x","xdc");

        enterXdcAddress = findViewById(R.id.enter_xdc_address);
        enterXdcAddress.setText(token_address.replace("0x","xdc"));

         web3 = Web3j.build(new HttpService("https://rpc.apothem.network/"));
        try {
            Web3ClientVersion clientVersion = web3.web3ClientVersion().sendAsync().get();
            if(!clientVersion.hasError()){
//Connected
                Toast.makeText(getApplicationContext(), "Connected",Toast.LENGTH_SHORT).show();
            }
            else {

                Toast.makeText(getApplicationContext(), "Error",Toast.LENGTH_SHORT).show();
//Show Error
            }
        }
        catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Exception",Toast.LENGTH_SHORT).show();
//Show Error
        }

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);


//        createToken();



        initUI();
//        getApprove();
//        getTransferFrom();



//        try {
//            BigInteger allowance = javaToken.balanceOf(token_number).send();
//        }
//        catch (Exception exception) {
//            exception.printStackTrace();
//        }


        submit_button = findViewById(R.id.submit);

        submit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (tokenAutoTV.getText() != null && tokenAutoTV.getText().length()>0){
                    token_address = tokenAutoTV.getText().toString();
                    enterXdcAddress.setText("");
                   token_address =  token_address.replace("xdc","0x");
                   getTokenDetails();
                }
                else{
//                    token_address = enterXdcAddress.getText().toString();
                    getTokenDetails();
                }



                getBalance();
                getSymbol();
                getTotalSupply();
                getName();
                getDecimal();
                getAllowance();

                Intent intent = new Intent(MainActivity.this,Details.class);
                if (balance != null){
                    intent.putExtra("BALANCE", balance.toString());
                }

                if (symbol != null){
                    intent.putExtra("SYMBOL", symbol);
                }

                if (totalSupply != null){
                    intent.putExtra("TOTAL_SUPPLY", totalSupply.toString());
                }

                if (name != null){
                    intent.putExtra("NAME", name);
                }
                token_address.replace("0x","xdc");
                intent.putExtra("TOKEN_ADDRESS",token_address);
                if (decimal != null){
                    intent.putExtra("DECIMAL", decimal.toString());
                }

               if (allowance != null){
                   intent.putExtra("ALLOWANCE", allowance.toString());
               }


                startActivity(intent);

            }
        });


    }

    public void getTokenDetails(){
        Credentials creds = org.web3j.crypto.Credentials.create(AppConstants.PRIVATE_KEY);

        javaToken = null;
        try {
            javaToken = ERC20.load(token_address, web3, creds, new DefaultGasProvider());
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

// to fetch allowance
    public void getAllowance(){
        try {
            allowance = javaToken.allowance(token_address,"0x2e550836caaa79884f36e78626363f59ca50e96e").send();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

//    public void getApprove(){
//
//        try {
//            TransactionReceipt approve = javaToken.approve("0x03c0d9bc556be68870b96976e81d32ebb49d335d",BigInteger.valueOf(1000)).send();
//        } catch (Exception exception) {
//            exception.printStackTrace();
//        }
//
//    }

//    public void getTransferFrom(){
//
//        try {
//            TransactionReceipt transfer = javaToken.transferFrom("0x03c0d9bc556be68870b96976e81d32ebb49d335d","0x114ac1863dcd99cdbe7b33eb69a87e12fb95f47a",BigInteger.valueOf(1000)).send();
//        } catch (Exception exception) {
//            exception.printStackTrace();
//        }
//
//    }

// to fetch the decimals of the token
    public void getDecimal(){

        try {
            decimal = javaToken.decimals().send();
        } catch (Exception exception) {
            exception.printStackTrace();
        }

    }

// to fetch the name of the token
    public void getName(){

        try {
            name = javaToken.name().send();
        } catch (Exception exception) {
            exception.printStackTrace();
        }

    }


// to fetch he total supply of the token
    public void getTotalSupply(){
        totalSupply = null;
        try {
            totalSupply = javaToken.totalSupply().send();

        } catch (Exception exception) {
            exception.printStackTrace();
        }

    }

// to fetch symbol of the token
    public void getSymbol(){

        try {
            symbol = javaToken.symbol().send();
        } catch (Exception exception) {
            exception.printStackTrace();
        }

    }

// to fetch the balance of the token
    public void getBalance()
    {

        try {
            balance = javaToken.balanceOf(token_address).send();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
//        try {
//            web3.ethGetBalance(token_number, DefaultBlockParameterName.LATEST).sendAsync().get();
//        } catch (ExecutionException e) {
//            e.printStackTrace();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
    }


    private void initUI()
    {
        //UI reference of textView
        tokenAutoTV = findViewById(R.id.customerTextView);

        // create list of customer
        ArrayList<String> customerList = getCustomerList();

        //Create adapter
        ArrayAdapter<String> adapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_spinner_item, customerList);

        //Set adapter
        tokenAutoTV.setAdapter(adapter);
    }

    private ArrayList<String> getCustomerList()
    {
        ArrayList<String> address = new ArrayList<>();
        address.add("xdc847aefb3d207e69749e970f8574743a4f388b6f2");
        address.add("xdce7c09f7c38156eaff9864d5d2dc83723b804f927");
        address.add("xdcce20035eceecd1f94ac4a828de5e3f9b7d2c7898");
        return address;
    }


}