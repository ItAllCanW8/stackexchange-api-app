package com.idt;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

public class App {
    private static final String API_URL = "https://api.stackexchange.com/2.2/";
    private static final String API_KEY = "ig8aZPv5JnWU8Tfakj8zVQ((";
    private static final String REQ_METHOD = "answers";
    private static final String REQ_SITE = "stackoverflow";
    private static final int    REQ_PAGE_SIZE = 10;
    private static final String REQ_SORT = "creation";
    private static final String REQ_SORT_ORDER = "desc";


    public static void main( String[] args ) throws IOException {
        Request request = new Request.Builder()
                .url(API_URL + REQ_METHOD + "?"
                        + "site=" + REQ_SITE
                        + "&sort=" + REQ_SORT
                        + "&order=" + REQ_SORT_ORDER
                        + "&pagesize=" + REQ_PAGE_SIZE
                        + "&key=" + API_KEY)
                .build();

        OkHttpClient client = new OkHttpClient();
        Call call = client.newCall(request);
        Response response = call.execute();

        System.out.println(response.body().string());
    }
}