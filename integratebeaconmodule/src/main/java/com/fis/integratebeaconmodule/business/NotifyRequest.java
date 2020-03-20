package com.fis.integratebeaconmodule.business;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Looper;
import android.util.Log;

import com.fis.integratebeaconmodule.configs.Constant;
import com.fis.integratebeaconmodule.entity.BeaconsNotificationEntity;
import com.fis.integratebeaconmodule.entity.NotificationParamEntity;
import com.fis.integratebeaconmodule.entity.ResponseApiEntity;
import com.fis.integratebeaconmodule.services.APIHepperService;
import com.fis.integratebeaconmodule.services.BeaconSharedPreferences;
import com.fis.integratebeaconmodule.utils.StringUtils;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import cz.msebera.android.httpclient.Header;

import androidx.core.app.NotificationCompat.Builder;

public class NotifyRequest extends AsyncTask<NotificationParamEntity, Void, List<Builder>> {

    private final Context context;
    private final BeaconSharedPreferences beaconSharedPreferences;

    public NotifyRequest(final Context context) {
        this.context = context;
        beaconSharedPreferences = new BeaconSharedPreferences(context);
    }

    @Override
    protected List<Builder> doInBackground(NotificationParamEntity... notificationParamEntities) {
        return handlerNewBeacon(notificationParamEntities);
    }

    private List<Builder> handlerNewBeacon(NotificationParamEntity[] notificationParamEntities) {
        final ArrayList output = new ArrayList();

        try {
            Long expiredCallApi = beaconSharedPreferences.getExpiredCallApi();

            if (new Date().getTime() > expiredCallApi) {

                for (NotificationParamEntity notificationParamEntity : notificationParamEntities) {
                    System.out.println("\n\n--------------------------------------------before handlerNewBeacon --------------------------------------\n\n");

                    RequestParams params = new RequestParams();
                    params.put("phoneNumber", notificationParamEntity.getPhoneNumber());
                    params.put("beaconCode", notificationParamEntity.getBeaconCode());

                    APIHepperService service = APIHepperService.getInstance();
                    System.out.println("\n\n-------------------------------------------- affter handlerNewBeacon --------------------------------------\n\n");

                    Looper.prepare();
                    service.get("CustomerIdentify/BeaconsNotification/getNotificationByCustomer", params, new JsonHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                            Log.i(Constant.LOG.RESPONSE_API, "url: " + "CustomerIdentify/BeaconsNotification/getNotificationByCustomer");
                            Log.i(Constant.LOG.RESPONSE_API, "statusCode" + statusCode);
                            Log.i(Constant.LOG.RESPONSE_API, "headers" + Arrays.toString(headers));
                            Log.i(Constant.LOG.RESPONSE_API, "body" + response.toString());

                            try {
                                if (statusCode == 200) {

                                    if (response.get("status").equals(ResponseApiEntity.RESPONSE_STATUS.SUCCESS)) {
                                        List<BeaconsNotificationEntity> lstNoti = StringUtils.jsonToList(response.get("data").toString(), BeaconsNotificationEntity.class);

                                        if (null != lstNoti && lstNoti.size() > 0) {

                                            BeaconNotification noti = new BeaconNotification(context);
                                            List<Builder> lstBuilder = noti.create(lstNoti);
                                            output.addAll(lstBuilder);
                                        }

                                    } else {
                                        throw new Exception("Server exception. message: " + response.get("message"));
                                    }
                                } else {
                                    throw new Exception("Server exception. status code error");
                                }
                            } catch (Exception e) {
                                Log.e(Constant.LOG.RESPONSE_API, "Exception!" + e.getMessage());
                            }
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                            Log.e(Constant.LOG.RESPONSE_API, "url: " + "CustomerIdentify/BeaconsNotification/getNotificationByCustomer");
                            Log.e(Constant.LOG.RESPONSE_API, "onFailure: statusCode -> " + statusCode);
                            Log.e(Constant.LOG.RESPONSE_API, "onFailure: headers ->" + Arrays.toString(headers));
                            Log.e(Constant.LOG.RESPONSE_API, "onFailure: errorResponse ->" + errorResponse.toString());
                        }

                        @Override
                        public void onRetry(int retryNo) {
                            System.out.println("onRetry - retryNo: " + retryNo);
                        }
                    });
                }
            }

            beaconSharedPreferences.setExpiredCallApi(Constant.EXPIRED_CALL_API);
        } catch (Exception e) {
            Log.e(Constant.LOG.HANDLER_NEW_BEACON, e.getMessage() != null ? e.getMessage() : "");
        }

        return output;
    }

    @Override
    protected void onPostExecute(List<Builder> lstBuilder) {
        super.onPostExecute(lstBuilder);

        for (Builder item : lstBuilder) {
            BeaconNotification.showNotification(item, context);
        }
    }

}
