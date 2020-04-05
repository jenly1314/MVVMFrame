package com.king.frame.mvvmframe.http.callback;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.HttpException;
import retrofit2.Response;
import timber.log.Timber;

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
public abstract class ApiCallback<T> implements Callback<T> {
    @Override
    public void onResponse(Call<T> call, Response<T> response) {
//        if(response.isSuccessful()){
            T result = response.body();
            Timber.d("Response:" + result);
            onResponse(call,result);
//        }else{
//            onError(call,new HttpException(response));
//        }
    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {
        Timber.w(t);
        onError(call,t);
    }

    public abstract void onResponse(Call<T> call,T result);

    public abstract void onError(Call<T> call, Throwable t);
}
