package br.ufpb.dcx.apps4society.educapimanager.control.service;

import br.ufpb.dcx.apps4society.educapimanager.model.bean.User;
import br.ufpb.dcx.apps4society.educapimanager.model.dto.UserDTO;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.POST;
import retrofit2.http.PUT;

public interface UsersService {

    @POST("v1/api/users")
    Call<User> insertNewUser(@Body UserDTO userRegister);

    @PUT("v1/api/auth/users")
    Call<User> updateUserData(@Body UserDTO userRegister);

    @DELETE("v1/api/auth/users")
    Call<Void> deleteUser();
}
