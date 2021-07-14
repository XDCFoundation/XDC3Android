package xinfin.sdk;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.xinfin.Model.TokenDetailsResponse;

import java.math.BigInteger;

public class Details extends AppCompatActivity {

    TextView xdc_address_value,name_value,symbol_value,decimals_value,total_supply_value,balance_off_value,
            transfer_value,allowance_value,approve_value,transfer_from_value,increase_allowance_value,decrease_allowance_value;
    String hex_to_dec;
    BigInteger dec_bal,dec_supply;
    TokenDetailsResponse tokenResponse = new TokenDetailsResponse();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        hex_to_dec = "1000000000000000000";

        xdc_address_value = findViewById(R.id.xdc_address_value);
        name_value = findViewById(R.id.name_value);
        symbol_value = findViewById(R.id.symbol_value);
        decimals_value = findViewById(R.id.decimals_value);
        total_supply_value = findViewById(R.id.total_supply_value);
        balance_off_value = findViewById(R.id.balance_off_value);
        transfer_value = findViewById(R.id.transfer_value);
        allowance_value = findViewById(R.id.allowance_value);
        approve_value = findViewById(R.id.approve_value);
        transfer_from_value = findViewById(R.id.transfer_from_value);
        increase_allowance_value = findViewById(R.id.increase_allowance_value);
        decrease_allowance_value = findViewById(R.id.decrease_allowance_value);



        if (getIntent().hasExtra("tokendetail")) {
            tokenResponse = (TokenDetailsResponse) getIntent().getSerializableExtra("tokendetail");

            Log.e("cardetail", tokenResponse.getName()+"");
        }



        assignValues();



    }


    public void assignValues(){

        xdc_address_value.setText(tokenResponse.getSpender_address().replace("0x","xdc"));

        if (name_value != null){
            name_value.setText(tokenResponse.getName());
        }
        else
            name_value.setText("-");

        if (symbol_value != null){
            symbol_value.setText(tokenResponse.getSymbol());
        }
        else
            symbol_value.setText("-");

        if (balance_off_value!= null){

                if (tokenResponse.getBalance() != null){
                    BigInteger a
                            = new BigInteger(tokenResponse.getBalance().toString());
                    BigInteger b
                            = new BigInteger(hex_to_dec);

                    // Using divide() method
                    dec_bal = a.divide(b);
                    balance_off_value.setText(dec_bal.toString());
                }



        }
        else
            balance_off_value.setText("-");

        if (decimals_value != null){
            decimals_value.setText(tokenResponse.getDecimal().toString());
        }

        else
            decimals_value.setText("-");

        if (allowance_value !=null){
            allowance_value.setText(tokenResponse.getAllowance().toString());
        }
       else
           allowance_value.setText("-");

       if (total_supply_value != null){
           if (tokenResponse.getTotalSupply() != null){
               BigInteger a
                       = new BigInteger(tokenResponse.getTotalSupply().toString());
               BigInteger b
                       = new BigInteger(hex_to_dec);

               // Using divide() method
               dec_supply = a.divide(b);
               total_supply_value.setText(dec_supply.toString());
           }

       }
       else
           total_supply_value.setText("-");


    }
}