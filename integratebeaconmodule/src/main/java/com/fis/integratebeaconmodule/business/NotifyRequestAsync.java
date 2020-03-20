//package com.fis.integratebeaconmodule.business;
//
//import android.content.Context;
//import android.util.Log;
//
//import com.fis.integratebeaconmodule.configs.Constant;
//import com.fis.integratebeaconmodule.entity.BeaconsNotificationEntity;
//import com.fis.integratebeaconmodule.entity.NotificationParamEntity;
//import com.fis.integratebeaconmodule.entity.ResponseApiEntity;
//import com.fis.integratebeaconmodule.services.APIHepperService;
//import com.fis.integratebeaconmodule.services.BeaconSharedPreferences;
//import com.fis.integratebeaconmodule.utils.StringUtils;
//import com.loopj.android.http.JsonHttpResponseHandler;
//import com.loopj.android.http.RequestParams;
//
//import org.json.JSONObject;
//
//import java.util.Date;
//
//import cz.msebera.android.httpclient.Header;
//
//public class NotifyRequestAsync {
//    private final Context context;
//    private final BeaconSharedPreferences beaconSharedPreferences;
//
//    public NotifyRequestAsync(Context context) {
//        this.context = context;
//        this.beaconSharedPreferences = new BeaconSharedPreferences(context);
//    }
//
//    public void getNotification(final NotificationParamEntity notificationParamEntity, final NotifyResponse notifyResponse) {
//        try {
//            Long expiredCallApi = beaconSharedPreferences.getExpiredCallApi();
//            System.out.println("\n\n--------------------------------------------before handlerNewBeacon --------------------------------------\n\n");
//
//            if (new Date().getTime() > expiredCallApi) {
//                APIHepperService service = APIHepperService.getInstance();
//                RequestParams params = new RequestParams();
//
//                params.put("phoneNumber", notificationParamEntity.getPhoneNumber());
//                params.put("beaconCode", notificationParamEntity.getBeaconCode());
//                System.out.println("\n\n-------------------------------------------- affter handlerNewBeacon --------------------------------------\n\n");
//
//                service.getAsync("CustomerIdentify/BeaconsNotification/getNotificationByCustomer", params, new JsonHttpResponseHandler() {
//                    @Override
//                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
//                        Log.i(Constant.LOG.RESPONSE_API, "url: " + "CustomerIdentify/BeaconsNotification/getNotificationByCustomer");
//                        Log.i(Constant.LOG.RESPONSE_API, "statusCode" + statusCode);
//                        Log.i(Constant.LOG.RESPONSE_API, "headers" + headers.toString());
//                        Log.i(Constant.LOG.RESPONSE_API, "body" + response.toString());
//
//                        try {
//                            if (statusCode == 200) {
//
//                                if (response.get("status").equals(ResponseApiEntity.RESPONSE_STATUS.SUCCESS)) {
//                                    BeaconsNotificationEntity beaconsNotificationEntity = (BeaconsNotificationEntity) StringUtils.jsonToObject(response.get("data").toString(), BeaconsNotificationEntity.class);
//
//                                    notifyResponse.onSuccess(beaconsNotificationEntity);
//
//                                } else {
//                                    throw new Exception("Server exception. message: " + response.get("message"));
//                                }
//                            } else {
//                                throw new Exception("Server exception. status code error");
//                            }
//                        } catch (Exception e) {
//                            Log.e(Constant.LOG.RESPONSE_API, "Exception!" + e.getMessage());
//                            notifyResponse.onError(e.getMessage());
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
//
//                        Log.e(Constant.LOG.RESPONSE_API, "url: " + "CustomerIdentify/BeaconsNotification/getNotificationByCustomer");
//                        Log.e(Constant.LOG.RESPONSE_API, "onFailure: statusCode -> " + statusCode);
//                        Log.e(Constant.LOG.RESPONSE_API, "onFailure: headers ->" + headers.toString());
//                        Log.e(Constant.LOG.RESPONSE_API, "onFailure: errorResponse ->" + errorResponse.toString());
//
//                        notifyResponse.onError(errorResponse.toString());
//                    }
//
//                    @Override
//                    public void onRetry(int retryNo) {
//                        System.out.println("onRetry - retryNo: " + retryNo);
//                    }
//                });
//            }
//
//            beaconSharedPreferences.setExpiredCallApi(Constant.EXPIRED_CALL_API);
//        } catch (Exception e) {
//            Log.e(Constant.LOG.HANDLER_NEW_BEACON, e.getMessage());
//        }
//    }
//}
