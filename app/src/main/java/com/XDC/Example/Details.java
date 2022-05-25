package com.XDC.Example;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.XDC.R;
import com.XDCJava.Model.TokenDetailsResponse;
import com.XDCJava.XDC20Client;

import java.io.IOException;
import java.math.BigInteger;
import java.util.concurrent.ExecutionException;

public class Details extends AppCompatActivity implements View.OnClickListener, View.OnFocusChangeListener {

    private TextView xdc_address_value, name_value, symbol_value, decimals_value,
            total_supply_value, balance_off_value, allowance_value;
    private TokenDetailsResponse tokenResponse = new TokenDetailsResponse();

    private EditText edt_privatekey, edt_allownce_owner, edt_allownce_spender,
            edt_approve_spender, edt_value_approve, etApproveGasPrice, etApproveGasLimit,
            edt_transfer_to, edt_value_transfer, etTransferGasPrice, etTransferGasLimit,
            text_contract_address, edt_increase_owner, edt_increase_spender,
            edt_increase_allownce_value, etIncreaseAllowanceGasPrice,
            etIncreaseAllowanceGasLimit, edt_decrease_owner, edt_decrease_spender,
            edt_decrease_allownce, edt_tfrom_spender, edt_tfrom_to, edt_tfrom_value,
            edt_tfrom_spender_privatekey, etTransferFromGasPrice, etTransferFromGasLimit,
            edt_balance_spender;
    private TextView approve_trasactonhash, transfer_trasactonhash,
            text_increase_allow_trasactonhash, text_decrease_allow_trasactonhash,
            text_transferfrom_trasactonhash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        BigInteger gasPrice = XDC20Client.getInstance().getGasPrice();
        BigInteger gasLimit = XDC20Client.getInstance().getGasLimit();

        xdc_address_value = findViewById(R.id.xdc_address_value);
        name_value = findViewById(R.id.name_value);
        symbol_value = findViewById(R.id.symbol_value);
        decimals_value = findViewById(R.id.decimals_value);
        total_supply_value = findViewById(R.id.total_supply_value);
        balance_off_value = findViewById(R.id.balance_off_value);
        etTransferGasPrice = findViewById(R.id.etTransferGasPrice);
        etTransferGasLimit = findViewById(R.id.etTransferGasLimit);
        allowance_value = findViewById(R.id.allowance_value);

        edt_privatekey = findViewById(R.id.edt_privatekey);
        Button check_address = findViewById(R.id.submit);
        text_contract_address = findViewById(R.id.text_contract_address);


        Button submit_balanceof = findViewById(R.id.submit_balanceof);
        submit_balanceof.setOnClickListener(this);
        edt_balance_spender = findViewById(R.id.edt_balance_spender);

        Button submit_allownce = findViewById(R.id.submit_allownce);
        submit_allownce.setOnClickListener(this);
        edt_allownce_owner = findViewById(R.id.edt_allownce_owner);
        edt_allownce_spender = findViewById(R.id.edt_allownce_spender);

        approve_trasactonhash = findViewById(R.id.approve_trasactonhash);
        edt_approve_spender = findViewById(R.id.edt_approve_spender);
        edt_value_approve = findViewById(R.id.edt_value_approve);
        etApproveGasPrice = findViewById(R.id.etApproveGasPrice);
        etApproveGasLimit = findViewById(R.id.etApproveGasLimit);
        Button submit_approve = findViewById(R.id.submit_approve);
        submit_approve.setOnClickListener(this);


        transfer_trasactonhash = findViewById(R.id.transfer_trasactonhash);
        edt_transfer_to = findViewById(R.id.edt_transfer_to);
        edt_value_transfer = findViewById(R.id.edt_value_transfer);
        Button submit_transfer = findViewById(R.id.submit_transfer);
        submit_transfer.setOnClickListener(this);


        text_increase_allow_trasactonhash = findViewById(R.id.text_increase_allow_trasactonhash);
        edt_increase_owner = findViewById(R.id.edt_increase_owner);
        edt_increase_spender = findViewById(R.id.edt_increase_spender);
        edt_increase_allownce_value = findViewById(R.id.edt_increase_allownce);
        etIncreaseAllowanceGasPrice = findViewById(R.id.etIncreaseAllowanceGasPrice);
        etIncreaseAllowanceGasLimit = findViewById(R.id.etIncreaseAllowanceGasLimit);

        Button submit_increase_Allownce = findViewById(R.id.submit_increase_Allownce);
        submit_increase_Allownce.setOnClickListener(this);


        text_decrease_allow_trasactonhash = findViewById(R.id.text_decrease_allow_trasactonhash);
        edt_decrease_owner = findViewById(R.id.edt_decrease_owner);
        edt_decrease_spender = findViewById(R.id.edt_decrease_spender);
        edt_decrease_allownce = findViewById(R.id.edt_decrease_allownce);
        Button submit_decrease_Allownce = findViewById(R.id.submit_decrease_Allownce);
        submit_decrease_Allownce.setOnClickListener(this);


