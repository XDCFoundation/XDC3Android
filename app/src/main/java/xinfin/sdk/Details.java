package xinfin.sdk;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.xinfin.Model.TokenDetailsResponse;
import com.xinfin.Web.Web3jClass;

import java.io.IOException;
import java.math.BigInteger;
import java.util.concurrent.ExecutionException;

public class Details extends AppCompatActivity implements View.OnClickListener {

    TextView xdc_address_value, name_value, symbol_value, decimals_value, total_supply_value, balance_off_value,
            transfer_value, allowance_value, approve_value, transfer_from_value, increase_allowance_value, decrease_allowance_value;
    String hex_to_dec;
    BigInteger dec_bal, dec_supply;
    TokenDetailsResponse tokenResponse = new TokenDetailsResponse();

    EditText edt_privatekey, edt_allownce_owner, edt_allownce_spender, edt_approve_spender,
            edt_value_approve, edt_transfer_to, edt_value_transfer,text_contract_address,edt_increase_owner,edt_increase_spender,
            edt_increase_allownce_value;
    Button check_address, submit_allownce, submit_approve, submit_transfer,submit_increase_Allownce;
    TextView  approve_trasactonhash, transfer_trasactonhash,text_increase_allow_trasactonhash;

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
        transfer_value = findViewById(R.id.edt_value_transfer);
        allowance_value = findViewById(R.id.allowance_value);

        edt_privatekey = findViewById(R.id.edt_privatekey);
        check_address = findViewById(R.id.submit);
        text_contract_address = findViewById(R.id.text_contract_address);


        submit_allownce = findViewById(R.id.submit_allownce);
        submit_allownce.setOnClickListener(this::onClick);
        edt_allownce_owner = findViewById(R.id.edt_allownce_owner);
        edt_allownce_spender = findViewById(R.id.edt_allownce_owner);

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


        text_increase_allow_trasactonhash  = findViewById(R.id.text_increase_allow_trasactonhash);
        edt_increase_owner = findViewById(R.id.edt_increase_owner);
        edt_increase_spender = findViewById(R.id.edt_increase_spender);
        edt_increase_allownce_value = findViewById(R.id.edt_increase_allownce);
        submit_increase_Allownce = findViewById(R.id.submit_increase_Allownce);
        submit_increase_Allownce.setOnClickListener(this::onClick);


        if (getIntent().hasExtra("tokendetail")) {
            tokenResponse = (TokenDetailsResponse) getIntent().getSerializableExtra("tokendetail");

            Log.e("cardetail", tokenResponse.getName() + "");
        }


        assignValues();


