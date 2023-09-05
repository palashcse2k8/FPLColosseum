package com.infotech.fplcolosseum.remote;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface APIServices {

    // for first Install  API
    @POST("RequestHandler/firstInstall")
    Call<ResponseBody> firstInstallApiCall(@Body String firstInstall);

    // for request key API
    @POST("RequestHandler/requestKey")
    Call<ResponseBody> requestKey(@Body String requestForKey);

    // For Login API
    //http://10.32.20.52:4000/SBLWalletBanking/v1/login
    @POST("RequestHandler/login")
//    Call<ResponseBody> sendLoginDataToApiForLogin(@Header("deviceId") String deviceId, @Body String loginDataToApiForLogin);
     Call<ResponseBody> sendLoginDataToApiForLogin(@Header("deviceId") String deviceId,@Header("source") String Source, @Header("version") String version , @Body String loginDataToApiForLogin);
    // FOR REGISTRATION API
   // http://10.10.80.174:8081/SBLWalletBanking/v1/walletrequest
    @POST("RequestHandler/v1/walletrequest")
    Call<ResponseBody> registrationAPICall(@Header("deviceId") String deviceId, @Body String registrationData);

   // URL: http://10.10.80.174:8080/SBLWalletBanking/v1/transactionrequest
    @POST("RequestHandler/v1/transactionrequest")
    Call<ResponseBody> addMoneyAccVerify(@Header("deviceId") String deviceId, @Body String acToWalletData);


    // URL: http://10.10.80.174:8080/SBLWalletBanking/v1/walletservice
    @POST("RequestHandler/v1/walletservice")
    Call<ResponseBody> beftnPayment(@Header("deviceId") String deviceId, @Body String acToWalletData);

    // URL: http://10.10.80.174:8080/SBLWalletBanking/v1/transactionrequest
    @POST("RequestHandler/v1/listFetch")
    Call<ResponseBody> bankAndDistrictData(@Header("deviceId") String deviceId, @Body String acToWalletData);

    @POST("RequestHandler/v1/accountinfo")
    Call<ResponseBody> checkBankBalance(@Header("deviceId") String deviceId, @Body String bankInfo);

    @POST("RequestHandler/v1/walletservice")
    Call<ResponseBody> walletservice(@Header("deviceId") String deviceId, @Body String walletservice);


    @POST("RequestHandler/v1/walletinfo")
    Call<ResponseBody> checkWalletBalance(@Header("deviceId") String deviceId,@Body String walletInfo);

    @POST("RequestHandler/v1/statementrequest")
    Call<ResponseBody> statementAPICall(@Header("deviceId") String deviceId, @Body String statementData);

    @POST("RequestHandler/v1/cardservice")
    Call<ResponseBody> getCardLimit(@Header("deviceId") String deviceId, @Body String cardLimitInfo);


    @POST("/RequestHandler/v1/walletservice")
    Call<ResponseBody> rechargeAPICall(@Header("deviceId") String deviceId, @Body String topUpRequest);

    @POST("/RequestHandler/v1/billservice")
    Call<ResponseBody> billAPICall(@Header("deviceId") String deviceId, @Body String billRequest);

    @POST("/RequestHandler/v1/listFetch")
    Call<ResponseBody> getLocations(@Header("deviceId") String deviceId, @Body String locationRequestData);

    @POST("RequestHandler/v1/getNotification")
    Call<ResponseBody> notificationHistoryAPICall(@Header("deviceId") String deviceId, @Body String statementData);

    @POST("RequestHandler/v1/walletinfo")
    Call<ResponseBody> cardListAPICall(@Header("deviceId") String deviceId, @Body String cardData);

    @POST("RequestHandler/v1/walletinfo")
    Call<ResponseBody> cardAddAPICall(@Header("deviceId") String deviceId, @Body String cardData);

    @POST("RequestHandler/v1/cardservice")
    Call<ResponseBody> cardDetailAPICall(@Header("deviceId") String deviceId, @Body String cardData);

    @POST("RequestHandler/v1/walletservice")
    Call<ResponseBody> cardPayBillAPICall(@Header("deviceId") String deviceId, @Body String cardData);



    @POST("RequestHandler/v1/IDTPService")
    Call<ResponseBody> invokeIdtpApi(@Header("deviceId") String deviceId, @Body String payload);



    @POST("RequestHandler/v1/GetMiscInfo1")
    Call<ResponseBody> getMiscInfo1(@Header("deviceId") String deviceId, @Body String payload);

    @POST("RequestHandler/v1/GetMiscInfo2")
    Call<ResponseBody> getMiscInfo2(@Header("deviceId") String deviceId, @Body String payload);


    @POST("RequestHandler/v1/qr-generate-sbl")
    Call<ResponseBody> getQrGenerateReq(@Header("deviceId") String deviceId, @Body String data);

    @POST("RequestHandler/v1/qr-parse")
    Call<ResponseBody> getQrParseResult(@Header("deviceId") String deviceId, @Body String qrData);

     @POST("RequestHandler/v1/getServiceCharge")
     Call<ResponseBody> qrPaymentChargeRequest(@Header("deviceId") String deviceId, @Body String payData);

    @POST("RequestHandler/v1/qr-payment")
    Call<ResponseBody> qrPaymentRequest(@Header("deviceId") String deviceId, @Body String payData);


    @POST("RequestHandler/v1/dpdcBill")
    Call<ResponseBody> dpdcBillRequest(@Header("deviceId") String deviceId, @Body String payData);


    @POST("/RequestHandler/v1/transactionprofilesetup")
    Call<ResponseBody> globalTp(@Header("deviceId") String deviceId, @Body String topUpRequest);

    @POST("/RequestHandler/v1/tpdetails")
    Call<ResponseBody> personalTp(@Header("deviceId") String deviceId, @Body String topUpRequest);



    // npsb
    @POST("/RequestHandler/v1/npsb")
    Call<ResponseBody> npsb(@Header("deviceId") String deviceId, @Body String topUpRequest);

    // mfs
    @POST("/RequestHandler/v1/nagad-deposit")
    Call<ResponseBody> nagaddeposit(@Header("deviceId") String deviceId, @Body String nagadBody);
 // mfs
    @POST("/RequestHandler/v1/getChargeInfo")
   Call<ResponseBody> getChargeInfo(@Header("deviceId") String deviceId, @Body String nagadBody);


    @POST("RequestHandler/v1/walletinfo")
    Call<ResponseBody> walletinfo(@Header("deviceId") String deviceId, @Body String payload);

    @POST("RequestHandler/v1/serviceinfo")
    Call<ResponseBody> serviceinfo(@Header("deviceId") String deviceId, @Body String payload);

    @POST("RequestHandler/v1/getServiceCharge")
    Call<ResponseBody> getServiceCharge(@Header("deviceId") String deviceId, @Body String payload);



    @POST("RequestHandler/v1/cheque-requisition")
    Call<ResponseBody> chequerequisition(@Header("deviceId") String deviceId, @Body String payload);
 ///v1/btcl/billQuery
   @POST("/RequestHandler/v1/btcl-billQuery")
   Call<ResponseBody> billQuery(@Header("deviceId") String deviceId, @Body String payload);


   @POST("/RequestHandler/v1/btcl-billPay")
   Call<ResponseBody> billPay(@Header("deviceId") String deviceId, @Body String payload);

   //Tax Return Submission API
   @POST("/RequestHandler/v1/fetchSubmittedReturn")
    Call<ResponseBody> fetchSubmittedReturn(@Header("deviceId") String deviceId, @Body String payload);

    @POST("RequestHandler/v1/uploadTaxReturnFile")
    Call<ResponseBody> submitReturnData(@Header("deviceId") String deviceId, @Body String payload);

    @POST("RequestHandler/v1/fetchFormCHistory")
    Call<ResponseBody> fetchFormCHistory(@Header("deviceId") String deviceId, @Body String payload);
    @POST("RequestHandler/v1/fetchFormCData")
    Call<ResponseBody> fetchFormCData(@Header("deviceId") String deviceId, @Body String payload);

    @POST("RequestHandler/v1/submitFormC")
    Call<ResponseBody> submitFormC(@Header("deviceId") String deviceId, @Body String payload);
}
