package com.idt.stackexchange;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

public class Api {
    public static Request sendRequest(String url){
        return new Request.Builder().url(url).build();
    }

    public static Response getResponse(Request request) throws IOException {
        OkHttpClient client = new OkHttpClient();
        Call call = client.newCall(request);
        Response response = call.execute();

        return response;
    }
}