package xinfin.sdk;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.xinfin.Model.Token721DetailsResponse;
import com.xinfin.Xinfin721Client;
import com.xinfin.XinfinClient;

import java.math.BigInteger;

public class Details721 extends AppCompatActivity implements View.OnClickListener {

    TextView xdc_address_value, name_value, symbol_value, decimals_value, total_supply_value, balance_off_value,
            transfer_value, allowance_value, approve_value, transfer_from_value, increase_allowance_value, decrease_allowance_value;
    String hex_to_dec;
    BigInteger dec_bal, dec_supply;
    Token721DetailsResponse tokenResponse = new Token721DetailsResponse();

    EditText edt_privatekey, edt_allownce_owner, edt_allownce_spender, edt_approve_spender,
            edt_value_approve, edt_transfer_to, edt_value_transfer, text_contract_address, edt_increase_owner, edt_increase_spender,
            edt_increase_allownce_value, edt_decrease_owner, edt_decrease_spender, edt_decrease_allownce, edt_tfrom_spender, edt_tfrom_to, edt_tfrom_value, edt_tfrom_spender_privatekey,
            edt_balance_spender;
    Button check_address, submit_allownce, submit_approve, submit_transfer, submit_increase_Allownce, submit_decrease_Allownce, submit_tfrom, submit_balanceof;
    TextView approve_trasactonhash, transfer_trasactonhash, text_increase_allow_trasactonhash, text_decrease_allow_trasactonhash, text_transferfrom_trasactonhash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details721);
        hex_to_dec = "1000000000000000000";

        xdc_address_value = findViewById(R.id.xdc_address_value);
        name_value = findViewById(R.id.name_value);
        symbol_value = findViewById(R.id.symbol_value);
        decimals_value = findViewById(R.id.decimals_value);
        total_supply_value = findViewById(R.id.total_supply_value);
        balance_off_value = findViewById(R.id.balance_off_value);
        transfer_value = findViewById(R.id.edt_value_transfer);
        allowance_value = findViewById(R.id.allowance_value);

        edt_privatekey = findViewById(R.id.edt_privatekey);
        check_address = findViewById(R.id.submit);
        text_contract_address = findViewById(R.id.text_contract_address);


        submit_balanceof = findViewById(R.id.submit_balanceof);
        submit_balanceof.setOnClickListener(this::onClick);
        edt_balance_spender = findViewById(R.id.edt_balance_spender);

        submit_allownce = findViewById(R.id.submit_allownce);
        submit_allownce.setOnClickListener(this::onClick);
        edt_allownce_owner = findViewById(R.id.edt_allownce_owner);
        edt_allownce_spender = findViewById(R.id.edt_allownce_spender);

        approve_trasactonhash = findViewById(R.id.approve_trasactonhash);
        edt_approve_spender = findViewById(R.id.edt_approve_spender);
        edt_value_approve = findViewById(R.id.edt_value_approve);
        submit_approve = findViewById(R.id.submit_approve);
        submit_approve.setOnClickListener(this::onClick);


        transfer_trasactonhash = findViewById(R.id.transfer_trasactonhash);
        edt_transfer_to = findViewById(R.id.edt_transfer_to);
        edt_value_transfer = findViewById(R.id.edt_value_transfer);
        submit_transfer = findViewById(R.id.submit_transfer);
        submit_transfer.setOnClickListener(this::onClick);


        text_increase_allow_trasactonhash = findViewById(R.id.text_increase_allow_trasactonhash);
        edt_increase_owner = findViewById(R.id.edt_increase_owner);
        edt_increase_spender = findViewById(R.id.edt_increase_spender);
        edt_increase_allownce_value = findViewById(R.id.edt_increase_allownce);
        submit_increase_Allownce = findViewById(R.id.submit_increase_Allownce);
        submit_increase_Allownce.setOnClickListener(this::onClick);


        text_decrease_allow_trasactonhash = findViewById(R.id.text_decrease_allow_trasactonhash);
        edt_decrease_owner = findViewById(R.id.edt_decrease_owner);
        edt_decrease_spender = findViewById(R.id.edt_decrease_spender);
        edt_decrease_allownce = findViewById(R.id.edt_decrease_allownce);
        submit_decrease_Allownce = findViewById(R.id.submit_decrease_Allownce);
        submit_decrease_Allownce.setOnClickListener(this::onClick);


        text_transferfrom_trasactonhash = findViewById(R.id.text_transferfrom_trasactonhash);
        edt_tfrom_spender = findViewById(R.id.edt_tfrom_spender);
        edt_tfrom_to = findViewById(R.id.edt_tfrom_to);
        edt_tfrom_value = findViewById(R.id.edt_tfrom_value);
        submit_tfrom = findViewById(R.id.submit_tfrom);
        submit_tfrom.setOnClickListener(this::onClick);
        edt_tfrom_spender_privatekey = findViewById(R.id.edt_tfrom_spender_privatekey);

        if (getIntent().hasExtra("tokendetail")) {
            tokenResponse = (Token721DetailsResponse) getIntent().getSerializableExtra("tokendetail");

            Log.e("cardetail", tokenResponse.getName() + "");
        }


        assignValues();


        check_address.setOnClickListener(this);
    }


    public void assignValues() {


        if (name_value != null) {
            name_value.setText(tokenResponse.getName());
        } else
            name_value.setText("-");

        if (symbol_value != null) {
            symbol_value.setText(tokenResponse.getSymbol());
        } else
            symbol_value.setText("-");

        xdc_address_value.setText(tokenResponse.getTokenAddress());

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.submit:
                verifyPrivatekey();

                break;


            case R.id.submit_balanceof:
                getbalance();
                break;


        }

    }


    private void getbalance()
    {
        if (edt_balance_spender.getText().toString() != null && edt_balance_spender.getText().toString().length() > 0) {


            String allownce = Xinfin721Client.getInstance().getBalance(tokenResponse.getTokenAddress(), edt_balance_spender.getText().toString());
            balance_off_value.setText(allownce);


        } else {
            Toast.makeText(Details721.this, "Please Enter  Address to check balance", Toast.LENGTH_LONG).show();
        }
    }

    private void verifyPrivatekey() {
        if (edt_privatekey.getText().toString() != null && edt_privatekey.getText().toString().length() > 0) {
            String contract_address = XinfinClient.getInstance().getContractAddress(edt_privatekey.getText().toString());
            text_contract_address.setText(contract_address);
        } else {
            Toast.makeText(Details721.this, "Please Enter Private key", Toast.LENGTH_LONG).show();
        }
    }
}