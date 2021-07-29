package com.xinfin;

import com.xinfin.Model.Token721DetailsResponse;
import com.xinfin.Model.TokenDetailsResponse;
import com.xinfin.Model.WalletData;
import com.xinfin.callback.CreateAccountCallback;
import com.xinfin.callback.Token721DetailCallback;
import com.xinfin.callback.TokenDetailCallback;
import com.xinfin.contracts.src.main.java.XRC165;
import com.xinfin.contracts.src.main.java.XRC20;
import com.xinfin.contracts.src.main.java.XRC721;
import com.xinfin.contracts.src.main.java.XRC721Metadata;

import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Bip39Wallet;
import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.ECKeyPair;
import org.web3j.crypto.RawTransaction;
import org.web3j.crypto.TransactionEncoder;
import org.web3j.crypto.WalletFile;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.EthGasPrice;
import org.web3j.protocol.core.methods.response.EthGetTransactionCount;
import org.web3j.protocol.core.methods.response.EthSendTransaction;
import org.web3j.protocol.core.methods.response.Web3ClientVersion;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.ClientTransactionManager;
import org.web3j.tx.gas.DefaultGasProvider;
import org.web3j.utils.Numeric;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;
import java.util.Collections;


public class Xinfin721Client {
    Web3j web3;
    public static Xinfin721Client instance;
    XRC721 javaToken;
    BigInteger allowance, decimal, totalSupply, balance;
    String symbol, name;
    TokenDetailsResponse tokenResponse;
    private WalletFile wallet;

    public static Xinfin721Client getInstance() {
        if (instance == null)
            instance = new Xinfin721Client();

        return instance;
    }


    public Boolean isWeb3jConnected() {
        web3 = Web3j.build(new

                HttpService(AppConstants.BASE_URL));
        try {
            Web3ClientVersion clientVersion = web3.web3ClientVersion().sendAsync().get();
            if (!clientVersion.hasError()) {
                //Connected
                return true;
            } else {
                //Show Error
                return false;
            }
        } catch (
                Exception e) {
            //Show Error
            return false;
        }
    }

    public void getTokenoinfo(String tokenAddress, Token721DetailCallback tokenDetailCallback) {

        web3 = Web3j.build(new

                HttpService(AppConstants.BASE_URL));
        try {
            Web3ClientVersion clientVersion = web3.web3ClientVersion().sendAsync().get();
            if (!clientVersion.hasError()) {
                ClientTransactionManager transactionManager = new ClientTransactionManager(web3,
                        tokenAddress);
                javaToken = null;
                try {
                    XRC721Metadata   javaToken = XRC721Metadata.load(tokenAddress, web3, transactionManager, new DefaultGasProvider());
                    getinfo(javaToken, tokenAddress, tokenDetailCallback);
                } catch (Exception exception) {
                    exception.printStackTrace();
                    tokenDetailCallback.failure(exception.getMessage());
                }

            } else {

                //Show Error
                tokenDetailCallback.failure("Connection has been failed");
            }
        } catch (
                Exception e) {
            tokenDetailCallback.failure(e.getMessage());
//Show Error

        }
    }


    public void getinfo(XRC721Metadata javaToken,String tokenAddress, Token721DetailCallback tokenDetailCallback) {
        try {

            symbol = javaToken.symbol().send();
            name = javaToken.name().send();
            Token721DetailsResponse   tokenResponse = new Token721DetailsResponse();
            tokenResponse.setSymbol(symbol);
            tokenResponse.setName(name);
            tokenResponse.setTokenAddress(tokenAddress);
            tokenDetailCallback.success(tokenResponse);

        } catch (Exception exception) {
            exception.printStackTrace();
            tokenDetailCallback.failure(exception.getMessage());
        }

    }


    public String getBalance(String tokenAddress, String ownerAddress) {

        web3 = Web3j.build(new

                HttpService(AppConstants.BASE_URL));
        try {
            Web3ClientVersion clientVersion = web3.web3ClientVersion().sendAsync().get();
            if (!clientVersion.hasError()) {
                ClientTransactionManager transactionManager = new ClientTransactionManager(web3,
                        tokenAddress);
                try {
                    XRC721   javaToken1 = XRC721.load(tokenAddress, web3, transactionManager, new DefaultGasProvider());
                    BigInteger balance =   javaToken1.balanceOf(ownerAddress).send();
                   // String ownerOf =   javaToken1.ownerOf(BigInteger.valueOf(21)).send();


                    return String.valueOf(balance);

                    // tokenDetailCallback.success(getinfo(javaToken, token_address,tokenDetailCallback));
                    // return getinfo(javaToken, token_address);
                } catch (Exception exception) {
                    exception.printStackTrace();
                    return  exception.getMessage();
                }

            } else {

                //Show Error
                return  "Connection has been failed";
            }
        } catch (
                Exception e) {
            return  e.getMessage();
//Show Error

        }


    }

