package br.ufpb.dcx.apps4society.educapimanager.control.service;

import java.util.List;

import br.ufpb.dcx.apps4society.educapimanager.control.service.response.ChallengePageResponse;
import br.ufpb.dcx.apps4society.educapimanager.model.bean.Challenge;
import br.ufpb.dcx.apps4society.educapimanager.model.dto.NewChallengeDTO;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ChallengesService {

    @GET("v1/api/auth/challenges")
    Call<List<Challenge>> getChallengesByUser();

    @GET("v1/api/auth/challenges/{idChallenge}")
    Call<Challenge> getChallengeByOfUserById(@Path("idChallenge") Long idChallenge);

    @PUT("v1/api/auth/challenges/{idChallenge}")
    Call<Challenge> updateChallenge(@Path("idChallenge") Long idChallenge, @Body NewChallengeDTO newChallengeDTO);

    @DELETE("v1/api/auth/challenges/{idChallenge}")
    Call<Void> deleteChallenges(@Path("idChallenge") Long idChallenge);

    @POST("v1/api/auth/challenges/{idContext}")
    Call<Challenge> insertChallenge(@Path("idContext") Long idContext, @Body NewChallengeDTO newChallengeDTO);

    @GET("v1/api/challenges")
    Call<ChallengePageResponse> getChallengesWithPagination(
            @Query("page") Integer page,
            @Query("prefix") String prefix,
            @Query("size") Integer size
    );
}
