package xinfin.sdk;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import org.web3j.crypto.Credentials;
import org.web3j.crypto.RawTransaction;
import org.web3j.crypto.TransactionEncoder;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.EthGetTransactionCount;
import org.web3j.protocol.core.methods.response.EthSendTransaction;
import org.web3j.utils.Numeric;

import java.math.BigInteger;
import java.util.concurrent.ExecutionException;

import xinfin.sdk.constants.AppConstants;

public class TransferAmount extends AppCompatActivity {

    Web3j web3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer_amount);


//        web3.ethGetTransactionCount(AppConstants.FROM_ADDRESS, DefaultBlockParameterName.LATEST);
//        Toast.makeText(this, web3.ethGetTransactionCount(AppConstants.FROM_ADDRESS, DefaultBlockParameterName.LATEST).toString(), Toast.LENGTH_SHORT).show();
//
//
//        EthGetTransactionCount ethGetTransactionCount = null;
//        try {
//            ethGetTransactionCount = web3.ethGetTransactionCount(
//                    AppConstants.FROM_ADDRESS, DefaultBlockParameterName.LATEST).sendAsync().get();
//        } catch (ExecutionException e) {
//            e.printStackTrace();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        BigInteger nonce = ethGetTransactionCount.getTransactionCount();
//
//
//        RawTransaction rawTransaction  = RawTransaction.createEtherTransaction(
//                nonce, BigInteger.valueOf(4000004), BigInteger.valueOf(50005), AppConstants.TO_ADDRESS, BigInteger.valueOf(1000000000000000000L));
//
//        byte[] signedMessage = TransactionEncoder.signMessage(rawTransaction, Credentials.create(AppConstants.PRIVATE_KEY_TRANSACTION));
//        String hexValue = Numeric.toHexString(signedMessage);
//
//
//        EthSendTransaction ethSendTransaction = null;
//        try {
//            ethSendTransaction = web3.ethSendRawTransaction(hexValue).sendAsync().get();
//        } catch (ExecutionException e) {
//            e.printStackTrace();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        String transactionHash = ethSendTransaction.getTransactionHash();
//        try {
//            web3.ethGetTransactionByHash(transactionHash).sendAsync().get();
//        } catch (ExecutionException e) {
//            e.printStackTrace();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
    }
}