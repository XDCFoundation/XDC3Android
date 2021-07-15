package xinfin.sdk.managers.api;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import xinfin.sdk.constants.AppConstants;
import xinfin.sdk.constants.EventConstants;

import xinfin.sdk.model.api.app.ApiTransferResponseModel;
import xinfin.sdk.model.api.app.EventModel;

import xinfin.sdk.utils.AppUtility;


public class TransferApiManager {
    private static TransferApiManager instance;
    private static ApiService apiService;
    private static ProgressDialog progress;
    private static Dialog customDialog;

    private TransferApiManager() {

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder()
                .retryOnConnectionFailure(true);


       httpClient.readTimeout(0,TimeUnit.HOURS)
               .connectTimeout(0,TimeUnit.HOURS)
               .writeTimeout(0,TimeUnit.HOURS)
               .build();

        Retrofit mRetrofit = new Retrofit.Builder()
                .baseUrl(AppConstants.TRANSFER_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build();
        apiService = mRetrofit.create(ApiService.class);

    }
    public static TransferApiManager getInstance() {
        if (instance == null)
            instance = new TransferApiManager();
        return instance;
    }

    public static synchronized void init() {
        if (instance == null)
            instance = new TransferApiManager();
    }

    public static void updateDialogMessage(String text) {
        if (AppUtility.isEmpty(text) || progress == null || !progress.isShowing())
            return;
        progress.setMessage(text);
    }

    private void onAPIErrorResponse(Response<?> response, Context context) {
        AppUtility.hideDialog();
        ResponseBody responseBody = response.errorBody();
        try {
            AppUtility.showAlert(context, responseBody.string());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void transferApi(final EventModel eventModel){
        Call<ApiTransferResponseModel> call = apiService.transfer((Map<String, Object>) eventModel.requestObj);

        call.enqueue(new Callback<ApiTransferResponseModel>(){
            @Override
            public void onResponse(Call<ApiTransferResponseModel> call, Response<ApiTransferResponseModel> response) {
                AppUtility.hideDialog();
                ApiTransferResponseModel responseModel = response.body();

                if(responseModel == null || !response.isSuccessful())
                {
                    EventBus.getDefault().post(new EventModel(null, null, EventConstants.TRANSFER_LIST_FAILURE, eventModel.context));
                }
                else{
                    EventBus.getDefault().post(new EventModel(null, responseModel, EventConstants.TRANSFER_LIST_SUCCESS, eventModel.context));
                }


            }

            @Override
            public void onFailure(Call<ApiTransferResponseModel> call, Throwable t) {
                AppUtility.hideDialog();
                EventBus.getDefault().post(new EventModel(null, null, EventConstants.TRANSFER_LIST_FAILURE, eventModel.context));

            }
        });
    }

    public static void showCustomDialog(Context context) {
        if (context == null)
            return;
        if (customDialog != null && customDialog.isShowing())
            return;
        customDialog = new Dialog(context);
        customDialog.setCancelable(false);
        customDialog.show();
    }

    public static void hideCustomDialog() {
        try {
            if (customDialog != null && customDialog.isShowing())
                customDialog.dismiss();
        } catch (Exception e) {
            Log.d(AppConstants.TAG, "hideDialog " + e.getLocalizedMessage());
        }
    }
}
