package com.XDCAndroid;

import com.XDCAndroid.Model.Token721DetailsResponse;
import com.XDCAndroid.Model.TokenDetailsResponse;
import com.XDCAndroid.callback.Token721DetailCallback;
import com.XDCAndroid.contracts.src.main.java.XRC165;
import com.XDCAndroid.contracts.src.main.java.XRC721;
import com.XDCAndroid.contracts.src.main.java.XRC721Enumerable;
import com.XDCAndroid.contracts.src.main.java.XRC721Metadata;

import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.RawTransaction;
import org.web3j.crypto.TransactionEncoder;
import org.web3j.crypto.WalletFile;
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

import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Collections;

public class XDC721Client {
    Web3j web3;
    public static XDC721Client instance;
    XRC721 javaToken;
    String symbol, name;
    TokenDetailsResponse tokenResponse;
    private WalletFile wallet;

    public static XDC721Client getInstance() {
        if (instance == null)
            instance = new XDC721Client();

        return instance;
    }


    public Boolean isWeb3jConnected() {
        web3 = Web3j.build(new HttpService(AppConstants.BASE_URL));
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

        if (isWeb3jConnected()) {
            ClientTransactionManager transactionManager = new ClientTransactionManager(web3,
                    tokenAddress);
            javaToken = null;
            try {
                XRC721Metadata javaToken = XRC721Metadata.load(tokenAddress, web3, transactionManager, new DefaultGasProvider());
                getinfo(javaToken, tokenAddress, tokenDetailCallback);
            } catch (Exception exception) {
                exception.printStackTrace();
                tokenDetailCallback.failure(exception.getMessage());
            }

        } else {

            //Show Error
            tokenDetailCallback.failure("Connection has been failed");
        }
    }


    public void getinfo(XRC721Metadata javaToken, String tokenAddress, Token721DetailCallback tokenDetailCallback) {
        try {
            /** @notice A descriptive name for a collection of NFTs in this contract */
            symbol = javaToken.symbol().send();

            /** @notice An abbreviated name for NFTs in this contract */
            name = javaToken.name().send();

            Token721DetailsResponse tokenResponse = new Token721DetailsResponse();
            tokenResponse.setSymbol(symbol);
            tokenResponse.setName(name);
            tokenResponse.setTokenAddress(tokenAddress);
            tokenDetailCallback.success(tokenResponse);

        } catch (Exception exception) {
            exception.printStackTrace();
            tokenDetailCallback.failure(exception.getMessage());
        }
    }


    /** @notice A distinct Uniform Resource Identifier (URI) for a given asset.
     * @dev Throws if `_tokenId` is not a valid NFT. URIs are defined in RFC
     * 3986. The URI may point to a JSON file that conforms to the "XRC721 Metadata JSON Schema".
     */

    public String getTokenUri(String tokenAddress, String tokenid) {

        if (isWeb3jConnected()) {
            ClientTransactionManager transactionManager = new ClientTransactionManager(web3,
                    tokenAddress);
            try {
                XRC721Metadata javaToken1 = XRC721Metadata.load(tokenAddress, web3, transactionManager, new DefaultGasProvider());
                String balance = javaToken1.tokenURI(BigInteger.valueOf(Long.parseLong(tokenid))).send();


                return String.valueOf(balance);

                // tokenDetailCallback.success(getinfo(javaToken, token_address,tokenDetailCallback));
                // return getinfo(javaToken, token_address);
            } catch (Exception exception) {
                exception.printStackTrace();
                return exception.getMessage();
            }

        } else {

            //Show Error
            return "Connection has been failed";
        }
    }

    /** @notice Count all NFTs assigned to an owner
     *  @dev NFTs assigned to the zero address are considered invalid, and this
     *  function throws for queries about the zero address.
     *  @param tokenAddress An address for whom to query the balance
     *  @param ownerAddress The address which owns the funds.
     *  @return The number of NFTs owned by `_owner`, possibly zero */

