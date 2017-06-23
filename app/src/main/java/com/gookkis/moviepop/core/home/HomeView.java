package com.gookkis.moviepop.core.home;

import android.content.Intent;

import com.gookkis.moviepop.models.MovieModel;


public interface HomeView {
    void showLoading();

    void hideLoading();

    void movieDetailSuccess(MovieModel movieModel);

    void movieDetailFailed(String message);

    void moveToDetails(Intent intent);
}
