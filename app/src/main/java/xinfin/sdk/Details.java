package xinfin.sdk;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import java.math.BigInteger;

public class Details extends AppCompatActivity {

    TextView xdc_address_value,name_value,symbol_value,decimals_value,total_supply_value,balance_off_value,
            transfer_value,allowance_value,approve_value,transfer_from_value,increase_allowance_value,decrease_allowance_value;
//    BigInteger allowance,decimal,totalSupply;
    String symbol,name,balance,allowance,decimal,totalSupply,xdc_address;
    String hex_to_dec,bal,supply;
    BigInteger dec_bal,dec_supply;


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

        getValues();
        assignValues();



    }

    public void getValues(){
        xdc_address = getIntent().getStringExtra("TOKEN_ADDRESS");
        name = getIntent().getStringExtra("NAME");
        symbol = getIntent().getStringExtra("SYMBOL");
        balance = getIntent().getStringExtra("BALANCE");
        decimal = getIntent().getStringExtra("DECIMAL");
        allowance = getIntent().getStringExtra("ALLOWANCE");
        totalSupply = getIntent().getStringExtra("TOTAL_SUPPLY");
    }

    public void assignValues(){

        xdc_address_value.setText(xdc_address.replace("0x","xdc"));

        if (name_value != null){
            name_value.setText(name);
        }
        else
            name_value.setText("-");

        if (symbol_value != null){
            symbol_value.setText(symbol);
        }
        else
            symbol_value.setText("-");

        if (balance_off_value!= null){

                if (balance != null){
                    bal = balance;
                    BigInteger a
                            = new BigInteger(bal);
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
            decimals_value.setText(decimal);
        }

        else
            decimals_value.setText("-");

        if (allowance_value !=null){
            allowance_value.setText(allowance);
        }
       else
           allowance_value.setText("-");

       if (total_supply_value != null){
           if (totalSupply != null){
               supply = totalSupply;
               BigInteger a
                       = new BigInteger(supply);
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