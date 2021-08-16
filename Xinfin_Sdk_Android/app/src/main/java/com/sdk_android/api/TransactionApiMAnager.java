package com.sdk_android.api;
//
//import android.app.Dialog;
//import android.app.ProgressDialog;
//import android.content.Context;
//import android.util.Log;
//import java.io.IOException;
//import java.util.Map;
//import java.util.concurrent.TimeUnit;
//
//import okhttp3.OkHttpClient;
//import okhttp3.Response;
//import okhttp3.ResponseBody;
//import retrofit2.Retrofit;
//import retrofit2.converter.gson.GsonConverterFactory;
//import xinfin.sdk.constants.AppConstants;
//import xinfin.sdk.model.api.ApiTransactionResponseModel;
//
//
//public class TransactionApiMAnager {
//    private static TransactionApiMAnager instance;
//    private static ApiService apiService;
//    private static ProgressDialog progress;
//    private static Dialog customDialog;
//
//    private TransactionApiMAnager() {
//
//        OkHttpClient.Builder httpClient = new OkHttpClient.Builder()
//                .retryOnConnectionFailure(true);
//
//
//       httpClient.readTimeout(0,TimeUnit.HOURS)
//               .connectTimeout(0,TimeUnit.HOURS)
//               .writeTimeout(0,TimeUnit.HOURS)
//               .build();
//
//        Retrofit mRetrofit = new Retrofit.Builder()
//                .baseUrl(AppConstants.AUCTION_BASE_URL)
//                .addConverterFactory(GsonConverterFactory.create())
//                .client(httpClient.build())
//                .build();
//        apiService = mRetrofit.create(ApiService.class);
//
//    }
//    public static TransactionApiMAnager getInstance() {
//        if (instance == null)
//            instance = new TransactionApiMAnager();
//        return instance;
//    }
//
//    public static synchronized void init() {
//        if (instance == null)
//            instance = new TransactionApiMAnager();
//    }
//
//    public static void updateDialogMessage(String text) {
//        if (AppUtility.isEmpty(text) || progress == null || !progress.isShowing())
//            return;
//        progress.setMessage(text);
//    }
//
//    private void onAPIErrorResponse(Response<?> response, Context context) {
//        AppUtility.hideDialog();
//        ResponseBody responseBody = response.errorBody();
//        try {
//           AppUtility.showAlert(context, responseBody.string());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//
//    public void listAuctionApi(final EventModel eventModel){
//        Call<ApiTransactionResponseModel> call = apiService.sendTransaction((Map<String, Object>) eventModel.requestObj);
//        Log.e("call",call.request().url()+".."+call.request().body().toString()+"...."+eventModel.requestObj.toString());
//        call.enqueue(new Callback<ApiTransactionResponseModel>(){
//            @Override
//            public void onResponse(Call<ApiTransactionResponseModel> call, Response<ApiTransactionResponseModel> response) {
//                AppUtility.hideDialog();
//                ApiTransactionResponseModel responseModel = response.body();
//
//                if(responseModel == null || !responseModel.isSuccess()){
//                    EventBus.getDefault().post(new EventModel(null, null, EventConstants.AUCTION_LIST_FAILURE, eventModel.context));
//                }
//                else{
//                    EventBus.getDefault().post(new EventModel(null, responseModel, EventConstants.AUCTION_LIST_SUCCESS, eventModel.context));
//                }
//
//
//            }
//
//            @Override
//            public void onFailure(Call<ApiTransactionResponseModel> call, Throwable t) {
//                AppUtility.hideDialog();
//                EventBus.getDefault().post(new EventModel(null, null, EventConstants.AUCTION_LIST_FAILURE, eventModel.context));
//
//            }
//        });
//    }
//
//    public static void showCustomDialog(Context context) {
//        if (context == null)
//            return;
//        if (customDialog != null && customDialog.isShowing())
//            return;
//        customDialog = new Dialog(context);
//        customDialog.setCancelable(false);
//        customDialog.show();
//    }
//
//    public static void hideCustomDialog() {
//        try {
//            if (customDialog != null && customDialog.isShowing())
//                customDialog.dismiss();
//        } catch (Exception e) {
//            Log.d(AppConstants.TAG, "hideDialog " + e.getLocalizedMessage());
//        }
//    }
//}