        text_transferfrom_trasactonhash = findViewById(R.id.text_transferfrom_trasactonhash);
        edt_tfrom_spender = findViewById(R.id.edt_tfrom_spender);
        edt_tfrom_to = findViewById(R.id.edt_tfrom_to);
        edt_tfrom_value = findViewById(R.id.edt_tfrom_value);
        Button submit_tfrom = findViewById(R.id.submit_tfrom);
        submit_tfrom.setOnClickListener(this);
        edt_tfrom_spender_privatekey = findViewById(R.id.edt_tfrom_spender_privatekey);
        etTransferFromGasPrice = findViewById(R.id.etTransferFromGasPrice);
        etTransferFromGasLimit = findViewById(R.id.etTransferFromGasLimit);

        if (gasPrice != null) {
            etTransferFromGasPrice.setText(gasPrice + "");
            etIncreaseAllowanceGasPrice.setText(gasPrice + "");
            etTransferGasPrice.setText(gasPrice + "");
            etApproveGasPrice.setText(gasPrice + "");
        }
        if (gasLimit != null) {
            etTransferFromGasLimit.setText(gasLimit + "");
            etIncreaseAllowanceGasLimit.setText(gasLimit + "");
            etTransferGasLimit.setText(gasLimit + "");
            etApproveGasLimit.setText(gasLimit + "");
        }

        etTransferFromGasPrice.setOnFocusChangeListener(this);
        etIncreaseAllowanceGasPrice.setOnFocusChangeListener(this);
        etTransferGasPrice.setOnFocusChangeListener(this);
        etApproveGasPrice.setOnFocusChangeListener(this);
        etTransferFromGasLimit.setOnFocusChangeListener(this);
        etIncreaseAllowanceGasLimit.setOnFocusChangeListener(this);
        etTransferGasLimit.setOnFocusChangeListener(this);
        etApproveGasLimit.setOnFocusChangeListener(this);

        if (getIntent().hasExtra("tokendetail")) {
            tokenResponse = (TokenDetailsResponse) getIntent().getSerializableExtra("tokendetail");
        }


        assignValues();


