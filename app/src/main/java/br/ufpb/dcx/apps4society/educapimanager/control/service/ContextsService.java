package br.ufpb.dcx.apps4society.educapimanager.control.service;

import java.util.List;

import br.ufpb.dcx.apps4society.educapimanager.control.service.response.ContextPageResponse;
import br.ufpb.dcx.apps4society.educapimanager.model.bean.Context;
import br.ufpb.dcx.apps4society.educapimanager.model.dto.ContextDTO;
import br.ufpb.dcx.apps4society.educapimanager.model.dto.NewContextDTO;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ContextsService {

    @GET("v1/api/auth/contexts")
    Call<List<ContextDTO>> getContextsByUser();

    @POST("v1/api/auth/contexts")
    Call<ContextDTO> insertNewContext(@Body NewContextDTO newContextDTO);

    @PUT("v1/api/auth/contexts/{idContext}")
    Call<ContextDTO> updateContext(@Path("idContext") Long idContext, @Body NewContextDTO newContextDTO);

    @DELETE("v1/api/auth/contexts/{idContext}")
    Call<ContextDTO> deleteContext(@Path("idContext") Long idContext);

    @GET("v1/api/contexts")
    Call<ContextPageResponse> getWithPagination(
            @Query("email") String email,
            @Query("name") String name,
            @Query("page") Integer page,
            @Query("size") Integer size);

    @GET("v1/api/contexts/{idContext}")
    Call<Context> getContextById(@Path("idContext") Long idContext);


}
