package xinfin.sdk.managers.api;


import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;
import xinfin.sdk.model.api.app.ApiTransferResponseModel;


public interface ApiService {


    @POST("/publicAPI")
    Call<ApiTransferResponseModel> transfer(@QueryMap Map<String, Object>  params);

}