    public String getBalance(String tokenAddress, String ownerAddress) {

        if (isWeb3jConnected()) {
            ClientTransactionManager transactionManager = new ClientTransactionManager(web3,
                    tokenAddress);
            try {
                XRC721 javaToken1 = XRC721.load(tokenAddress, web3, transactionManager, new DefaultGasProvider());
                BigInteger balance = javaToken1.balanceOf(ownerAddress).send();


                return String.valueOf(balance);

                // tokenDetailCallback.success(getinfo(javaToken, token_address,tokenDetailCallback));
                // return getinfo(javaToken, token_address);
            } catch (Exception exception) {
                exception.printStackTrace();
                return exception.getMessage();
            }

        } else {

            //Show Error
            return "Connection has been failed";
        }
    }


    /**@notice Count NFTs tracked by this contract
     * them has an assigned and queryable owner not equal to the zero address
     *  @param tokenAddress An address for whom to query the balance
     * @return A count of valid NFTs tracked by this contract, where each one of */

    public String gettotalSupply(String tokenAddress) {


        if (isWeb3jConnected()) {
            ClientTransactionManager transactionManager = new ClientTransactionManager(web3,
                    tokenAddress);
            try {
                XRC721Enumerable javaToken1 = XRC721Enumerable.load(tokenAddress, web3, transactionManager, new DefaultGasProvider());
                BigInteger totalSupply = javaToken1.totalSupply().send();


                return String.valueOf(totalSupply);

                // tokenDetailCallback.success(getinfo(javaToken, token_address,tokenDetailCallback));
                // return getinfo(javaToken, token_address);
            } catch (Exception exception) {
                exception.printStackTrace();
                return exception.getMessage();
            }

        } else {

            //Show Error
            return "Connection has been failed";
        }
    }


    /** @notice Enumerate valid NFTs
     *  @dev Throws if `_index` >= `totalSupply()`.
     *  @param tokenAddress An address for whom to query the balance
     *  @param index A counter less than `totalSupply()`
     *  @return The token identifier for the `_index`th NFT */

    public String gettokenByIndex(String tokenAddress, String index) {


        if (isWeb3jConnected()) {
            ClientTransactionManager transactionManager = new ClientTransactionManager(web3,
                    tokenAddress);
            try {
                XRC721Enumerable javaToken1 = XRC721Enumerable.load(tokenAddress, web3, transactionManager, new DefaultGasProvider());
                BigInteger token = javaToken1.tokenByIndex(new BigInteger(index)).send();


                return String.valueOf(token);

                // tokenDetailCallback.success(getinfo(javaToken, token_address,tokenDetailCallback));
                // return getinfo(javaToken, token_address);
            } catch (Exception exception) {
                exception.printStackTrace();
                return exception.getMessage();
            }

        } else {

            //Show Error
            return "Connection has been failed";
        }
    }




    /** @notice Enumerate NFTs assigned to an owner
     *  @dev Throws if `_index` >= `balanceOf(_owner)` or if `_owner` is the zero address, representing invalid NFTs.
     *  @param tokenAddress An address for whom to query the balance
     *  @param ownerAddress The address which owns the funds.
     *  @param index A counter less than `totalSupply()`
     *  @return The token identifier for the `_index`th NFT assigned to `_owner` */

    public String tokenOfOwnerByIndex(String tokenAddress, String ownerAddress, String index) {


        if (isWeb3jConnected()) {
            ClientTransactionManager transactionManager = new ClientTransactionManager(web3,
                    tokenAddress);
            try {
                XRC721Enumerable javaToken1 = XRC721Enumerable.load(tokenAddress, web3, transactionManager, new DefaultGasProvider());
                BigInteger token = javaToken1.tokenOfOwnerByIndex(ownerAddress, new BigInteger(index)).send();


                return String.valueOf(token);

                // tokenDetailCallback.success(getinfo(javaToken, token_address,tokenDetailCallback));
                // return getinfo(javaToken, token_address);
            } catch (Exception exception) {
                exception.printStackTrace();
                return exception.getMessage();
            }

        } else {

            //Show Error
            return "Connection has been failed";
        }
    }


