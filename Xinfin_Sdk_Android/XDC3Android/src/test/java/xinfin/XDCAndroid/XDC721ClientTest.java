package xinfin.XDCAndroid;

import android.util.Log;

import junit.framework.TestCase;

import xinfin.XDCAndroid.Model.Token721DetailsResponse;
import xinfin.XDCAndroid.callback.Token721DetailCallback;

public class XDC721ClientTest extends TestCase {
    String TAG = "XDC721ClientTest";
    Token721DetailCallback token721DetailCallback;

    public void testGetTokenoinfo() {

        try {
            XDC721Client.getInstance().getTokenoinfo("0x5f07d8ca1f176ec49b738cfe309db9a9cf369c88", new Token721DetailCallback() {

                @Override
                public void success(Token721DetailsResponse tokenApiModel) {
                    assertEquals(tokenApiModel.getName(),"Demo721");
                    assertEquals(tokenApiModel.getSymbol(),"DMO");
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


    public void testGetTokenUri() {

        try {
            String tokenUri = XDC721Client.getInstance().getTokenUri("0x5f07d8ca1f176ec49b738cfe309db9a9cf369c88","25");
            Log.e(TAG, "testApprove: "+tokenUri);
            assertNotNull(tokenUri);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void testGetBalance() {

        try {
            String balance = XDC721Client.getInstance().getBalance("0x5f07d8ca1f176ec49b738cfe309db9a9cf369c88","0x7af2f691d78c51d0f0368a3c367634c9d76cfe7d");
            Log.e(TAG, "testApprove: "+balance);
            assertNotNull(balance);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void testGettotalSupply() {

        try {
            String gettotalSupply = XDC721Client.getInstance().gettotalSupply("0x5f07d8ca1f176ec49b738cfe309db9a9cf369c88");
            Log.e(TAG, "testApprove: "+gettotalSupply);
            assertEquals(gettotalSupply,"1");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void testGettokenByIndex() {

       try {
           String gettokenByIndex = XDC721Client.getInstance().gettokenByIndex("0x5f07d8ca1f176ec49b738cfe309db9a9cf369c88","0");
           Log.e(TAG, "testApprove: "+gettokenByIndex);
            assertEquals(gettokenByIndex,"25");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void testTokenOfOwnerByIndex() {

        try {
            String tokenOfOwnerByIndex = XDC721Client.getInstance().tokenOfOwnerByIndex("0xf6b4102b25756a33a90f8f192c74e53d22a9628e","0x517b6be05e5c50df6876906909c23ad130476cc7","0");
            Log.e(TAG, "testApprove: "+tokenOfOwnerByIndex);
            assertNotNull(tokenOfOwnerByIndex);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void testGetOwnerof() {

        try {
            String ownerof = XDC721Client.getInstance().getOwnerof("0x5f07d8ca1f176ec49b738cfe309db9a9cf369c88","25");
            Log.e(TAG, "testApprove: "+ownerof);
            assertNotNull(ownerof);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void testGetsupportInterface() {
    }

    public void testApprove() {
            try {
                String approve =   XDC721Client.instance.approve("0xf6b4102b25756a33a90f8f192c74e53d22a9628e", "0x7415687b87e1f1bcf55b26e0ddb086f9eb1d31cdc2059a296ce05de51b043258", "19", "0x7af2f691d78c51d0f0368a3c367634c9d76cfe7d");
                Log.e(TAG, "testApprove: "+approve);
                assertNotNull(approve);
            } catch (Exception e) {
                e.printStackTrace();
            }

    }

    public void testGetApproved() {
        try {
            String approved = XDC721Client.getInstance().getApproved("0x5f07d8ca1f176ec49b738cfe309db9a9cf369c88","25");
            Log.e(TAG, "testApprove: "+approved);
            assertNotNull(approved);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void testIsApprovedForAll() {
        try {
            boolean approvedForAll = XDC721Client.getInstance().isApprovedForAll("0x5f07d8ca1f176ec49b738cfe309db9a9cf369c88","0x7af2f691d78c51d0f0368a3c367634c9d76cfe7d","0x79dbbbe40993253aa18e930a39d3636b15866725");
            Log.e(TAG, "testApprove: "+approvedForAll);
            assertNotNull(approvedForAll);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void testSetApprovalForAll() {
        try {
            String setApprovalForAll = XDC721Client.getInstance().setApprovalForAll("0x5f07d8ca1f176ec49b738cfe309db9a9cf369c88","0x78e5eff6026eeeee03401909c51a1b3bd3b2071dd333632bb4d54c279dc5f1ea","0x79dbbbe40993253aa18e930a39d3636b15866725","TRUE");
            Log.e(TAG, "testApprove: "+setApprovalForAll);
            assertNotNull(setApprovalForAll);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void testSafeTransferFrom() {
        try {
            String safeTransferFrom = XDC721Client.getInstance().safeTransferFrom("0x5f07d8ca1f176ec49b738cfe309db9a9cf369c88","0x78e5eff6026eeeee03401909c51a1b3bd3b2071dd333632bb4d54c279dc5f1ea","0x79dbbbe40993253aa18e930a39d3636b15866725","25");
            Log.e(TAG, "testApprove: "+safeTransferFrom);
            assertNotNull(safeTransferFrom);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void testTransferfrom() {
        try {
            String transferfrom = XDC721Client.getInstance().transferfrom("0x5f07d8ca1f176ec49b738cfe309db9a9cf369c88","0x78e5eff6026eeeee03401909c51a1b3bd3b2071dd333632bb4d54c279dc5f1ea","0x79dbbbe40993253aa18e930a39d3636b15866725","25");
            Log.e(TAG, "testApprove: "+transferfrom);
            assertNotNull(transferfrom);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}