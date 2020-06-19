package com.gusrifarisyudaalhafis.uas.api.movie;

import com.gusrifarisyudaalhafis.uas.model.movie.MovieResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface APIEndpoints {

    //GET Movie
    @GET("movie/{category}")
    Call<MovieResponse> getMovies(@Path("category") String movieCategory);

    //GET TV
    @GET("tv/{category}")
    Call<MovieResponse> getTv(@Path("category") String tvCategory);
}
