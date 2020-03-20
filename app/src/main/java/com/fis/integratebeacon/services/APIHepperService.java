package com.fis.integratebeacon.services;

import com.fis.integratebeacon.configs.Constant;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.ResponseHandlerInterface;
import com.loopj.android.http.SyncHttpClient;


public class APIHepperService {

    private static SyncHttpClient client = new SyncHttpClient();
    private static AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
    private static APIHepperService instance = null;

    public static APIHepperService getInstance() {
        if (instance == null) {
            instance = new APIHepperService();
        }
        return instance;
    }

    private APIHepperService() {
        client.setConnectTimeout(30000);
        client.setResponseTimeout(30000);
        client.addHeader("Accept", "application/json");
        client.addHeader("Content-type", "application/json;charset=utf-8");
        client.addHeader("Authorization", "key=" + Constant.TOKEN_KEY);

        asyncHttpClient.setConnectTimeout(60000);
        asyncHttpClient.setResponseTimeout(60000);
        asyncHttpClient.addHeader("Accept", "application/json");
        asyncHttpClient.addHeader("Content-type", "application/json;charset=utf-8");
        asyncHttpClient.addHeader("Authorization", "key=" + Constant.TOKEN_KEY);
    }

    public static void get(String url, RequestParams params, ResponseHandlerInterface responseHandler) {
        client.get(getAbsoluteUrl(url), params, responseHandler);
    }

    public static void post(String url, RequestParams params, ResponseHandlerInterface responseHandler) {
        client.post(getAbsoluteUrl(url), params, responseHandler);
    }

    public static void put(String url, RequestParams params, ResponseHandlerInterface responseHandler) {
        client.put(getAbsoluteUrl(url), params, responseHandler);
    }

    public static void delete(String url, RequestParams params, ResponseHandlerInterface responseHandler) {
        client.delete(getAbsoluteUrl(url), responseHandler);
    }

    private static String getAbsoluteUrl(String relativeUrl) {
        return Constant.API_URL + relativeUrl;
    }

    public static void getAsync(String url, RequestParams params, ResponseHandlerInterface responseHandler) {
        asyncHttpClient.get(getAbsoluteUrl(url), params, responseHandler);
    }

    public static void postAsync(String url, RequestParams params, ResponseHandlerInterface responseHandler) {
        asyncHttpClient.post(getAbsoluteUrl(url), params, responseHandler);
    }

    public static void putAsync(String url, RequestParams params, ResponseHandlerInterface responseHandler) {
        asyncHttpClient.put(getAbsoluteUrl(url), params, responseHandler);
    }

    public static void deleteAsync(String url, RequestParams params, ResponseHandlerInterface responseHandler) {
        asyncHttpClient.delete(getAbsoluteUrl(url), responseHandler);
    }
}
