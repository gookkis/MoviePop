package com.gookkis.moviepop.core.detail;

import android.content.Context;

public class DetailPresenter {
    private DetailView view;
    private Context context;

    public DetailPresenter(DetailView view, Context context) {
        this.view = view;
        this.context = context;
    }
}
