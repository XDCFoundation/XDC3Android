package com.xinfin;

import com.xinfin.Model.TokenDetailsResponse;
import com.xinfin.Model.WalletData;
import com.xinfin.callback.CreateAccountCallback;
import com.xinfin.contracts.src.main.java.XRC165;
import com.xinfin.contracts.src.main.java.XRC20;

import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
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
import java.util.Arrays;


public class XinfinClient_721 {
    Web3j web3;
    public static XinfinClient_721 instance;
    XRC20 javaToken;
    BigInteger allowance, decimal, totalSupply, balance;
    String symbol, name;
    TokenDetailsResponse tokenResponse;
    private WalletFile wallet;

    public static XinfinClient_721 getInstance() {
        if (instance == null)
            instance = new XinfinClient_721();

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

    public void generateWallet(File walletDirectory, String Password, CreateAccountCallback createAccountCallback) {

        try {


            Bip39Wallet walletName = WalletUtils.generateBip39Wallet(Password, walletDirectory);
            System.out.println("wallet location: " + walletDirectory + "/" + walletName);
            Credentials credentials = WalletUtils.loadBip39Credentials(Password, walletName.getMnemonic());
            String accountAddress = credentials.getAddress();
            System.out.println("Account address: " + accountAddress);

            ECKeyPair ecKeyPair = credentials.getEcKeyPair();
            String privateKey = ecKeyPair.getPrivateKey().toString(16);
            String publickeyKey = ecKeyPair.getPrivateKey().toString(16);
            System.out.println("privateKey: " + ecKeyPair.getPrivateKey());
            System.out.println("sPrivatekeyInHex: " + privateKey);

            String seedPhrase = walletName.getMnemonic();


            WalletData walletData = new WalletData();
            accountAddress = accountAddress.replace("0x", "xdc");
            walletData.setAccountAddress(accountAddress);
            walletData.setPrivateKey(privateKey);
            walletData.setPublickeyKey(publickeyKey);
            walletData.setSeedPhrase(seedPhrase);

            createAccountCallback.success(walletData);

           /* Credentials restoreCredentials = WalletUtils.loadBip39Credentials("1234567890",
                    seedPhrase);
            ECKeyPair restoredPrivateKey = restoreCredentials.getEcKeyPair();
            String restoredAccountAddress = restoreCredentials.getAddress();*/




          /*  setupBouncyCastle();


            try {
                wallet = createWallet();
            } catch (Exception e) {
                System.out.println("BIG RIP");
            }
*/


        } catch (IOException | CipherException e) {

            e.printStackTrace();
            createAccountCallback.failure(e.getMessage());

        }
    }


    public String getContractAddress(String Privatekey) {

        if (isWeb3jConnected()) {
            try {
                Credentials creds = Credentials.create(Privatekey);
                if (creds.getAddress() != null && creds.getAddress().length() > 0) {
                    return creds.getAddress();
                } else {
                    return "Please Enter Valid private key";
                }
            } catch (Exception e) {
                return e.getMessage();

            }

        } else {
            return "Please check your Connection";
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
                    String s1 = "0x80ac58cd";
                    byte[] bytes = s1.getBytes("UTF-8");
                    //byte[] b = new BigInteger(s1, 16).toByteArray();


                   /* EthGetTransactionCount ethGetTransactionCount = web3.ethGetTransactionCount("0x301815025bd43513ec36b6c6159ebaa8dff5e36d", DefaultBlockParameterName.LATEST).sendAsync().get();
                    if (ethGetTransactionCount == null) {
                        return false;
                    }
                    BigInteger   nonce = ethGetTransactionCount.getTransactionCount();
                    //gasPrice and gasLimit can be set manually
                    BigInteger gasPrice;
                    EthGasPrice ethGasPrice = web3.ethGasPrice().sendAsync().get();
                    if (ethGasPrice == null) {
                        return false;
                    }
                    gasPrice = ethGasPrice.getGasPrice();
                    //BigInteger.valueOf(4300000L) If the transaction fails, it is probably a problem with the setting of the fee.
                    BigInteger gasLimit = BigInteger.valueOf(60000L);


                    Function function = new Function("supportsInterface",
                            Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes4(bytes)),
                            Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
                    String encodedFunction = FunctionEncoder.encode(function);
                    //token address
                    RawTransaction rawTransaction = RawTransaction.createTransaction(nonce, gasPrice, gasLimit,
                            token_address, encodedFunction);

                    //Signature Transaction
                    byte[] signMessage = TransactionEncoder.signMessage(rawTransaction, credentials);
                    String hexValue = Numeric.toHexString(signMessage);
                    //Send the transaction
                    EthSendTransaction ethSendTransaction = web3.ethSendRawTransaction(hexValue).sendAsync().get();
                    String hash = ethSendTransaction.getTransactionHash();*/
                   /* if (hash != null) {
                        return hash;
                    }*/

                    Boolean supportInterface = javaToken2.supportsInterface(bytes).sendAsync().get();
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


}
