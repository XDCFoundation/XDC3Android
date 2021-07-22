package xinfin.sdk;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.RawTransaction;
import org.web3j.crypto.TransactionEncoder;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.EthGasPrice;
import org.web3j.protocol.core.methods.response.EthGetTransactionCount;
import org.web3j.protocol.core.methods.response.EthSendTransaction;
import org.web3j.protocol.http.HttpService;
import org.web3j.utils.Numeric;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.concurrent.ExecutionException;

import xinfin.sdk.constants.AppConstants;

public class CreateAccount extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        try {
            transferERC20Token();
        } catch (ExecutionException | IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            approveERC20Token();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String transferERC20Token() throws ExecutionException, InterruptedException, IOException {
        Web3j web3j = Web3j.build(new HttpService(AppConstants.BASE_URL));
        // Load the required documents for the transfer, with the private key
        Credentials credentials = Credentials.create("0xe258e6b92739236070ca1baf6ee4ae63203237fec29249a96d122f24f18f7ded");
        // Get nonce, the number of transactions
        BigInteger nonce;
        EthGetTransactionCount ethGetTransactionCount = web3j.ethGetTransactionCount("0x32f158af29c171392a1ff35a7387583ff4959053", DefaultBlockParameterName.LATEST).sendAsync().get();
        if (ethGetTransactionCount == null) {
            return null;
        }
        nonce = ethGetTransactionCount.getTransactionCount();
        //gasPrice and gasLimit can be set manually
        BigInteger gasPrice;
        EthGasPrice ethGasPrice = web3j.ethGasPrice().sendAsync().get();
        if (ethGasPrice == null) {
            return null;
        }
        gasPrice = ethGasPrice.getGasPrice();
        //BigInteger.valueOf(4300000L) If the transaction fails, it is probably a problem with the setting of the fee.
        BigInteger gasLimit = BigInteger.valueOf(60000L);
        //ERC20 token contract method
//        value = value.multiply(VALUE);
        Function function = new Function(
                "transfer",
                Arrays.asList(new Address("0x73585ae0c1aa818db5f360ed734ffad68d9b2ef8"), new Uint256(BigInteger.valueOf(1000))),
                Collections.singletonList(new TypeReference<Type>() {
                }));
        // Create RawTransaction transaction object
        String encodedFunction = FunctionEncoder.encode(function);
        RawTransaction rawTransaction = RawTransaction.createTransaction(nonce, gasPrice, gasLimit,
                "0xd3d1ea96362d2660d38c749c196370b5619a3620", encodedFunction);

        //Signature Transaction
        byte[] signMessage = TransactionEncoder.signMessage(rawTransaction, credentials);
        String hexValue = Numeric.toHexString(signMessage);
        //Send the transaction
        EthSendTransaction ethSendTransaction = web3j.ethSendRawTransaction(hexValue).sendAsync().get();
        String hash = ethSendTransaction.getTransactionHash();
        if (hash != null) {
            return hash;
        }
        return null;
    }

    public static String approveERC20Token() throws ExecutionException, InterruptedException, IOException {
        Web3j web3j = Web3j.build(new HttpService(AppConstants.BASE_URL));
        // Load the required documents for the transfer, with the private key
        Credentials credentials = Credentials.create("0xe258e6b92739236070ca1baf6ee4ae63203237fec29249a96d122f24f18f7ded");
        // Get nonce, the number of transactions
        BigInteger nonce;
        EthGetTransactionCount ethGetTransactionCount = web3j.ethGetTransactionCount("0x32f158af29c171392a1ff35a7387583ff4959053", DefaultBlockParameterName.LATEST).sendAsync().get();
        if (ethGetTransactionCount == null) {
            return null;
        }
        nonce = ethGetTransactionCount.getTransactionCount();
        //gasPrice and gasLimit can be set manually
        BigInteger gasPrice;
        EthGasPrice ethGasPrice = web3j.ethGasPrice().sendAsync().get();
        if (ethGasPrice == null) {
            return null;
        }
        gasPrice = ethGasPrice.getGasPrice();
        //BigInteger.valueOf(4300000L) If the transaction fails, it is probably a problem with the setting of the fee.
        BigInteger gasLimit = BigInteger.valueOf(60000L);
        //ERC20 token contract method
//        value = value.multiply(VALUE);
        Function function = new Function(
                "approve",
                Arrays.asList(new Address("0x73585ae0c1aa818db5f360ed734ffad68d9b2ef8"), new Uint256(BigInteger.valueOf(1000))),
                Collections.singletonList(new TypeReference<Type>() {
                }));
        // Create RawTransaction transaction object
        String encodedFunction = FunctionEncoder.encode(function);
        RawTransaction rawTransaction = RawTransaction.createTransaction(nonce, gasPrice, gasLimit,
                "0xd3d1ea96362d2660d38c749c196370b5619a3620", encodedFunction);

        //Signature Transaction
        byte[] signMessage = TransactionEncoder.signMessage(rawTransaction, credentials);
        String hexValue = Numeric.toHexString(signMessage);
        //Send the transaction
        EthSendTransaction ethSendTransaction = web3j.ethSendRawTransaction(hexValue).sendAsync().get();
        String hash = ethSendTransaction.getTransactionHash();
        if (hash != null) {
            return hash;
        }
        return null;
    }
}