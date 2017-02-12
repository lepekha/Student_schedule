package ruslep.student_schedule.architecture.other.network;

import android.content.Context;

import org.androidannotations.annotations.EBean;


import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import ruslep.student_schedule.architecture.other.Const;

/**
 * Created by Ruslan on 12.08.2016.
 */
@EBean(scope = EBean.Scope.Singleton)
public class APIhelper {

    OkHttpClient provideHttpClient(){
                 return new OkHttpClient
                         .Builder()
                         .connectTimeout(30, TimeUnit.SECONDS)
                         .writeTimeout(30, TimeUnit.SECONDS)
                         .readTimeout(30, TimeUnit.SECONDS)
                         .addInterceptor(new Interceptor() {
                                 @Override
                                 public Response intercept(Chain chain) throws IOException {
                                         Request request = chain.request();
                                                 request = request.newBuilder().header("Accept", "application/json").build();
                                         return chain.proceed(request);
                                     }
                             })
                         .build();
             }




    public API getService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Const.BASE_URL)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(provideHttpClient())
                .build();

        return retrofit.create(API.class);
    }
}
