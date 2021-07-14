package xinfin.sdk.transfer.presenter;

import android.content.Context;


import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.Map;

import xinfin.sdk.constants.EventConstants;
import xinfin.sdk.managers.api.TransferApiManager;


import xinfin.sdk.model.api.app.ApiTransferResponseModel;
import xinfin.sdk.model.api.app.EventModel;
import xinfin.sdk.transfer.view.ITransferView;


public class TransferPresenter implements ITransferNowPresenter {

    private ITransferView iTransferView;


    public TransferPresenter(ITransferView iTransferView) {
        this.iTransferView = iTransferView;
        EventBus.getDefault().register(this);
    }


    @Override
    public void TransferApi(Context context, Map<String, String> transferListRequestObj) {
        TransferApiManager.getInstance().transferApi(new EventModel(transferListRequestObj, null, null, context));
    }


    @Subscribe
    public void onMessageEvent(EventModel eventModel) {
        if (eventModel == null)
            return;
        switch (eventModel.eventName) {
            case EventConstants.TRANSFER_LIST_FAILURE:
                onAuctionBidNowFailure();
                break;
            case EventConstants.TRANSFER_LIST_SUCCESS:
                onAuctionBidnowSuccess(eventModel);
                break;



        }
    }

    private void onAuctionBidNowFailure() {
        iTransferView.onTransferFailure(EventConstants.FAILURE);
    }

    private void onAuctionBidnowSuccess(EventModel eventModel) {

        if(eventModel.responseObj == null){
            return;
        }

        ApiTransferResponseModel updateResponseModel = (ApiTransferResponseModel) eventModel.responseObj;

        iTransferView.onTransferSuccess(updateResponseModel);

    }



}
