package com.xinfin.Web;

import com.xinfin.Model.TokenDetailsResponse;
import com.xinfin.Model.TokenTransferResponse;
import com.xinfin.Model.TransactionResponse;
import com.xinfin.Model.WalletData;
import com.xinfin.callback.CreateAccountCallback;
import com.xinfin.callback.TokenDetailCallback;
import com.xinfin.callback.TokenTransferCallback;
import com.xinfin.contracts.src.main.java.org.web3j.contracts.eip165.generated.ERC165;
import com.xinfin.contracts.src.main.java.org.web3j.contracts.eip20.generated.ERC20;
import com.xinfin.contracts.src.main.java.org.web3j.contracts.eip20.generated.HumanStandardToken;
import com.xinfin.contracts.src.main.java.org.web3j.contracts.eip721.generated.ERC721;
import com.xinfin.contracts.src.main.java.org.web3j.contracts.eip721.generated.ERC721Metadata;

import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Bool;
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
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.methods.response.EthGasPrice;
import org.web3j.protocol.core.methods.response.EthGetTransactionCount;
import org.web3j.protocol.core.methods.response.EthGetTransactionReceipt;
import org.web3j.protocol.core.methods.response.EthSendTransaction;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
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
import java.util.List;
import java.util.concurrent.ExecutionException;


public class Web3jClass {
    Web3j web3;
    public static Web3jClass instance;
    ERC721Metadata javaToken;
    ERC721 javaToken1;
    ERC165 javaToken2;
    HumanStandardToken humanStandardToken;
    BigInteger allowance, decimal, totalSupply, balance;
    String symbol, name,tokenUri;
    TokenDetailsResponse tokenResponse;
    private WalletFile wallet;

    public static Web3jClass getInstance() {
        if (instance == null)
            instance = new Web3jClass();

        return instance;
    }


