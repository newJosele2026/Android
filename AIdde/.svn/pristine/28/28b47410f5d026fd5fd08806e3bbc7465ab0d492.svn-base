package com.slt.dependenciajudicial.requests.settings;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Sergio on 25/10/2017.
 */

public class RetrofitClient {

    private static Retrofit retrofit = null;

    public static Retrofit getClient(String baseUrl) {

        if (retrofit == null) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();//setting Logs  retrofit http Request Response
            logging.setLevel(HttpLoggingInterceptor.Level.HEADERS);//setting Logs retrofit http request Response

            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .readTimeout(ApiUtils.READ_TIME_OUT, TimeUnit.SECONDS)
                    .addInterceptor(logging)//add interceptor for logs.
                    .connectTimeout(ApiUtils.CONNECT_TIME_OUT, TimeUnit.SECONDS)
                    .build();
            retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return retrofit;
    }


}
