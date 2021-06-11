package br.ufpb.dcx.apps4society.educapimanager.control.service;

import br.ufpb.dcx.apps4society.educapimanager.control.service.response.LoginResponse;
import br.ufpb.dcx.apps4society.educapimanager.model.dto.UserLogin;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface LoginService {

    @POST("v1/api/auth/login")
    Call<LoginResponse> login(@Body UserLogin userLogin);
}