    /** @notice Find the owner of an NFT
     *  @dev NFTs assigned to zero address are considered invalid, and queries
     *  about them do throw.
     *  @param tokenAddress An address for whom to query the balance
     *  @param tokenid The identifier for an NFT
     *  @return The address of the owner of the NFT */
    public String getOwnerof(String tokenAddress, String tokenid) {


        if (isWeb3jConnected()) {
            ClientTransactionManager transactionManager = new ClientTransactionManager(web3,
                    tokenAddress);
            try {
                XRC721 javaToken1 = XRC721.load(tokenAddress, web3, transactionManager, new DefaultGasProvider());
                String ownerOf = javaToken1.ownerOf(BigInteger.valueOf(Long.parseLong(tokenid))).send();


                return ownerOf;

                // tokenDetailCallback.success(getinfo(javaToken, token_address,tokenDetailCallback));
                // return getinfo(javaToken, token_address);
            } catch (Exception exception) {
                exception.printStackTrace();
                return exception.getMessage();
            }

        } else {

            //Show Error
            return "Connection has been failed";
        }
    }

    /** @notice Query if a contract implements an interface
     *  @dev Interface identification is specified in XRC-165. This function
     *  uses less than 30,000 gas.
     *  @param tokenAddress An address for whom to query the balance
     *  @param interfaceID The interface identifier, as specified in XRC-165
     *  @return `true` if the contract implements `interfaceID` and
     *  `interfaceID` is not 0xffffffff, `false` otherwise */
    public boolean getsupportInterface(String tokenAddress, String interfaceID) {


        if (isWeb3jConnected()) {
            ClientTransactionManager transactionManager = new ClientTransactionManager(web3,
                    tokenAddress);
            try {
                XRC165 javaToken2 = XRC165.load(tokenAddress, web3, transactionManager, new DefaultGasProvider());
                ByteBuffer b = ByteBuffer.allocate(4);

                b.putInt(0x80ac58cd);
                byte[] result = b.array();

                   /* final char[] chars = Character.toChars(Integer.parseInt("80ac58cd"));
                    final String s = new String(chars);
                    @SuppressWarnings("NewApi") final byte[] asBytes = s.getBytes(StandardCharsets.UTF_8);*/


                Boolean supportInterface = javaToken2.supportsInterface(result).send();
                return supportInterface;
            } catch (Exception exception) {
                exception.printStackTrace();
                return false;
            }
        } else {
            //Show Error
            return false;
        }
    }


    /** @notice Change or reaffirm the approved address for an NFT
     *  @dev The zero address indicates there is no approved address.
     *  Throws unless `msg.sender` is the current NFT owner, or an authorized
     *  operator of the current owner.
     *  @param tokenAddress An address for whom to query the balance
     *  @param privatekey     Owner Private key.
     *  @param tokenid The identifier for an NFT
     *  @param receiverAddress The address to transfer to. */

