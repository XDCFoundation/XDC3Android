package com.XDCJava;

import android.util.Log;

import com.XDC.Example.Config721;
import com.XDCJava.Model.Token721DetailsResponse;
import com.XDCJava.callback.Token721DetailCallback;

import junit.framework.TestCase;

public class XDC721ClientTest extends TestCase {

    private static final String TAG =  "testXRC721" ;

    public void testGetTokenoinfo() {

        try {
            XDC721Client.getInstance().getTokenoinfo(Config721.TOKEN_ADDRESS, new Token721DetailCallback() {

                @Override
                public void success(Token721DetailsResponse tokenApiModel) {
                    assertEquals(tokenApiModel.getName(),"Demo721");
                    assertEquals(tokenApiModel.getSymbol(),"DMO");
                }

                @Override
                public void success(String message) {
                    
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
            String tokenUri = XDC721Client.getInstance().getTokenUri(Config721.TOKEN_ADDRESS,Config721.TOKEN_ID);
            Log.e(TAG, "testApprove: "+tokenUri);
            assertNotNull(tokenUri);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void testGetBalance() {

        try {
            String balance = XDC721Client.getInstance().getBalance(Config721.TOKEN_ADDRESS,Config721.OWNER_ADDRESS);
            Log.e(TAG, "testApprove: "+balance);
            assertNotNull(balance);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void testGettotalSupply() {

        try {
            String gettotalSupply = XDC721Client.getInstance().gettotalSupply(Config721.TOKEN_ADDRESS);
            Log.e(TAG, "testApprove: "+gettotalSupply);
            assertEquals(gettotalSupply,"1");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void testGettokenByIndex() {

        try {
            String gettokenByIndex = XDC721Client.getInstance().gettokenByIndex(Config721.TOKEN_ADDRESS,Config721.TOKEN_INDEX);
            Log.e(TAG, "testApprove: "+gettokenByIndex);
            assertEquals(gettokenByIndex,"25");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void testTokenOfOwnerByIndex() {

        try {
            String tokenOfOwnerByIndex = XDC721Client.getInstance().tokenOfOwnerByIndex(Config721.TOKEN_READ,Config721.TOKEN_OWNER_READ,Config721.TOKEN_INDEX);
            Log.e(TAG, "testApprove: "+tokenOfOwnerByIndex);
            assertNotNull(tokenOfOwnerByIndex);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void testGetOwnerof() {

        try {
            String ownerof = XDC721Client.getInstance().getOwnerof(Config721.TOKEN_ADDRESS,Config721.TOKEN_ID);
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
            String approve =   XDC721Client.instance.approve(Config721.TOKEN_READ, Config721.TOKEN_READ_PRIVATE_KEY, Config721.TOKEN_READ_ID, Config721.OWNER_ADDRESS);
            Log.e(TAG, "testApprove: "+approve);
            assertNotNull(approve);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void testGetApproved() {
        try {
            String approved = XDC721Client.getInstance().getApproved(Config721.TOKEN_ADDRESS,Config721.TOKEN_ID);
            Log.e(TAG, "testApprove: "+approved);
            assertNotNull(approved);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void testIsApprovedForAll() {
        try {
            boolean approvedForAll = XDC721Client.getInstance().isApprovedForAll(Config721.TOKEN_ADDRESS,Config721.OWNER_ADDRESS,Config721.OPERATOR_ADDRESS);
            Log.e(TAG, "testApprove: "+approvedForAll);
            assertNotNull(approvedForAll);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void testSetApprovalForAll() {
        try {
            String setApprovalForAll = XDC721Client.getInstance().setApprovalForAll(Config721.TOKEN_ADDRESS,Config721.OWNER_PRIVATE_KEY,Config721.OPERATOR_ADDRESS,"TRUE");
            Log.e(TAG, "testApprove: "+setApprovalForAll);
            assertNotNull(setApprovalForAll);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void testSafeTransferFrom() {
        try {
            String safeTransferFrom = XDC721Client.getInstance().safeTransferFrom(Config721.TOKEN_ADDRESS,Config721.OWNER_PRIVATE_KEY,Config721.SPENDER_ADDRESS,Config721.TOKEN_ID);
            Log.e(TAG, "testApprove: "+safeTransferFrom);
            assertNotNull(safeTransferFrom);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void testTransferfrom() {
        try {
            String transferfrom = XDC721Client.getInstance().transferfrom(Config721.TOKEN_ADDRESS,Config721.OWNER_PRIVATE_KEY,Config721.SPENDER_ADDRESS,Config721.TOKEN_ID);
            Log.e(TAG, "testApprove: "+transferfrom);
            assertNotNull(transferfrom);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}