        check_address.setOnClickListener(this);
    }


    public void assignValues() {

        xdc_address_value.setText(tokenResponse.getSpender_address().replace("0x", "xdc"));

        if (name_value != null) {
            name_value.setText(tokenResponse.getName());
        } else
            name_value.setText("-");

        if (symbol_value != null) {
            symbol_value.setText(tokenResponse.getSymbol());
        } else
            symbol_value.setText("-");

        if (balance_off_value != null) {

            if (tokenResponse.getBalance() != null) {
                BigInteger a
                        = new BigInteger(tokenResponse.getBalance().toString());
                BigInteger b
                        = new BigInteger(hex_to_dec);

                // Using divide() method
                dec_bal = a.divide(b);
                balance_off_value.setText(dec_bal.toString());
            }


        } else
            balance_off_value.setText("-");

        if (decimals_value != null) {
            decimals_value.setText(tokenResponse.getDecimal().toString());
        } else
            decimals_value.setText("-");

        if (allowance_value != null) {
            allowance_value.setText(tokenResponse.getAllowance().toString());
        } else
            allowance_value.setText("-");

        if (total_supply_value != null) {
            if (tokenResponse.getTotalSupply() != null) {
                BigInteger a
                        = new BigInteger(tokenResponse.getTotalSupply().toString());
                BigInteger b
                        = new BigInteger(hex_to_dec);

                // Using divide() method
                dec_supply = a.divide(b);
                total_supply_value.setText(dec_supply.toString());
            }

        } else
            total_supply_value.setText("-");


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.submit:
                verifyPrivatekey();

                break;

            case R.id.submit_allownce:
                getallownce();
                break;

            case R.id.submit_approve:
                getapprove();
                break;

            case R.id.submit_transfer:
                transfertoken();
                break;

            case R.id.submit_increase_Allownce:
                increaseAllownce();
                break;

        }

    }

    private void increaseAllownce()
    {

    }

    private void transfertoken()
    {

        if (edt_transfer_to.getText().toString() != null && edt_transfer_to.getText().toString().length() > 0) {

            if (text_contract_address.getText().toString() != null && text_contract_address.getText().toString().length() > 0) {
                if (edt_privatekey.getText().toString() != null && edt_privatekey.getText().toString().length() > 0) {
                    if (edt_value_transfer.getText().toString() != null && edt_value_transfer.getText().toString().length() > 0) {

                        String approved_hash = null;
                        try {
                            approved_hash = Web3jClass.getInstance().approveERC20Token(tokenResponse.getSpender_address(), edt_privatekey.getText().toString(), text_contract_address.getText().toString(), edt_transfer_to.getText().toString(), edt_value_transfer.getText().toString());
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        transfer_trasactonhash.setText(approved_hash);

                    } else {
                        Toast.makeText(Details.this, "Please Enter Value to Transfer", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(Details.this, "Please Enter token owner Address", Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(Details.this, "Please Enter Private key", Toast.LENGTH_LONG).show();
            }


        } else {
            Toast.makeText(Details.this, "Please Enter address to transfer Token", Toast.LENGTH_LONG).show();
        }

    }

    private void getapprove() {
        if (edt_approve_spender.getText().toString() != null && edt_approve_spender.getText().toString().length() > 0) {

            if (text_contract_address.getText().toString() != null && text_contract_address.getText().toString().length() > 0) {
                if (edt_privatekey.getText().toString() != null && edt_privatekey.getText().toString().length() > 0) {
                    if (edt_value_approve.getText().toString() != null && edt_value_approve.getText().toString().length() > 0) {

                        String approved_hash = null;
                        try {
                            approved_hash = Web3jClass.getInstance().approveERC20Token(tokenResponse.getSpender_address(), edt_privatekey.getText().toString(), text_contract_address.getText().toString(), edt_allownce_spender.getText().toString(), edt_value_approve.getText().toString());
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        approve_trasactonhash.setText(approved_hash);

                    } else {
                        Toast.makeText(Details.this, "Please Enter Value to Approve", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(Details.this, "Please Enter token owner Address", Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(Details.this, "Please Enter Private key", Toast.LENGTH_LONG).show();
            }


        } else {
            Toast.makeText(Details.this, "Please Enter Spendar Address", Toast.LENGTH_LONG).show();
        }
    }

    private void getallownce() {
        if (edt_allownce_owner.getText().toString() != null && edt_allownce_owner.getText().toString().length() > 0) {

            if (edt_allownce_spender.getText().toString() != null && edt_allownce_spender.getText().toString().length() > 0) {

                String allownce = Web3jClass.getInstance().getAllowance(tokenResponse.getSpender_address(), edt_allownce_owner.getText().toString(), edt_allownce_spender.getText().toString());
                allowance_value.setText(allownce);

            } else {
                Toast.makeText(Details.this, "Please Enter Spender Address", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(Details.this, "Please Enter Owner Address", Toast.LENGTH_LONG).show();
        }
    }

    private void verifyPrivatekey() {
        if (edt_privatekey.getText().toString() != null && edt_privatekey.getText().toString().length() > 0) {
            String contract_address = Web3jClass.getInstance().getContractAddress(edt_privatekey.getText().toString());
            text_contract_address.setText(contract_address);
        } else {
            Toast.makeText(Details.this, "Please Enter Private key", Toast.LENGTH_LONG).show();
        }
    }
}