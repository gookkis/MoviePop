package com.gookkis.moviepop.network;

import com.gookkis.moviepop.models.MovieModel;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface RequestAPI {
    @GET("/3/movie/{sort}")
    Observable<MovieModel> getMovieList(@Path("sort") String sort_type, @Query("api_key") String api_key);
}
