package br.ufpb.dcx.apps4society.educapimanager.helper;

import android.content.Context;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class AuthInterceptor implements Interceptor {

    private GerenteDeSessao gerenteDeSessao;

    public AuthInterceptor(Context context) {
        gerenteDeSessao = new GerenteDeSessao(context);
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request.Builder requestBuilder = chain.request().newBuilder();
        requestBuilder.addHeader("Authorization", "Bearer " + gerenteDeSessao.fetchAuthToken());

        return chain.proceed(requestBuilder.build());
    }
}
