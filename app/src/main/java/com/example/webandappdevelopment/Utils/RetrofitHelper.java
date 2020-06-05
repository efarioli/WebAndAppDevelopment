package com.example.webandappdevelopment.Utils;

import android.content.Context;
import android.os.Build;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;


import com.example.webandappdevelopment.R;
import com.example.webandappdevelopment.RestFulWebServiceApi;
import com.muddzdev.styleabletoast.StyleableToast;

import java.util.function.Consumer;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitHelper <T>{
    private static final String TAG = "MainActivity";

    public  static RestFulWebServiceApi getRetrofitSepUP (){
        Retrofit retrofit = new Retrofit.Builder()
                //.baseUrl("https://192.168.0.16:8080/LibraryDemo/webresources/generic/")
                .baseUrl("http://10.0.2.2:8080/LibraryDemo/webresources/generic/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RestFulWebServiceApi restFulWebServiceApi = retrofit.create(RestFulWebServiceApi.class);
        return restFulWebServiceApi;
    }

    public static <T> void   makeCall (@Nullable Consumer<T> action, Call<T> call, @Nullable Integer position, Context context) {
        call.enqueue(new Callback<T>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(Call<T> call, Response<T> response) {
                if(!response.isSuccessful()){
                    //Toast.makeText(context.getApplicationContext(), "code: " + response.code() , Toast.LENGTH_SHORT).show();
                    StyleableToast.makeText(context.getApplicationContext(), "ERROR: check your network...", R.style.toast_error).show();

                    return;
                }
                //Only if response is successful
                action.accept(response.body());

            }

            @Override
            public void onFailure(Call<T> call, Throwable t) {
                //Toast.makeText(context.getApplicationContext(), "Failure: " + t.getMessage() , Toast.LENGTH_SHORT).show();
                StyleableToast.makeText(context.getApplicationContext(), "ERROR: check your network...", R.style.toast_error).show();


            }
        });
    }
}
