package br.ufpb.dcx.apps4society.educapimanager.helper;

import android.content.Context;

import java.util.concurrent.TimeUnit;

import br.ufpb.dcx.apps4society.educapimanager.control.service.ContextsService;
import br.ufpb.dcx.apps4society.educapimanager.control.service.LoginService;
import br.ufpb.dcx.apps4society.educapimanager.control.service.UsersService;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitConfig {

    public static Retrofit getAuthRetrofit(Context context){
        return new Retrofit.Builder()
                .baseUrl(EducAPIConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(new OkHttpClient.Builder()
                        .readTimeout(30, TimeUnit.MINUTES)
                        .connectTimeout(30, TimeUnit.MINUTES)
                        .addInterceptor(new AuthInterceptor(context))
                        .build())
                .build();
    }

    public static Retrofit getPublicRetrofit(){
        return new Retrofit.Builder()
                .baseUrl(EducAPIConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static LoginService loginService(){
        return getPublicRetrofit().create(LoginService.class);
    }

    public static UsersService userNewService(){
        return getPublicRetrofit().create(UsersService.class);
    }

    public static ContextsService contextsService(){
        return getPublicRetrofit().create(ContextsService.class);
    }
}
