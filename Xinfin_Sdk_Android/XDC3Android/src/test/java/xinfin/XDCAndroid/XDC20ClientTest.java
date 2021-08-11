package xinfin.XDCAndroid;

import android.util.Log;

import junit.framework.TestCase;

import java.math.BigInteger;

import xinfin.XDCAndroid.Model.TokenDetailsResponse;
import xinfin.XDCAndroid.callback.TokenDetailCallback;

public class XDC20ClientTest extends TestCase {
    String TAG = "XDC20ClientTest";
    TokenDetailCallback tokenDetailCallback;

    public void testGetTokenoinfo() {
        try {
             XDC20Client.getInstance().getTokenoinfo("0xba9d6a36fbc194f5d1aa48a2892ae4bdf6939cb9", new TokenDetailCallback() {
                 @Override
                 public void success(TokenDetailsResponse tokenApiModel)
                 {

                     assertEquals(tokenApiModel.getName(),"SDK Testing");
                     assertEquals(tokenApiModel.getDecimal(),BigInteger.valueOf(18));
                     assertEquals(tokenApiModel.getSymbol(),"SDK");
//                     assertEquals(tokenApiModel.getTotalSupply(),"10000000000000000000000000000000000000000000000000000000000000000000");

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
            String allowance = XDC20Client.getInstance().getAllowance("0xba9d6a36fbc194f5d1aa48a2892ae4bdf6939cb9","0x6ffe09f9302a857fcb122296e3ab3bb80c45cbcd","0xd7813e7cfdf83d6fa3469d7411b52a50ed2b867f");
            Log.e(TAG, "testApprove: "+allowance);
            assertEquals(allowance,"1000000000000000000");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void testGetBalance() {
        try {
            String balance = XDC20Client.getInstance().getBalance("0xba9d6a36fbc194f5d1aa48a2892ae4bdf6939cb9","0x6ffe09f9302a857fcb122296e3ab3bb80c45cbcd");
            Log.e(TAG, "testApprove: "+balance);
            assertNotNull(balance);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    public void testTransferXdc() {
        try {
            String transferXdc = XDC20Client.getInstance().TransferXdc("0x07d2fb9a1f3a912000bbf9215ee0815a969b1d49e7e4c5ec94600ae2dfcfa4ce","0x6ffe09f9302a857fcb122296e3ab3bb80c45cbcd","0xd7813e7cfdf83d6fa3469d7411b52a50ed2b867f",BigInteger.valueOf(50000), Long.valueOf(30000),Long.valueOf(30000));
            Log.e(TAG, "testApprove: "+transferXdc);
            assertNotNull(transferXdc);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void testApproveXRC20Token() {
        try {
            String approveXRC20Token = XDC20Client.getInstance().approveXRC20Token("0xba9d6a36fbc194f5d1aa48a2892ae4bdf6939cb9","0x07d2fb9a1f3a912000bbf9215ee0815a969b1d49e7e4c5ec94600ae2dfcfa4ce","0xd7813e7cfdf83d6fa3469d7411b52a50ed2b867f","50");
            Log.e(TAG, "testApprove: "+approveXRC20Token);
            assertNotNull(approveXRC20Token);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void testTransferXRC20Token() {

        try {
            String transferXRC20Token = XDC20Client.getInstance().transferXRC20Token("0xba9d6a36fbc194f5d1aa48a2892ae4bdf6939cb9","0x07d2fb9a1f3a912000bbf9215ee0815a969b1d49e7e4c5ec94600ae2dfcfa4ce","0xd7813e7cfdf83d6fa3469d7411b52a50ed2b867f","50");
            Log.e(TAG, "testApprove: "+transferXRC20Token);
            assertNotNull(transferXRC20Token);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void testIncreaseAllownce() {
        try {
            String increaseAllownce = XDC20Client.getInstance().increaseAllownce("0x6ffe09f9302a857fcb122296e3ab3bb80c45cbcd","0xd7813e7cfdf83d6fa3469d7411b52a50ed2b867f","0x07d2fb9a1f3a912000bbf9215ee0815a969b1d49e7e4c5ec94600ae2dfcfa4ce","25","0xba9d6a36fbc194f5d1aa48a2892ae4bdf6939cb9");
            Log.e(TAG, "testApprove: "+increaseAllownce);
            assertNotNull(increaseAllownce);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void testDecreaseAllownce() {
        try {
            String decreaseAllownce = XDC20Client.getInstance().decreaseAllownce("0x6ffe09f9302a857fcb122296e3ab3bb80c45cbcd","0xd7813e7cfdf83d6fa3469d7411b52a50ed2b867f","0x07d2fb9a1f3a912000bbf9215ee0815a969b1d49e7e4c5ec94600ae2dfcfa4ce","20","0xba9d6a36fbc194f5d1aa48a2892ae4bdf6939cb9");
            Log.e(TAG, "testApprove: "+decreaseAllownce);
            assertNotNull(decreaseAllownce);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void testTransferfrom() {

        try {
            String transferfrom = XDC20Client.getInstance().transferfrom("0xd7813e7cfdf83d6fa3469d7411b52a50ed2b867f","0xedb472070566e072f3bdaa50cfa076e772135f33","0x8a32103448a851b2fed3e95d2b4fbaa5e564f3b8cc42eac0d3be15a96311f355","25","0xba9d6a36fbc194f5d1aa48a2892ae4bdf6939cb9");
            Log.e(TAG, "testApprove: "+transferfrom);
            assertNotNull(transferfrom);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}