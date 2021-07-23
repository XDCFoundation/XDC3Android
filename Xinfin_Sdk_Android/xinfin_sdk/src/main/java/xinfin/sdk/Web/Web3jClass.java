package xinfin.sdk.Web;



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
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.Function;

import xinfin.sdk.Model.TokenDetailsResponse;
import xinfin.sdk.Model.TokenTransferResponse;
import xinfin.sdk.Model.WalletData;
import xinfin.sdk.callback.CreateAccountCallback;
import xinfin.sdk.callback.TokenDetailCallback;
import xinfin.sdk.callback.TokenTransferCallback;
import xinfin.sdk.contracts.src.main.java.org.web3j.contracts.eip20.generated.ERC20;


public class Web3jClass {
    Web3j web3;
    public static Web3jClass instance;
    ERC20 javaToken;
    BigInteger allowance, decimal, totalSupply, balance;
    String symbol, name;
    TokenDetailsResponse tokenResponse;
    private WalletFile wallet;

    public static Web3jClass getInstance() {
        if (instance == null)
            instance = new Web3jClass();

        return instance;
    }


    public Boolean isWeb3jConnected()
    {
        web3 = Web3j.build(new

                HttpService(AppConstants.BASE_URL));
        try {
            Web3ClientVersion clientVersion = web3.web3ClientVersion().sendAsync().get();
            if (!clientVersion.hasError())
            {
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


    public void getTokenoinfo(String token_address, TokenDetailCallback tokenDetailCallback) {

        web3 = Web3j.build(new

                HttpService(AppConstants.BASE_URL));
        try {
            Web3ClientVersion clientVersion = web3.web3ClientVersion().sendAsync().get();
            if (!clientVersion.hasError()) {
                Credentials creds = org.web3j.crypto.Credentials.create(AppConstants.PRIVATE_KEY);
                javaToken = null;
                try {
                    javaToken = ERC20.load(token_address, web3, creds, new DefaultGasProvider());
                    getinfo(javaToken, token_address, tokenDetailCallback);
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

    public void getinfo(ERC20 javaToken, String token_address, TokenDetailCallback tokenDetailCallback) {
        try {
            allowance = javaToken.allowance(token_address, "0x2e550836caaa79884f36e78626363f59ca50e96e").send();
            balance = javaToken.balanceOf(token_address).send();
            symbol = javaToken.symbol().send();
            totalSupply = javaToken.totalSupply().send();
            name = javaToken.name().send();
            decimal = javaToken.decimals().send();
            String contract = javaToken.getContractAddress();




            tokenResponse = new TokenDetailsResponse();
            tokenResponse.setAllowance(allowance);
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
    public void TransferXdc(String PRIVATE_KEY_TRANSACTION, String FROM_ADDRESS, String TO_ADDRESS, BigInteger value, Long gasprice, Long gaslimit, TokenTransferCallback tokenCallback) {
        web3 = Web3j.build(new

                HttpService(AppConstants.BASE_URL));
        try {
            Web3ClientVersion clientVersion = web3.web3ClientVersion().sendAsync().get();
            if (!clientVersion.hasError()) {

                // web3.ethGetTransactionCount(AppConstants.FROM_ADDRESS, DefaultBlockParameterName.LATEST);
                // Log.e("response", web3.ethGetTransactionCount(AppConstants.FROM_ADDRESS, DefaultBlockParameterName.LATEST);


                EthGetTransactionCount ethGetTransactionCount = null;
                try {
                    ethGetTransactionCount = web3.ethGetTransactionCount(
                            FROM_ADDRESS, DefaultBlockParameterName.LATEST).sendAsync().get();
                } catch (
                        ExecutionException e) {
                    e.printStackTrace();
                    tokenCallback.failure(e.getMessage());
                } catch (
                        InterruptedException e) {
                    e.printStackTrace();
                    tokenCallback.failure(e.getMessage());
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
                    tokenCallback.failure(e.getMessage());
                } catch (
                        InterruptedException e) {
                    e.printStackTrace();
                    tokenCallback.failure(e.getMessage());
                }

                try {
                    String transactionHash = ethSendTransaction.getTransactionHash();
                    EthGetTransactionReceipt transactionReceipt =
                            web3.ethGetTransactionReceipt(transactionHash).send();


                    if (transactionHash != null && transactionHash.length() > 0) {
                        if (transactionReceipt.getTransactionReceipt().isPresent()) {
                            TransactionReceipt receipt_data = transactionReceipt.getResult();
                            TokenTransferResponse tokenTransferResponse = new TokenTransferResponse();
                            tokenTransferResponse.setBlockHash(receipt_data.getBlockHash());
                            tokenTransferResponse.setBlockNumber(receipt_data.getBlockNumberRaw());
                            tokenTransferResponse.setTransactionHash(receipt_data.getTransactionHash());
                            tokenTransferResponse.setStatus(receipt_data.getStatus());
                            tokenTransferResponse.setFrom(receipt_data.getFrom());
                            tokenTransferResponse.setTo(receipt_data.getTo());
                            tokenTransferResponse.setContractAddress(receipt_data.getContractAddress());
                            tokenTransferResponse.setGasUsed(receipt_data.getGasUsedRaw());


                            tokenCallback.success(tokenTransferResponse);
                        } else {
                            tokenCallback.success(transactionHash);
                            //tokenCallback.success("Token has been transfered and Transation has been approved ");
                        }

                    } else {
                        tokenCallback.failure("Failed");
                    }
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                    tokenCallback.failure(ioException.getMessage());
                }
            } else {

                tokenCallback.failure("Failed");
            }
        } catch (
                Exception e) {

            tokenCallback.failure(e.getMessage());
//Show Error

        }


        //return null;

    }




}
