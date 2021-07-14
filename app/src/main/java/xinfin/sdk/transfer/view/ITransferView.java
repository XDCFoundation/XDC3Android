package xinfin.sdk.transfer.view;


import xinfin.sdk.model.api.app.ApiTransferResponseModel;

public interface ITransferView
{
    void onTransferFailure(String failure);

    void onTransferSuccess(ApiTransferResponseModel apiTransferResponseModel);

}