    public String approve(String tokenAddress, String privatekey, String tokenid, String receiverAddress) throws Exception {

        if(isWeb3jConnected()) {
            //spender privatekey
            Credentials credentials = Credentials.create(privatekey);
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
                    Arrays.<Type>asList(new Address(receiverAddress),
                            new Uint256(BigInteger.valueOf(Long.parseLong(tokenid)))),
                    Collections.<TypeReference<?>>emptyList());

            //Create RawTransaction transaction object
            String encodedFunction = FunctionEncoder.encode(function);
            //token address
            RawTransaction rawTransaction = RawTransaction.createTransaction(nonce, gasPrice, gasLimit,
                    tokenAddress, encodedFunction);
            //Signature Transaction
            byte[] signMessage = TransactionEncoder.signMessage(rawTransaction, credentials);
            String hexValue = Numeric.toHexString(signMessage);
            //Send the transaction
            EthSendTransaction ethSendTransaction = web3.ethSendRawTransaction(hexValue).sendAsync().get();
            String hash = ethSendTransaction.getTransactionHash();
            if (hash != null) {
                return hash;
            }
            else
            {
                return "Failed";
            }
        }
        else
        {
            return "Failed";
        }

    }

    /** @notice Get the approved address for a single NFT
     *  @dev Throws if `_tokenId` is not a valid NFT.
     *  @param tokenAddress An address for whom to query the balance
     *  @param tokenId The identifier for an NFT */

    public String getApproved(String tokenAddress, String tokenId) {


        if (isWeb3jConnected()) {
            ClientTransactionManager transactionManager = new ClientTransactionManager(web3,
                    tokenAddress);
            try {
                XRC721 javaToken1 = XRC721.load(tokenAddress, web3, transactionManager, new DefaultGasProvider());
                String getApproved = javaToken1.getApproved(BigInteger.valueOf(Long.parseLong(tokenId))).send();
                return getApproved;
            } catch (Exception exception) {
                exception.printStackTrace();
                return (exception.getMessage());
            }

        } else {

            //Show Error
            return "Connection has been failed";
        }
    }


    /** @notice Query if an address is an authorized operator for another address
     *  @param tokenAddress An address for whom to query the balance
     *  @param ownerAddress The address which owns the funds.
     *  @param OperatorAddress The address that acts on behalf of the owner
     *  @return True if `_operator` is an approved operator for `_owner`, false otherwise */

    public boolean isApprovedForAll(String tokenAddress, String ownerAddress, String OperatorAddress) {


        if (isWeb3jConnected()) {
            ClientTransactionManager transactionManager = new ClientTransactionManager(web3,
                    tokenAddress);
            try {
                XRC721 javaToken = XRC721.load(tokenAddress, web3, transactionManager, new DefaultGasProvider());
                Boolean isApproved = javaToken.isApprovedForAll(ownerAddress, OperatorAddress).send();

                return isApproved;

            } catch (Exception exception) {
                exception.printStackTrace();
                return false;
            }

        } else {

            //Show Error
            return false;
        }
    }


    /** @notice Enable or disable approval for a third party ("operator") to manage
     *  all of `msg.sender`'s assets
     *  @dev Emits the ApprovalForAll event. The contract MUST allow
     *  multiple operators per owner.
     *  @param tokenAddress An address for whom to query the balance
     *  @param privatekey    Owner Private key.
     *  @param OperatorAddress Address to add to the set of authorized operators
     *  @param booleanvalue if the operator is approved, false to revoke approval */

    public String setApprovalForAll(String tokenAddress, String privatekey, String OperatorAddress, String booleanvalue) throws Exception {


        if(isWeb3jConnected()) {
            //spender privatekey
            Credentials credentials = Credentials.create(privatekey);
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
            final org.web3j.abi.datatypes.Function function = new Function(
                    "setApprovalForAll",
                    Arrays.<Type>asList(new Address(OperatorAddress),
                            new Bool(Boolean.parseBoolean(booleanvalue))),
                    Collections.<TypeReference<?>>emptyList());


            //Create RawTransaction transaction object
            String encodedFunction = FunctionEncoder.encode(function);
            RawTransaction rawTransaction = RawTransaction.createTransaction(nonce, gasPrice, gasLimit,
                    tokenAddress, encodedFunction);

            //Signature Transaction
            byte[] signMessage = TransactionEncoder.signMessage(rawTransaction, credentials);
            String hexValue = Numeric.toHexString(signMessage);
            //Send the transaction
            EthSendTransaction ethSendTransaction = web3.ethSendRawTransaction(hexValue).sendAsync().get();
            String hash = ethSendTransaction.getTransactionHash();
            if (hash != null) {
                return hash;
            }
            else
            {
                return "Failed";
            }
        }
        else
        {
            return "Failed";
        }

    }


    /** @notice Transfers the ownership of an NFT from one address to another address
     *  @dev This works identically to the other function with an extra data parameter,
     *  except this function just sets data to "".
     *  @param tokenAddress An address for whom to query the balance
     *  @param privatekey    Owner Private key.
     *  @param tokenid The identifier for an NFT
     *  @param receiverAddress The address to transfer to */

    public String safeTransferFrom(String tokenAddress, String privatekey, String receiverAddress, String tokenid) throws Exception {

        if(isWeb3jConnected()) {
            //Load the required documents for the transfer, with the private key
            //spender privatekey
            Credentials credentials = Credentials.create(privatekey);
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
            gasPrice = BigInteger.valueOf(3000000L);
            //BigInteger.valueOf(4300000L) If the transaction fails, it is probably a problem with the setting of the fee.
            BigInteger gasLimit = BigInteger.valueOf(3000000L);
            final org.web3j.abi.datatypes.Function function = new Function(
                    "safeTransferFrom",
                    Arrays.<Type>asList(new Address(credentials.getAddress()),
                            new Address(receiverAddress),
                            new Uint256(Long.parseLong(tokenid))),
                    Collections.<TypeReference<?>>emptyList());
            //Create RawTransaction transaction object
            String encodedFunction = FunctionEncoder.encode(function);
            RawTransaction rawTransaction = RawTransaction.createTransaction(nonce, gasPrice, gasLimit,
                    tokenAddress, encodedFunction);

            //Signature Transaction
            byte[] signMessage = TransactionEncoder.signMessage(rawTransaction, credentials);
            String hexValue = Numeric.toHexString(signMessage);
            //Send the transaction
            EthSendTransaction ethSendTransaction = web3.ethSendRawTransaction(hexValue).sendAsync().get();
            String hash = ethSendTransaction.getTransactionHash();
            if (hash != null) {
                return hash;
            }
            else
            {
                return "Failed";
            }
        }
        else
        {
            return "Failed";
        }
    }


    /** @notice Transfer ownership of an NFT -- THE CALLER IS RESPONSIBLE
     *  TO CONFIRM THAT `_to` IS CAPABLE OF RECEIVING NFTS OR ELSE
     *  THEY MAY BE PERMANENTLY LOST
     *  @dev Throws unless `msg.sender` is the current owner, an authorized
     *  operator, or the approved address for this NFT. Throws if `_from` is
     *  not the current owner. Throws if `_to` is the zero address. Throws if
     *  `_tokenId` is not a valid NFT.
     *  @param tokenAddress An address for whom to query the balance
     *  @param privatekey    Owner Private key.
     *  @param tokenid The identifier for an NFT
     *  @param receiverAddress The address to transfer to */

    public String transferfrom(String tokenAddress, String privatekey, String receiverAddress, String tokenid) throws Exception {
        if(isWeb3jConnected()) {
            //spender privatekey
            Credentials credentials = Credentials.create(privatekey);
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
            // gasPrice = ethGasPrice.getGasPrice();
            gasPrice = BigInteger.valueOf(3000000L);
            //BigInteger.valueOf(4300000L) If the transaction fails, it is probably a problem with the setting of the fee.
            BigInteger gasLimit = BigInteger.valueOf(3000000L);
            final Function function = new Function(
                    "transferFrom",
                    Arrays.<Type>asList(new Address(credentials.getAddress()),
                            new Address(receiverAddress),
                            new Uint256(Long.parseLong(tokenid))),
                    Collections.<TypeReference<?>>emptyList());


            //Create RawTransaction transaction object
            String encodedFunction = FunctionEncoder.encode(function);
            RawTransaction rawTransaction = RawTransaction.createTransaction(nonce, gasPrice, gasLimit,
                    tokenAddress, encodedFunction);

            //Signature Transaction
            byte[] signMessage = TransactionEncoder.signMessage(rawTransaction, credentials);
            String hexValue = Numeric.toHexString(signMessage);
            //Send the transaction
            EthSendTransaction ethSendTransaction = web3.ethSendRawTransaction(hexValue).sendAsync().get();
            String hash = ethSendTransaction.getTransactionHash();
            if (hash != null) {
                return hash;
            }
            else
            {
                return "Failed";
            }
        }
        else
        {
            return "Failed";
        }
    }

}
