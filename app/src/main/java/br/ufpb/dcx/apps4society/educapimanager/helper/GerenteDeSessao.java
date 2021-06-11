package br.ufpb.dcx.apps4society.educapimanager.helper;

import android.content.Context;
import android.content.SharedPreferences;

import br.ufpb.dcx.apps4society.educapimanager.R;

public class GerenteDeSessao {

    private final SharedPreferences sharedPreferences;

    public GerenteDeSessao(Context context) {
        sharedPreferences = context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE);
    }

    private static final String USER_TOKEN = "user_token";

    public void saveAuthToken(String token){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(USER_TOKEN, token);
        editor.apply();
    }

    public String fetchAuthToken(){
        return sharedPreferences.getString(USER_TOKEN, null);
    }

}
