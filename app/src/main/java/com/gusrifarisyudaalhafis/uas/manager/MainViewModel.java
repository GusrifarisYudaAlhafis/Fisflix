package com.gusrifarisyudaalhafis.uas.manager;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.gusrifarisyudaalhafis.uas.activity.MainActivity;
import com.gusrifarisyudaalhafis.uas.api.movie.APIClient;
import com.gusrifarisyudaalhafis.uas.api.movie.APIEndpoints;
import com.gusrifarisyudaalhafis.uas.model.movie.Movie;
import com.gusrifarisyudaalhafis.uas.model.movie.MovieResponse;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainViewModel extends ViewModel {
    //deklarasi
    private MutableLiveData<ArrayList<Movie>> movieList = new MutableLiveData<>();

    //setMovie berdasarkan kategori
    public void setMovies(String movieCategory) {
        APIEndpoints apiService = APIClient.getClient().create(APIEndpoints.class);
        Call<MovieResponse> call = apiService.getMovies(movieCategory);
        call.enqueue(new Callback<MovieResponse>() {
            //ketika berhasil
            @Override
            public void onResponse(@NotNull Call<MovieResponse> call, @NotNull Response<MovieResponse> response) {
                //uji coba
                try {
                    //dapatkan result
                    ArrayList<Movie> movies = Objects.requireNonNull(response.body()).getResults();
                    movieList.postValue(movies);
                } catch (Exception e) {
                    Log.d(MainActivity.class.getSimpleName(), Objects.requireNonNull(e.getLocalizedMessage()));
                }
            }

            //ketika gagal
            @Override
            public void onFailure(@NotNull Call<MovieResponse> call, @NotNull Throwable t) {
                //berikan log
                Log.d(MainActivity.class.getSimpleName(), Objects.requireNonNull(t.getLocalizedMessage()));
            }
        });
    }

    //dapatkan movie
    public LiveData<ArrayList<Movie>> getMovies() {
        return movieList;
    }

    //setTV berdasarkan kategori
    public void setTv(String tvCategory) {
        APIEndpoints apiService = APIClient.getClient().create(APIEndpoints.class);
        Call<MovieResponse> call = apiService.getTv(tvCategory);
        call.enqueue(new Callback<MovieResponse>() {
            //ketika berhasil
            @Override
            public void onResponse(@NotNull Call<MovieResponse> call, @NotNull Response<MovieResponse> response) {
                //uji coba
                try {
                    //dapatkan result
                    ArrayList<Movie> movies = Objects.requireNonNull(response.body()).getResults();
                    movieList.postValue(movies);
                } catch (Exception e) {
                    Log.d(MainActivity.class.getSimpleName(), Objects.requireNonNull(e.getLocalizedMessage()));
                }
            }

            //ketika gagal
            @Override
            public void onFailure(@NotNull Call<MovieResponse> call, @NotNull Throwable t) {
                //berikan log
                Log.d(MainActivity.class.getSimpleName(), Objects.requireNonNull(t.getLocalizedMessage()));
            }
        });
    }

    //dapatkan TV
    public LiveData<ArrayList<Movie>> getTv() {
        return movieList;
    }
}