        check_address.setOnClickListener(this);
    }


    public void assignValues() {


        xdc_address_value.setText(tokenResponse.getToken_address());

        if (name_value != null) {
            name_value.setText(tokenResponse.getName());
        } else
            name_value.setText("-");

        if (symbol_value != null) {
            symbol_value.setText(tokenResponse.getSymbol());
        } else
            symbol_value.setText("-");


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

                total_supply_value.setText(tokenResponse.getTotalSupply().toString());
            }

        } else
            total_supply_value.setText("-");


    }

    @SuppressLint("NonConstantResourceId")
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
            case R.id.submit_decrease_Allownce:
                decreaseAllownce();
                break;

            case R.id.submit_tfrom:
                transferFrom();
                break;
            case R.id.submit_balanceof:
                getbalance();
                break;
        }

    }


    private void transferFrom() {
        if (edt_tfrom_spender.getText().toString().length() > 0) {
            if (edt_tfrom_to.getText().toString().length() > 0) {
                if (edt_tfrom_spender_privatekey.getText().toString().length() > 0) {
                    if (edt_tfrom_value.getText().toString().length() > 0) {
                        String approved_hash = null;
                        try {
                            approved_hash = XDC20Client.getInstance().transferfrom(
                                    edt_tfrom_spender.getText().toString(),
                                    edt_tfrom_to.getText().toString(),
                                    edt_tfrom_spender_privatekey.getText().toString(),
                                    edt_tfrom_value.getText().toString(),
                                    tokenResponse.getToken_address(),
                                    new BigInteger(etTransferFromGasPrice.getText().toString()),
                                    new BigInteger(etTransferFromGasLimit.getText().toString()));
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        text_transferfrom_trasactonhash.setText(approved_hash);
                    } else {
                        Toast.makeText(Details.this, "Please Enter Value to transfer from", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(Details.this, "Please Enter Spendar's Private key", Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(Details.this, "Please Enter To address for transfer Token", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(Details.this, "Please Enter token Spender Address", Toast.LENGTH_LONG).show();
        }
    }


    private void increaseAllownce() {
        if (edt_increase_owner.getText().toString().length() > 0) {
            if (edt_increase_spender.getText().toString().length() > 0) {
                if (edt_privatekey.getText().toString().length() > 0) {
                    if (edt_increase_allownce_value.getText().toString().length() > 0) {

                        String approved_hash = null;
                        try {
                            approved_hash = XDC20Client.getInstance()
                                    .increaseAllownce(edt_increase_owner.getText().toString(),
                                            edt_increase_spender.getText().toString(),
                                            edt_privatekey.getText().toString(),
                                            edt_increase_allownce_value.getText().toString(),
                                            tokenResponse.getToken_address(),
                                            new BigInteger(etIncreaseAllowanceGasPrice.getText().toString()),
                                            new BigInteger(etIncreaseAllowanceGasLimit.getText().toString()));
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        text_increase_allow_trasactonhash.setText(approved_hash);

                    } else {
                        Toast.makeText(Details.this, "Please Enter Value to increase allownce", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(Details.this, "Please Enter Private key", Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(Details.this, "Please Enter address to increase allownce", Toast.LENGTH_LONG).show();

            }


        } else {

            Toast.makeText(Details.this, "Please Enter token owner Address", Toast.LENGTH_LONG).show();
        }
    }

    private void decreaseAllownce() {
        if (edt_decrease_owner.getText().toString().length() > 0) {
            if (edt_decrease_spender.getText().toString().length() > 0) {
                if (edt_privatekey.getText().toString().length() > 0) {
                    if (edt_decrease_allownce.getText().toString().length() > 0) {
                        String approved_hash = null;
                        try {
                            approved_hash = XDC20Client.getInstance().decreaseAllownce(
                                    edt_decrease_owner.getText().toString(),
                                    edt_decrease_spender.getText().toString(),
                                    edt_privatekey.getText().toString(),
                                    edt_decrease_allownce.getText().toString(),
                                    tokenResponse.getToken_address(),
                                    new BigInteger(etIncreaseAllowanceGasPrice.getText().toString()),
                                    new BigInteger(etIncreaseAllowanceGasLimit.getText().toString()));
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        text_decrease_allow_trasactonhash.setText(approved_hash);

                    } else {
                        Toast.makeText(Details.this, "Please Enter Value to increase allownce", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(Details.this, "Please Enter Private key", Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(Details.this, "Please Enter address to decrease allownce", Toast.LENGTH_LONG).show();

            }


        } else {

            Toast.makeText(Details.this, "Please Enter token owner Address", Toast.LENGTH_LONG).show();
        }
    }

    private void transfertoken() {

        if (edt_transfer_to.getText().toString().length() > 0) {
            if (edt_privatekey.getText().toString().length() > 0) {
                if (edt_value_transfer.getText().toString().length() > 0) {
                    String approved_hash = null;
                    try {
                        approved_hash = XDC20Client.getInstance().transferXRC20Token(
                                tokenResponse.getToken_address(),
                                edt_privatekey.getText().toString(),
                                edt_transfer_to.getText().toString(),
                                edt_value_transfer.getText().toString(),
                                new BigInteger(etTransferGasPrice.getText().toString()),
                                new BigInteger(etTransferGasLimit.getText().toString()));
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
                Toast.makeText(Details.this, "Please Enter Private key", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(Details.this, "Please Enter address to transfer Token", Toast.LENGTH_LONG).show();
        }

    }

    private void getapprove() {
        if (edt_approve_spender.getText().toString().length() > 0) {
            if (edt_privatekey.getText().toString().length() > 0) {
                if (edt_value_approve.getText().toString().length() > 0) {

                    String approved_hash = null;
                    try {
                        approved_hash = XDC20Client.getInstance().approveXRC20Token(
                                tokenResponse.getToken_address(),
                                edt_privatekey.getText().toString(),
                                edt_allownce_spender.getText().toString(),
                                edt_value_approve.getText().toString(),
                                new BigInteger(etApproveGasPrice.getText().toString()),
                                new BigInteger(etApproveGasLimit.getText().toString()));
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
                Toast.makeText(Details.this, "Please Enter Private key", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(Details.this, "Please Enter Spendar Address", Toast.LENGTH_LONG).show();
        }
    }

    private void getallownce() {
        if (edt_allownce_owner.getText().toString().length() > 0) {
            if (edt_allownce_spender.getText().toString().length() > 0) {
                String allownce = XDC20Client.getInstance().getAllowance(tokenResponse.getToken_address(), edt_allownce_owner.getText().toString(), edt_allownce_spender.getText().toString());
                allowance_value.setText(allownce);
            } else {
                Toast.makeText(Details.this, "Please Enter Spender Address", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(Details.this, "Please Enter Owner Address", Toast.LENGTH_LONG).show();
        }
    }

    private void getbalance() {
        if (edt_balance_spender.getText().toString().length() > 0) {
            String allownce = XDC20Client.getInstance().getBalance(tokenResponse.getToken_address(), edt_balance_spender.getText().toString());
            balance_off_value.setText(allownce);
        } else {
            Toast.makeText(Details.this, "Please Enter  Address to check balance", Toast.LENGTH_LONG).show();
        }
    }

    private void verifyPrivatekey() {
        if (edt_privatekey.getText().toString().length() > 0) {
            String contract_address = XDC20Client.getInstance().getContractAddress(edt_privatekey.getText().toString());
            text_contract_address.setText(contract_address);
        } else {
            Toast.makeText(Details.this, "Please Enter Private key", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onFocusChange(View view, boolean hasFocus) {
        if (hasFocus) {
            new AlertDialog.Builder(Details.this)
                    .setMessage(getString(R.string.err_gas_price_limit_edit))
                    .setPositiveButton(android.R.string.ok, (dialog, which) -> dialog.dismiss())
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }
    }
}