    public boolean getsupportInterface() {

        web3 = Web3j.build(new
                HttpService(AppConstants.BASE_URL));
        try {
            Web3ClientVersion clientVersion = web3.web3ClientVersion().sendAsync().get();
            if (!clientVersion.hasError()) {
                ClientTransactionManager transactionManager = new ClientTransactionManager(web3,
                        "0x301815025bd43513ec36b6c6159ebaa8dff5e36d");
//                Credentials creds = org.web3j.crypto.Credentials.create(AppConstants.PRIVATE_KEY);
                try {
                    XRC165 javaToken2 = XRC165.load("0x301815025bd43513ec36b6c6159ebaa8dff5e36d", web3, transactionManager, new DefaultGasProvider());
//                    String name = javaToken.name().send();
//                    getinfo(javaToken,"0x161cdb7f674ef7c4c8b09b83fb6342a12f1a12c2" , tokenDetailCallback);
                  /*  String s1 = "0x80ac58cd";
                    //  byte[4] b4 =
                    //  org.web3j.abi.datatypes.generated.Bytes4 nt  = 0x80ac58cd ;

                    byte[] byteValue = s1.getBytes();
                    byte[] byteValueLen32 = new byte[4];
                    System.arraycopy(byteValue, 0, byteValueLen32, 0, byteValue.length);
                    Bytes32 myStringInBytes32 = new Bytes32(byteValueLen32);*/


                    int  i = 0x80ac58cd;


                    byte[] a =    ByteBuffer.allocate(4).order(ByteOrder.nativeOrder()).putInt(i).array();



                    Boolean supportInterface = javaToken2.supportsInterface(a).sendAsync().get();
//                    getinfo(javaToken,"0x161cdb7f674ef7c4c8b09b83fb6342a12f1a12c2" , tokenDetailCallback);
                    // tokenDetailCallback.success(getinfo(javaToken, token_address,tokenDetailCallback));
                    // return getinfo(javaToken, token_address);
                    return supportInterface;
                } catch (Exception exception) {
                    exception.printStackTrace();
                    return false;
                }
            } else {
                //Show Error
                return false;
            }
        } catch (
                Exception e) {
            return false;
//Show Error
        }

    }

    @SuppressWarnings("NewApi")
    public static String asciiToHex(String asciiValue) {
        char[] chars = asciiValue.toCharArray();
        StringBuffer hex = new StringBuffer();
        for (int i = 0; i < chars.length; i++) {
            hex.append(Integer.toHexString((int) chars[i]));
        }

        return hex.toString() + "".join("", Collections.nCopies(32 - (hex.length() / 2), "00"));
    }

    public String approve( ) throws Exception {
        web3 = Web3j.build(new
                HttpService(AppConstants.BASE_URL));
        //spender privatekey
        Credentials credentials = Credentials.create("0x07d2fb9a1f3a912000bbf9215ee0815a969b1d49e7e4c5ec94600ae2dfcfa4ce");
        // Get nonce, the number of transactions
        BigInteger nonce;
        EthGetTransactionCount ethGetTransactionCount = web3.ethGetTransactionCount(credentials.getAddress(), DefaultBlockParameterName.LATEST).sendAsync().get();
        if (ethGetTransactionCount == null) {
            return null;
        }
        nonce = ethGetTransactionCount.getTransactionCount();
        //gasPrice and gasLimit can be set manually
        BigInteger gasPrice;
        EthGasPrice ethGasPrice = web3.ethGasPrice().sendAsync().get();
        if (ethGasPrice == null) {
            return null;
        }
        gasPrice = ethGasPrice.getGasPrice();
        BigInteger gasLimit = BigInteger.valueOf(60000L);
        //receiver key - token id
        final Function function = new Function(
                "approve",
                Arrays.<Type>asList(new Address("0xedb472070566e072f3bdaa50cfa076e772135f33"),
                        new Uint256(BigInteger.valueOf(21))),
                Collections.<TypeReference<?>>emptyList());

        //Create RawTransaction transaction object
        String encodedFunction = FunctionEncoder.encode(function);
        //token address
        RawTransaction rawTransaction = RawTransaction.createTransaction(nonce, gasPrice, gasLimit,
                "0x301815025bd43513ec36b6c6159ebaa8dff5e36d", encodedFunction);
        //Signature Transaction
        byte[] signMessage = TransactionEncoder.signMessage(rawTransaction, credentials);
        String hexValue = Numeric.toHexString(signMessage);
        //Send the transaction
        EthSendTransaction ethSendTransaction = web3.ethSendRawTransaction(hexValue).sendAsync().get();
        String hash = ethSendTransaction.getTransactionHash();
        if (hash != null) {
            return hash;
        }
        return null;
    }


}
