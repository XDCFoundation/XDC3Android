package xinfin.sdk.transfer.view;


import java.util.List;

import xinfin.sdk.model.api.ResultDataResponseModel;
import xinfin.sdk.model.api.app.ApiTransferResponseModel;

public interface ITransferView
{
    void onTransferFailure(String failure);

    void onTransferSuccess(List<ResultDataResponseModel> getTransfer);

}
