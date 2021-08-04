package xinfin.XDCAndroid;


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
import java.util.Arrays;
import java.util.Collections;
import java.util.concurrent.ExecutionException;

import xinfin.XDCAndroid.Model.TokenDetailsResponse;
import xinfin.XDCAndroid.Model.WalletData;
import xinfin.XDCAndroid.callback.CreateAccountCallback;
import xinfin.XDCAndroid.callback.TokenDetailCallback;
import xinfin.XDCAndroid.contracts.src.main.java.XRC20;


public class XDC20Client {
    Web3j web3;
    public static XDC20Client instance;
    XRC20 javaToken;
    BigInteger allowance, decimal, totalSupply, balance;
    String symbol, name;
    TokenDetailsResponse tokenResponse;
    private WalletFile wallet;

    public static XDC20Client getInstance() {
        if (instance == null)
            instance = new XDC20Client();

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


    public void getTokenoinfo(String token_address, TokenDetailCallback tokenDetailCallback) {

        web3 = Web3j.build(new

                HttpService(AppConstants.BASE_URL));
        try {
            Web3ClientVersion clientVersion = web3.web3ClientVersion().sendAsync().get();
            if (!clientVersion.hasError()) {
                ClientTransactionManager transactionManager = new ClientTransactionManager(web3,
                        token_address);

                try {
                    javaToken = XRC20.load(token_address, web3, transactionManager, new DefaultGasProvider());
                    getinfo(javaToken, token_address, tokenDetailCallback);
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


    public String getAllowance(String token_address, String owner_address, String spender_address) {

        if (isWeb3jConnected()) {

            ClientTransactionManager transactionManager = new ClientTransactionManager(web3,
                    owner_address);
            javaToken = XRC20.load(token_address, web3, transactionManager, new DefaultGasProvider());
            try {
                allowance = javaToken.allowance(owner_address, spender_address).send();
                return String.valueOf(allowance);
            } catch (Exception e) {
                e.printStackTrace();
                return e.getMessage();
            }


        } else {
            return "check your connection";
        }


    }


    public String getBalance(String token_address, String owner_address) {

        if (isWeb3jConnected()) {

            ClientTransactionManager transactionManager = new ClientTransactionManager(web3,
                    owner_address);
            javaToken = XRC20.load(token_address, web3, transactionManager, new DefaultGasProvider());
            try {
                allowance = javaToken.balanceOf(owner_address).send();
                return String.valueOf(allowance);
            } catch (Exception e) {
                e.printStackTrace();
                return e.getMessage();
            }


        } else {
            return "check your connection";
        }


    }

    public void getinfo(XRC20 javaToken, String token_address, TokenDetailCallback tokenDetailCallback) {
        try {
            balance = javaToken.balanceOf(token_address).send();
            symbol = javaToken.symbol().send();
            totalSupply = javaToken.totalSupply().send();
            name = javaToken.name().send();
            decimal = javaToken.decimals().send();
            String contract = javaToken.getContractAddress();


            tokenResponse = new TokenDetailsResponse();
            tokenResponse.setAllowance(BigInteger.valueOf(0));
            tokenResponse.setBalance(balance);
            tokenResponse.setSymbol(symbol);
            tokenResponse.setTotalSupply(totalSupply);
            tokenResponse.setName(name);
            tokenResponse.setSpender_address(token_address);
            tokenResponse.setDecimal(decimal);
            tokenResponse.setcontract(contract);
            tokenDetailCallback.success(tokenResponse);

        } catch (Exception exception) {
            exception.printStackTrace();
            tokenDetailCallback.failure(exception.getMessage());
        }

    }


    @SuppressWarnings("NewApi")
    public String TransferXdc(String PRIVATE_KEY_TRANSACTION, String FROM_ADDRESS, String TO_ADDRESS, BigInteger value, Long gasprice, Long gaslimit) {
        web3 = Web3j.build(new

                HttpService(AppConstants.BASE_URL));
        try {
            Web3ClientVersion clientVersion = web3.web3ClientVersion().sendAsync().get();
            if (!clientVersion.hasError()) {



                EthGetTransactionCount ethGetTransactionCount = null;
                try {
                    ethGetTransactionCount = web3.ethGetTransactionCount(
                            FROM_ADDRESS, DefaultBlockParameterName.LATEST).sendAsync().get();
                } catch (
                        ExecutionException e) {
                    e.printStackTrace();
                    return e.getMessage();
                } catch (
                        InterruptedException e) {
                    e.printStackTrace();
                    return e.getMessage();
                }

                BigInteger nonce = ethGetTransactionCount.getTransactionCount();


                RawTransaction rawTransaction = RawTransaction.createEtherTransaction(
                        nonce, BigInteger.valueOf(gasprice), BigInteger.valueOf(gaslimit), TO_ADDRESS, value);

                byte[] signedMessage = TransactionEncoder.signMessage(rawTransaction, Credentials.create(PRIVATE_KEY_TRANSACTION));
                String hexValue = Numeric.toHexString(signedMessage);


                EthSendTransaction ethSendTransaction = null;
                try {
                    ethSendTransaction = web3.ethSendRawTransaction(hexValue).sendAsync().get();
                } catch (
                        ExecutionException e) {
                    e.printStackTrace();
                    return e.getMessage();

                } catch (
                        InterruptedException e) {
                    e.printStackTrace();

                    return e.getMessage();
                }

                String transactionHash = ethSendTransaction.getTransactionHash();
                return transactionHash;


            } else {
                return "Failed";
            }
        } catch (
                Exception e) {

            return e.getMessage();
            //Show Error

        }
    }


    public String approveXRC20Token(String token_address, String private_key, String spender_address, String value) throws ExecutionException, InterruptedException, IOException {
        web3 = Web3j.build(new

                HttpService(AppConstants.BASE_URL));
        //Load the required data for the approve, with the private key
        Credentials credentials = Credentials.create(private_key);
        // Get nonce, the number of transactions
        BigInteger nonce;
        //token owner - wallet
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
        //BigInteger.valueOf(4300000L) If the transaction fails, it is probably a problem with the setting of the fee.
        BigInteger gasLimit = BigInteger.valueOf(60000L);
        //XRC20 token contract method
        Function function = new Function(
                "approve",
                Arrays.asList(new Address(spender_address), new Uint256(BigInteger.valueOf(Long.parseLong(value)))),
                Collections.singletonList(new TypeReference<Type>() {
                }));
        //Create RawTransaction transaction object
        String encodedFunction = FunctionEncoder.encode(function);
        //token address
        RawTransaction rawTransaction = RawTransaction.createTransaction(nonce, gasPrice, gasLimit,
                token_address, encodedFunction);

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

    public static String transferXRC20Token(String token_address, String private_key, String receiver_add, String value) throws ExecutionException, InterruptedException, IOException {
        Web3j web3j = Web3j.build(new HttpService(AppConstants.BASE_URL));
        //Load the required documents for the transfer, with the private key
        Credentials credentials = Credentials.create(private_key);
        // Get nonce, the number of transactions
        BigInteger nonce;
        EthGetTransactionCount ethGetTransactionCount = web3j.ethGetTransactionCount(credentials.getAddress(), DefaultBlockParameterName.LATEST).sendAsync().get();
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
        //XRC20 token contract method
        BigInteger a
                = new BigInteger(value);
        BigInteger b
                = new BigInteger("1000000000000000000");

        // Using divide() method
        BigInteger value_final = a.multiply(b);

        Function function = new Function(
                "transfer",
                Arrays.asList(new Address(receiver_add), new Uint256(value_final)),
                Collections.singletonList(new TypeReference<Type>() {
                }));
        //Create RawTransaction transaction object
        String encodedFunction = FunctionEncoder.encode(function);
        RawTransaction rawTransaction = RawTransaction.createTransaction(nonce, gasPrice, gasLimit,
                token_address, encodedFunction);

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


    public String increaseAllownce(String owner_Address, String spender_address, String private_key, String value, String token_address) throws Exception {
        web3 = Web3j.build(new

                HttpService(AppConstants.BASE_URL));
        //Load the required documents for the transfer, with the private key
        Credentials credentials = Credentials.create(private_key);
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
        //BigInteger.valueOf(4300000L) If the transaction fails, it is probably a problem with the setting of the fee.
        BigInteger gasLimit = BigInteger.valueOf(60000L);
        //XRC20 token contract method
        XRC20 javaToken = XRC20.load(token_address, web3, credentials, new DefaultGasProvider());
        BigInteger allowance = javaToken.allowance(owner_Address, spender_address).send();

        allowance = allowance.add(BigInteger.valueOf(Long.parseLong(value)));
        Function function = new Function(
                "approve",
                Arrays.asList(new Address(spender_address), new Uint256(allowance)),
                Collections.singletonList(new TypeReference<Type>() {
                }));
        //Create RawTransaction transaction object
        String encodedFunction = FunctionEncoder.encode(function);
        RawTransaction rawTransaction = RawTransaction.createTransaction(nonce, gasPrice, gasLimit,
                token_address, encodedFunction);

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


    public String decreaseAllownce(String owner_Address, String spender_address, String private_key, String value, String token_address) throws Exception {
        web3 = Web3j.build(new

                HttpService(AppConstants.BASE_URL));
        //Load the required documents for the transfer, with the private key
        Credentials credentials = Credentials.create(private_key);
        // Get nonce, the number of transactions
        BigInteger nonce;
        EthGetTransactionCount ethGetTransactionCount = web3.ethGetTransactionCount(owner_Address, DefaultBlockParameterName.LATEST).sendAsync().get();
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
        //BigInteger.valueOf(4300000L) If the transaction fails, it is probably a problem with the setting of the fee.
        BigInteger gasLimit = BigInteger.valueOf(60000L);
        //XRC20 token contract method

        XRC20 javaToken = XRC20.load(token_address, web3, credentials, new DefaultGasProvider());
        BigInteger allowance = javaToken.allowance(owner_Address, spender_address).send();

        allowance = allowance.subtract(BigInteger.valueOf(Long.parseLong(value)));
        Function function = new Function(
                "approve",
                Arrays.asList(new Address(spender_address), new Uint256(allowance)),
                Collections.singletonList(new TypeReference<Type>() {
                }));
        //Create RawTransaction transaction object
        String encodedFunction = FunctionEncoder.encode(function);
        RawTransaction rawTransaction = RawTransaction.createTransaction(nonce, gasPrice, gasLimit,
                token_address, encodedFunction);

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


    public String approve(String owner_Address, String spender_address, String private_key, String value, String token_address) throws Exception {
        web3 = Web3j.build(new

                HttpService(AppConstants.BASE_URL));
        //Load the required documents for the transfer, with the private key
        Credentials credentials = Credentials.create(private_key);
        // Get nonce, the number of transactions
        BigInteger nonce;
        EthGetTransactionCount ethGetTransactionCount = web3.ethGetTransactionCount(owner_Address, DefaultBlockParameterName.LATEST).sendAsync().get();
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
        //BigInteger.valueOf(4300000L) If the transaction fails, it is probably a problem with the setting of the fee.
        BigInteger gasLimit = BigInteger.valueOf(60000L);
        //XRC20 token contract method
        Function function = null;
        try {
            function = new Function(
                    "approve",
                    Arrays.asList(new Address(spender_address), new Uint256(Long.parseLong(value))),
                    Collections.singletonList(new TypeReference<Type>() {
                    }));
        } catch (Exception e) {
            return e.getMessage();
        }

        //Create RawTransaction transaction object
        String encodedFunction = FunctionEncoder.encode(function);
        RawTransaction rawTransaction = RawTransaction.createTransaction(nonce, gasPrice, gasLimit,
                token_address, encodedFunction);

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


    public String transferfrom(String spender_address, String to_address, String private_key, String value, String token_Address) throws Exception {
        web3 = Web3j.build(new

                HttpService(AppConstants.BASE_URL));
        //Load the required documents for the transfer, with the private key
        //spender privatekey
        Credentials credentials = Credentials.create(private_key);
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
        //BigInteger.valueOf(4300000L) If the transaction fails, it is probably a problem with the setting of the fee.
        BigInteger gasLimit = BigInteger.valueOf(60000L);
        //XRC20 token contract method
        final Function function = new Function(
                "transferFrom",
                Arrays.<Type>asList(new Address(spender_address),
                        new Address(to_address),
                        new Uint256(BigInteger.valueOf(Long.parseLong(value)))),
                Collections.<TypeReference<?>>emptyList());


        //Create RawTransaction transaction object
        String encodedFunction = FunctionEncoder.encode(function);
        RawTransaction rawTransaction = RawTransaction.createTransaction(nonce, gasPrice, gasLimit,
                token_Address, encodedFunction);

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
