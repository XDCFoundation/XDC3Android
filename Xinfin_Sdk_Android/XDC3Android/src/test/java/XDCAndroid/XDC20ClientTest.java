package XDCAndroid;

import android.util.Log;

import junit.framework.TestCase;

import java.math.BigInteger;

import com.XDCAndroid.ConfigXDC20;
import com.XDCAndroid.Model.TokenDetailsResponse;
import com.XDCAndroid.XDC20Client;
import com.XDCAndroid.callback.TokenDetailCallback;

public class XDC20ClientTest extends TestCase {
    String TAG = "XDC20ClientTest";
    TokenDetailCallback tokenDetailCallback;

    public void testGetTokenoinfo() {
        try {
             XDC20Client.getInstance().getTokenoinfo(ConfigXDC20.TOKEN_ADDRESS, new TokenDetailCallback() {
                 @Override
                 public void success(TokenDetailsResponse tokenApiModel)
                 {

                     assertEquals(tokenApiModel.getName(),"SDK Testing");
                     assertEquals(tokenApiModel.getDecimal(),BigInteger.valueOf(18));
                     assertEquals(tokenApiModel.getSymbol(),"SDK");

                 }

                 @Override
                 public void failure(Throwable t) {

                 }

                 @Override
                 public void failure(String message) {

                 }
             });


        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public void testGetAllowance() {

        try {
            String allowance = XDC20Client.getInstance().getAllowance(ConfigXDC20.TOKEN_ADDRESS,ConfigXDC20.OWNER_ADDRESS,ConfigXDC20.SPENDER_ADDRESS);
            Log.e(TAG, "testApprove: "+allowance);
            assertNotNull(allowance);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void testGetBalance() {
        try {
            String balance = XDC20Client.getInstance().getBalance(ConfigXDC20.TOKEN_ADDRESS,ConfigXDC20.OWNER_ADDRESS);
            Log.e(TAG, "testApprove: "+balance);
            assertNotNull(balance);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    public void testTransferXdc() {
        try {
            String transferXdc = XDC20Client.getInstance().TransferXdc(ConfigXDC20.OWNER_PRIVATE_KEY,ConfigXDC20.OWNER_ADDRESS,ConfigXDC20.SPENDER_ADDRESS,BigInteger.valueOf(50000), Long.valueOf(30000),Long.valueOf(30000));
            Log.e(TAG, "testApprove: "+transferXdc);
            assertNotNull(transferXdc);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void testApproveXRC20Token() {
        try {
            String approveXRC20Token = XDC20Client.getInstance().approveXRC20Token(ConfigXDC20.TOKEN_ADDRESS,ConfigXDC20.OWNER_PRIVATE_KEY,ConfigXDC20.SPENDER_ADDRESS,ConfigXDC20.VALUE);
            Log.e(TAG, "testApprove: "+approveXRC20Token);
            assertNotNull(approveXRC20Token);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void testTransferXRC20Token() {

        try {
            String transferXRC20Token = XDC20Client.getInstance().transferXRC20Token(ConfigXDC20.TOKEN_ADDRESS,ConfigXDC20.OWNER_PRIVATE_KEY,ConfigXDC20.SPENDER_ADDRESS,ConfigXDC20.VALUE);
            Log.e(TAG, "testApprove: "+transferXRC20Token);
            assertNotNull(transferXRC20Token);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void testIncreaseAllownce() {
        try {
            String increaseAllownce = XDC20Client.getInstance().increaseAllownce(ConfigXDC20.OWNER_ADDRESS,ConfigXDC20.SPENDER_ADDRESS,ConfigXDC20.OWNER_PRIVATE_KEY,ConfigXDC20.VALUE,ConfigXDC20.TOKEN_ADDRESS);
            Log.e(TAG, "testApprove: "+increaseAllownce);
            assertNotNull(increaseAllownce);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void testDecreaseAllownce() {
        try {
            String decreaseAllownce = XDC20Client.getInstance().decreaseAllownce(ConfigXDC20.OWNER_ADDRESS,ConfigXDC20.SPENDER_ADDRESS,ConfigXDC20.OWNER_PRIVATE_KEY,ConfigXDC20.VALUE,ConfigXDC20.TOKEN_ADDRESS);
            Log.e(TAG, "testApprove: "+decreaseAllownce);
            assertNotNull(decreaseAllownce);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void testTransferfrom() {

        try {
            String transferfrom = XDC20Client.getInstance().transferfrom(ConfigXDC20.SPENDER_ADDRESS,ConfigXDC20.TRANSFER_FROM_ADDRESS,ConfigXDC20.SPENDER_PRIVATE_KEY,ConfigXDC20.VALUE,ConfigXDC20.TOKEN_ADDRESS);
            Log.e(TAG, "testApprove: "+transferfrom);
            assertNotNull(transferfrom);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}