package com.xxxxx.sampleapplicationtmdb.data

import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("movie/popular")
    fun getPopularMovies(@Query("page") page: Int, @Query("api_key") apiKey: String): Single<MoviePageResult?>

    @GET("movie/{movie_id}")
    fun getMovie(@Path("movie_id") movieId: Int, @Query("api_key") apiKey: String): Single<MovieDetails?>
}