    public Boolean isWeb3jConnected() {
        web3 = Web3j.build(new

                HttpService("https://rinkeby.infura.io/v3/99c9f09cf44a4bf79613acd8474c42a5"));
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

    public void generateWallet(File walletDirectory, String Password, CreateAccountCallback createAccountCallback)
    {

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


    public void getTokenoinfo(String s, TokenDetailCallback tokenDetailCallback) {

        web3 = Web3j.build(new

                HttpService(AppConstants.BASE_URL));
        try {
            Web3ClientVersion clientVersion = web3.web3ClientVersion().sendAsync().get();
            if (!clientVersion.hasError()) {
                ClientTransactionManager transactionManager = new ClientTransactionManager(web3,
                        "0x301815025bd43513ec36b6c6159ebaa8dff5e36d");
//                Credentials creds = org.web3j.crypto.Credentials.create(AppConstants.PRIVATE_KEY);
                javaToken = null;
                try {
                    javaToken = ERC721Metadata.load("0x301815025bd43513ec36b6c6159ebaa8dff5e36d", web3, transactionManager, new DefaultGasProvider());
                    String name = javaToken.name().send();
                    String tokenUri = javaToken.tokenURI(BigInteger.valueOf(21)).send();
                    getinfo(javaToken,"0x301815025bd43513ec36b6c6159ebaa8dff5e36d" , tokenDetailCallback);
                    // tokenDetailCallback.success(getinfo(javaToken, token_address,tokenDetailCallback));
                    // return getinfo(javaToken, token_address);
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

    public void getBalance(String s, TokenDetailCallback tokenDetailCallback) {

        web3 = Web3j.build(new

                HttpService(AppConstants.BASE_URL));
        try {
            Web3ClientVersion clientVersion = web3.web3ClientVersion().sendAsync().get();
            if (!clientVersion.hasError()) {
                ClientTransactionManager transactionManager = new ClientTransactionManager(web3,
                        "0x301815025bd43513ec36b6c6159ebaa8dff5e36d");
//                Credentials creds = org.web3j.crypto.Credentials.create(AppConstants.PRIVATE_KEY);
                javaToken1 = null;
                try {
                    javaToken1 = ERC721.load("0x301815025bd43513ec36b6c6159ebaa8dff5e36d", web3, transactionManager, new DefaultGasProvider());
//                    String name = javaToken.name().send();
//                    getinfo(javaToken,"0x161cdb7f674ef7c4c8b09b83fb6342a12f1a12c2" , tokenDetailCallback);
                  BigInteger balance =   javaToken1.balanceOf("0xd7813e7cfdf83d6fa3469d7411b52a50ed2b867f").send();
                    String ownerOf =   javaToken1.ownerOf(BigInteger.valueOf(21)).send();


                    // tokenDetailCallback.success(getinfo(javaToken, token_address,tokenDetailCallback));
                    // return getinfo(javaToken, token_address);
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

    public void getSupportInterface(String s, TokenDetailCallback tokenDetailCallback) {

        web3 = Web3j.build(new

                HttpService(AppConstants.BASE_URL));
        try {
            Web3ClientVersion clientVersion = web3.web3ClientVersion().sendAsync().get();
            if (!clientVersion.hasError()) {
                ClientTransactionManager transactionManager = new ClientTransactionManager(web3,
                        "0x301815025bd43513ec36b6c6159ebaa8dff5e36d");
//                Credentials creds = org.web3j.crypto.Credentials.create(AppConstants.PRIVATE_KEY);
                javaToken2 = null;
                try {
                    ERC165 javaToken2 = ERC165.load("0x301815025bd43513ec36b6c6159ebaa8dff5e36d", web3, transactionManager, new DefaultGasProvider());
//                    String name = javaToken.name().send();
//                    getinfo(javaToken,"0x161cdb7f674ef7c4c8b09b83fb6342a12f1a12c2" , tokenDetailCallback);
//                    String s1 = "0x80ac58cd";
//                    byte[] b = new BigInteger(s1, 16).toByteArray();
//                    int  i = 0x80ac58cd;
//
//                    byte[] a =    ByteBuffer.allocate(4).order(ByteOrder.nativeOrder()).putInt(i).array();

                    ByteBuffer b = ByteBuffer.allocate(4);
//b.order(ByteOrder.BIG_ENDIAN); // optional, the initial order of a byte buffer is always BIG_ENDIAN.
                    b.putInt(0x80ac58cd);

                    byte[] result = b.array();
                    Boolean supportInterface = javaToken2.supportsInterface(result).send();

//                    getinfo(javaToken,"0x161cdb7f674ef7c4c8b09b83fb6342a12f1a12c2" , tokenDetailCallback);

                    // tokenDetailCallback.success(getinfo(javaToken, token_address,tokenDetailCallback));
                    // return getinfo(javaToken, token_address);
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

        public void getApproved(String s, TokenDetailCallback tokenDetailCallback) {

            web3 = Web3j.build(new

                    HttpService(AppConstants.BASE_URL));
            try {
                Web3ClientVersion clientVersion = web3.web3ClientVersion().sendAsync().get();
                if (!clientVersion.hasError()) {
                    ClientTransactionManager transactionManager = new ClientTransactionManager(web3,
                            "0x301815025bd43513ec36b6c6159ebaa8dff5e36d");
//                Credentials creds = org.web3j.crypto.Credentials.create(AppConstants.PRIVATE_KEY);
                    javaToken2 = null;
                    try {
                        javaToken1 = ERC721.load("0x301815025bd43513ec36b6c6159ebaa8dff5e36d", web3, transactionManager, new DefaultGasProvider());
//                    String name = javaToken.name().send();
//                    getinfo(javaToken,"0x161cdb7f674ef7c4c8b09b83fb6342a12f1a12c2" , tokenDetailCallback);
                        String getApproved = javaToken1.getApproved(BigInteger.valueOf(21)).send();
//                        0x301815025bd43513ec36b6c6159ebaa8dff5e36d



                        // tokenDetailCallback.success(getinfo(javaToken, token_address,tokenDetailCallback));
                        // return getinfo(javaToken, token_address);
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

    public void isApprovedForAll(String s, TokenDetailCallback tokenDetailCallback) {

        web3 = Web3j.build(new

                HttpService(AppConstants.BASE_URL));
        try {
            Web3ClientVersion clientVersion = web3.web3ClientVersion().sendAsync().get();
            if (!clientVersion.hasError()) {
                ClientTransactionManager transactionManager = new ClientTransactionManager(web3,
                        "0x301815025bd43513ec36b6c6159ebaa8dff5e36d");
//                Credentials creds = org.web3j.crypto.Credentials.create(AppConstants.PRIVATE_KEY);
                javaToken2 = null;
                try {
                    javaToken1 = ERC721.load("0x301815025bd43513ec36b6c6159ebaa8dff5e36d", web3, transactionManager, new DefaultGasProvider());
//                    String name = javaToken.name().send();
//                    getinfo(javaToken,"0x161cdb7f674ef7c4c8b09b83fb6342a12f1a12c2" , tokenDetailCallback);
                    Boolean isApproved = javaToken1.isApprovedForAll("0xd7813e7cfdf83d6fa3469d7411b52a50ed2b867f","0x6ffe09f9302a857fcb122296e3ab3bb80c45cbcd").send();
//                        0x301815025bd43513ec36b6c6159ebaa8dff5e36d



                    // tokenDetailCallback.success(getinfo(javaToken, token_address,tokenDetailCallback));
                    // return getinfo(javaToken, token_address);
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

    public String setApprovalForAll() throws Exception {

        web3 = Web3j.build(new

                HttpService(AppConstants.BASE_URL));
        //Load the required documents for the transfer, with the private key
        //spender privatekey
        Credentials credentials = Credentials.create("0x8a32103448a851b2fed3e95d2b4fbaa5e564f3b8cc42eac0d3be15a96311f355");
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
        //ERC20 token contract method
        // value = value.multiply(value);

        /*ERC20 javaToken = ERC20.load(token_Address, web3, credentials, new DefaultGasProvider());
        BigInteger allowance = javaToken.allowance(token_owner, spender_address).send();
*/


        //  allowance = allowance.subtract( BigInteger.valueOf(123)) ;
       /* Function function = new Function(
                "transferFrom",
                Arrays.asList(new Address("0x32f158af29c171392a1ff35a7387583ff4959053"), new Uint256(1)),
                Collections.singletonList(new TypeReference<Type>() {
                }));*/


        final org.web3j.abi.datatypes.Function function = new Function(
                "setApprovalForAll",
                Arrays.<Type>asList(new Address("0x6ffe09f9302a857fcb122296e3ab3bb80c45cbcd"),
                        new Bool(Boolean.TRUE)),
                Collections.<TypeReference<?>>emptyList());


        //Create RawTransaction transaction object
        String encodedFunction = FunctionEncoder.encode(function);
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

    public String safeTransferFrom() throws Exception {

        web3 = Web3j.build(new

                HttpService(AppConstants.BASE_URL));
        //Load the required documents for the transfer, with the private key
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
        //BigInteger.valueOf(4300000L) If the transaction fails, it is probably a problem with the setting of the fee.
        BigInteger gasLimit = BigInteger.valueOf(60000L);
        //ERC20 token contract method
        // value = value.multiply(value);

        /*ERC20 javaToken = ERC20.load(token_Address, web3, credentials, new DefaultGasProvider());
        BigInteger allowance = javaToken.allowance(token_owner, spender_address).send();
*/


        //  allowance = allowance.subtract( BigInteger.valueOf(123)) ;
       /* Function function = new Function(
                "transferFrom",
                Arrays.asList(new Address("0x32f158af29c171392a1ff35a7387583ff4959053"), new Uint256(1)),
                Collections.singletonList(new TypeReference<Type>() {
                }));*/

        final org.web3j.abi.datatypes.Function function = new Function(
                "safeTransferFrom",
                Arrays.<Type>asList(new Address("0x6ffe09f9302a857fcb122296e3ab3bb80c45cbcd"),
                        new Address("0xd7813e7cfdf83d6fa3469d7411b52a50ed2b867f"),
                        new Uint256(21)),
                Collections.<TypeReference<?>>emptyList());





        //Create RawTransaction transaction object
        String encodedFunction = FunctionEncoder.encode(function);
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


    public String transferfrom( ) throws Exception {
        web3 = Web3j.build(new

                HttpService(AppConstants.BASE_URL));
        //Load the required documents for the transfer, with the private key
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
        //BigInteger.valueOf(4300000L) If the transaction fails, it is probably a problem with the setting of the fee.
        BigInteger gasLimit = BigInteger.valueOf(60000L);
        //ERC20 token contract method
        // value = value.multiply(value);

        /*ERC20 javaToken = ERC20.load(token_Address, web3, credentials, new DefaultGasProvider());
        BigInteger allowance = javaToken.allowance(token_owner, spender_address).send();
*/


        //  allowance = allowance.subtract( BigInteger.valueOf(123)) ;
       /* Function function = new Function(
                "transferFrom",
                Arrays.asList(new Address("0x32f158af29c171392a1ff35a7387583ff4959053"), new Uint256(1)),
                Collections.singletonList(new TypeReference<Type>() {
                }));*/


        final Function function = new Function(
                "transferFrom",
                Arrays.<Type>asList(new Address("0x6ffe09f9302a857fcb122296e3ab3bb80c45cbcd"),
                        new Address("0xd7813e7cfdf83d6fa3469d7411b52a50ed2b867f"),
                        new Uint256(21)),
                Collections.<TypeReference<?>>emptyList());


        //Create RawTransaction transaction object
        String encodedFunction = FunctionEncoder.encode(function);
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

    public String approve( ) throws Exception {
        web3 = Web3j.build(new

                HttpService(AppConstants.BASE_URL));
        //Load the required documents for the transfer, with the private key
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
        //BigInteger.valueOf(4300000L) If the transaction fails, it is probably a problem with the setting of the fee.
        BigInteger gasLimit = BigInteger.valueOf(60000L);
        //ERC20 token contract method
        // value = value.multiply(value);

        /*ERC20 javaToken = ERC20.load(token_Address, web3, credentials, new DefaultGasProvider());
        BigInteger allowance = javaToken.allowance(token_owner, spender_address).send();
*/


        //  allowance = allowance.subtract( BigInteger.valueOf(123)) ;
       /* Function function = new Function(
                "transferFrom",
                Arrays.asList(new Address("0x32f158af29c171392a1ff35a7387583ff4959053"), new Uint256(1)),
                Collections.singletonList(new TypeReference<Type>() {
                }));*/


        final Function function = new Function(
                "approve",
                Arrays.<Type>asList(new Address("0xedb472070566e072f3bdaa50cfa076e772135f33"),
                        new Uint256(BigInteger.valueOf(21))),
                Collections.<TypeReference<?>>emptyList());


        //Create RawTransaction transaction object
        String encodedFunction = FunctionEncoder.encode(function);
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


    public void getinfo(ERC721Metadata javaToken,String s, TokenDetailCallback tokenDetailCallback) {
        try {
//            allowance = javaToken.allowance(token_address, "0x2e550836caaa79884f36e78626363f59ca50e96e").send();
//            balance = javaToken.balanceOf(token_address).send();
            symbol = javaToken.symbol().send();
//            totalSupply = javaToken.totalSupply().send();
            name = javaToken.name().send();
//            tokenUri = javaToken.tokenURI().send();
//            decimal = javaToken.decimals().send();
//            String contract = javaToken.getContractAddress();




            tokenResponse = new TokenDetailsResponse();
//            tokenResponse.setAllowance(allowance);
//            tokenResponse.setBalance(balance);
            tokenResponse.setSymbol(symbol);
//            tokenResponse.setTotalSupply(totalSupply);
            tokenResponse.setName(name);
//            tokenResponse.setSpender_address(token_address);
//            tokenResponse.setDecimal(decimal);
//            tokenResponse.setcontract(contract);
            tokenDetailCallback.success(tokenResponse);

        } catch (Exception exception) {
            exception.printStackTrace();
            tokenDetailCallback.failure(exception.getMessage());
        }

    }



//    @SuppressWarnings("NewApi")
//    public void TransferXdc(String PRIVATE_KEY_TRANSACTION, String FROM_ADDRESS, String TO_ADDRESS, BigInteger value, Long gasprice, Long gaslimit, TokenTransferCallback tokenCallback) {
//        web3 = Web3j.build(new
//
//                HttpService(AppConstants.BASE_URL));
//        try {
//            Web3ClientVersion clientVersion = web3.web3ClientVersion().sendAsync().get();
//            if (!clientVersion.hasError()) {
//
//                // web3.ethGetTransactionCount(AppConstants.FROM_ADDRESS, DefaultBlockParameterName.LATEST);
//                // Log.e("response", web3.ethGetTransactionCount(AppConstants.FROM_ADDRESS, DefaultBlockParameterName.LATEST);
//
//
//                EthGetTransactionCount ethGetTransactionCount = null;
//                try {
//                    ethGetTransactionCount = web3.ethGetTransactionCount(
//                            FROM_ADDRESS, DefaultBlockParameterName.LATEST).sendAsync().get();
//                } catch (
//                        ExecutionException e) {
//                    e.printStackTrace();
//                    tokenCallback.failure(e.getMessage());
//                } catch (
//                        InterruptedException e) {
//                    e.printStackTrace();
//                    tokenCallback.failure(e.getMessage());
//                }
//
//                BigInteger nonce = ethGetTransactionCount.getTransactionCount();
//
//
//                RawTransaction rawTransaction = RawTransaction.createEtherTransaction(
//                        nonce, BigInteger.valueOf(gasprice), BigInteger.valueOf(gaslimit), TO_ADDRESS, value);
//
//                byte[] signedMessage = TransactionEncoder.signMessage(rawTransaction, Credentials.create(PRIVATE_KEY_TRANSACTION));
//                String hexValue = Numeric.toHexString(signedMessage);
//
//
//                EthSendTransaction ethSendTransaction = null;
//                try {
//                    ethSendTransaction = web3.ethSendRawTransaction(hexValue).sendAsync().get();
//                } catch (
//                        ExecutionException e) {
//                    e.printStackTrace();
//                    tokenCallback.failure(e.getMessage());
//                } catch (
//                        InterruptedException e) {
//                    e.printStackTrace();
//                    tokenCallback.failure(e.getMessage());
//                }
//
//                try {
//                    String transactionHash = ethSendTransaction.getTransactionHash();
//                    EthGetTransactionReceipt transactionReceipt =
//                            web3.ethGetTransactionReceipt(transactionHash).send();
//
//
//                    if (transactionHash != null && transactionHash.length() > 0) {
//                        if (transactionReceipt.getTransactionReceipt().isPresent()) {
//                            TransactionReceipt receipt_data = transactionReceipt.getResult();
//                            TokenTransferResponse tokenTransferResponse = new TokenTransferResponse();
//                            tokenTransferResponse.setBlockHash(receipt_data.getBlockHash());
//                            tokenTransferResponse.setBlockNumber(receipt_data.getBlockNumberRaw());
//                            tokenTransferResponse.setTransactionHash(receipt_data.getTransactionHash());
//                            tokenTransferResponse.setStatus(receipt_data.getStatus());
//                            tokenTransferResponse.setFrom(receipt_data.getFrom());
//                            tokenTransferResponse.setTo(receipt_data.getTo());
//                            tokenTransferResponse.setContractAddress(receipt_data.getContractAddress());
//                            tokenTransferResponse.setGasUsed(receipt_data.getGasUsedRaw());
//
//
//                            tokenCallback.success(tokenTransferResponse);
//                        } else {
//                            tokenCallback.success(transactionHash);
//                            //tokenCallback.success("Token has been transfered and Transation has been approved ");
//                        }
//
//                    } else {
//                        tokenCallback.failure("Failed");
//                    }
//                } catch (IOException ioException) {
//                    ioException.printStackTrace();
//                    tokenCallback.failure(ioException.getMessage());
//                }
//            } else {
//
//                tokenCallback.failure("Failed");
//            }
//        } catch (
//                Exception e) {
//
//            tokenCallback.failure(e.getMessage());
////Show Error
//
//        }


        //return null;

    }


//    public void tranferfrom() {
//
//
//        web3 = Web3j.build(new
//
//                HttpService(AppConstants.BASE_URL));
//        try {
//            Web3ClientVersion clientVersion = web3.web3ClientVersion().sendAsync().get();
//            if (!clientVersion.hasError()) {
//                Credentials creds = org.web3j.crypto.Credentials.create("0xbd6b2f02f90e4fd438af6b3fb636cc6912a8b384bb4767487d191c3dfe9713ae");
//                humanStandardToken = null;
//                try {
//
///*
//                    HumanStandardToken contract = HumanStandardToken
//
//                            .deploy(
//                                    web3, creds,new DefaultGasProvider(),
//                                    (BigInteger.valueOf(1000000)),
//                                    new Utf8String("web3j tokens"),
//                                    (BigInteger.TEN),
//                                    new Utf8String("w3j$"))
//                            .get();*/
//
//                    ClientTransactionManager transactionManager = new ClientTransactionManager(web3,
//                            AppConstants.FROM_ADDRESS);
//                    humanStandardToken = HumanStandardToken.load("0x5543f72F0bDB8B38453478403148Fb5E4AF49B23", web3, creds, new DefaultGasProvider());
//                    // TransactionReceipt transfer = javaToken.transferFrom(AppConstants.FROM_ADDRESS, AppConstants.TO_ADDRESS, BigInteger.valueOf(10000)).send();
//
//
//                    // transferFrom(AppConstants.APPROVE_SENDER, AppConstants.TO_ADDRESS, BigInteger.valueOf(10000)).send();
//                    //                     processTransferEventsResponse(humanStandardToken, humanStandardToken.transferFrom(new Address(AppConstants.APPROVE_SENDER),new Address(AppConstants.TO_ADDRESS) ,new Uint256(BigInteger.valueOf(1000000000000L))));
//
//                    processTransferEventsResponse(humanStandardToken, humanStandardToken.transferFrom("0x5543f72F0bDB8B38453478403148Fb5E4AF49B23", AppConstants.FROM_ADDRESS, BigInteger.valueOf(10000)).send());
//
//
//                    // ArrayList<ERC20.TransferEventResponse> responses = (ArrayList<ERC20.TransferEventResponse>) javaToken.getTransferEvents(transfer);
//
//
//                } catch (Exception exception) {
//                    exception.printStackTrace();
//                    System.err.printf("hash=%s from=%s to=%s amount=%s%n", exception.getMessage());
//                }
//
//            }
//        } catch (
//                Exception e) {
//
////Show Error
//
//        }
//
//    }


//    public void approve() {
//
//
//        web3 = Web3j.build(new
//
//                HttpService(AppConstants.BASE_URL));
//        try {
//            Web3ClientVersion clientVersion = web3.web3ClientVersion().sendAsync().get();
//            if (!clientVersion.hasError()) {
//                Credentials creds = org.web3j.crypto.Credentials.create(AppConstants.PRIVATE_KEY_APPROVE);
//                javaToken = null;
//                try {
//                    javaToken = ERC20.load(AppConstants.APPROVE_contract, web3, creds, new DefaultGasProvider());
//                    // TransactionReceipt transfer = javaToken.transferFrom(AppConstants.FROM_ADDRESS, AppConstants.TO_ADDRESS, BigInteger.valueOf(10000)).send();
//
//
//                    TransactionReceipt transactionReceipt = javaToken.approve(AppConstants.APPROVE_SENDER, BigInteger.valueOf(1000)).send();
//
//
//                    // ArrayList<ERC20.TransferEventResponse> responses = (ArrayList<ERC20.TransferEventResponse>) javaToken.getTransferEvents(transfer);
//
//
//                } catch (Exception exception) {
//                    exception.printStackTrace();
//                    System.err.printf("hash=%s from=%s to=%s amount=%s%n", exception.getMessage());
//                }
//
//            }
//        } catch (
//                Exception e) {
//
////Show Error
//
//        }
//
//    }

//    private TransactionResponse<TransferEventResponse>
//    processTransferEventsResponse(
//            HumanStandardToken humanStandardToken,
//            TransactionReceipt transactionReceipt) {
//
//        return processEventResponse(
//                humanStandardToken.getTransferEvents(transactionReceipt),
//                transactionReceipt,
//                TransferEventResponse::new);
//    }
//
//
//    @SuppressWarnings("NewApi")
//    private <T, R> TransactionResponse<R> processEventResponse(
//            List<T> eventResponses, TransactionReceipt transactionReceipt, Function<T, R> map) {
//        if (!eventResponses.isEmpty()) {
//            return new TransactionResponse<>(
//                    transactionReceipt.getTransactionHash(),
//                    map.apply(eventResponses.get(0)));
//        } else {
//            return new TransactionResponse<>(
//                    transactionReceipt.getTransactionHash());
//        }
//    }


//    public static class TransferEventResponse {
//        private String from;
//        private String to;
//        private long value;
//
//        public TransferEventResponse() {
//        }
//
//        public TransferEventResponse(
//                HumanStandardToken.TransferEventResponse transferEventResponse) {
//            this.from = transferEventResponse._from;
//            this.to = transferEventResponse._to;
//            this.value = transferEventResponse._value.longValueExact();
//        }
//
//        public String getFrom() {
//            return from;
//        }
//
//        public void setFrom(String from) {
//            this.from = from;
//        }
//
//        public String getTo() {
//            return to;
//        }
//
//        public void setTo(String to) {
//            this.to = to;
//        }
//
//        public long getValue() {
//            return value;
//        }
//
//        public void setValue(long value) {
//            this.value = value;
//        }
//    }

//}
