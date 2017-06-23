package com.gookkis.moviepop.core.home;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.gookkis.moviepop.R;
import com.gookkis.moviepop.adapter.MovieAdapter;
import com.gookkis.moviepop.models.MovieModel;
import com.gookkis.moviepop.models.Result;
import com.gookkis.moviepop.utils.ApiConst;
import com.gookkis.moviepop.utils.Helpers;
import com.gookkis.moviepop.utils.RecyclerItemClickListener;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeActivity extends AppCompatActivity implements HomeView {

    private HomePresenter homePresenter;
    private MovieModel movieModel;
    public final static String LIST_STATE_KEY = "recycler_list_state";
    Parcelable listState;
    GridLayoutManager gridLayoutManager;

    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);

        initComponent();
    }

    private void initComponent() {
        homePresenter = new HomePresenter(this, getApplicationContext());
        gridLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.addOnItemTouchListener(itemClickListener());
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_spinner, menu);

        MenuItem item = menu.findItem(R.id.spinner);
        Spinner spinner = (Spinner) MenuItemCompat.getActionView(item);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.desc_sort_by, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                homePresenter.loadMovie(homePresenter.getValueSort(position), ApiConst.API_KEY);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                homePresenter.loadMovie(homePresenter.getValueSort(0), ApiConst.API_KEY);
            }
        });
        return true;
    }


    @Override
    public void showLoading() {
        progressBar.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.INVISIBLE);
    }

    @Override
    public void hideLoading() {
        progressBar.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public void movieDetailSuccess(MovieModel respMovieModel) {
        if (respMovieModel.getPage() != null) {
            this.movieModel = respMovieModel;
            recyclerView.setAdapter(new MovieAdapter(getApplicationContext(),
                    movieModel.getResults(), R.layout.item_movie, Helpers.getWidthPoster(this)));
        }
    }

    private RecyclerItemClickListener itemClickListener() {
        return new RecyclerItemClickListener(getApplicationContext(), recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Result result = movieModel.getResults().get(position);
                homePresenter.goToDetails(result);
            }

            @Override
            public void onLongItemClick(View view, int position) {

            }
        });
    }

    @Override
    public void movieDetailFailed(String message) {
        Toast.makeText(this, "Error: " + message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void moveToDetails(Intent intent) {
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        homePresenter.destroyData();
    }
}
