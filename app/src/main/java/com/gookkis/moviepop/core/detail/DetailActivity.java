package com.gookkis.moviepop.core.detail;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.gookkis.moviepop.R;
import com.gookkis.moviepop.utils.ApiConst;
import com.gookkis.moviepop.utils.Const;
import com.gookkis.moviepop.utils.Helpers;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity implements DetailView {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_poster)
    ImageView ivPoster;
    @BindView(R.id.tv_released_date)
    TextView tvReleasedDate;
    @BindView(R.id.tv_rating)
    TextView tvRating;
    @BindView(R.id.tv_synopsis)
    TextView tvSynopsis;

    private DetailPresenter detailPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        initComponent();

    }

    private void initComponent() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if (getIntent() != null) {
            tvTitle.setText(getIntent().getStringExtra(Const.original_title));
            tvRating.setText(getIntent().getStringExtra(Const.vote_average));
            tvReleasedDate.setText(getIntent().getStringExtra(Const.release_date));
            tvSynopsis.setText(getIntent().getStringExtra(Const.overview));
            Picasso.with(getApplicationContext()).load(ApiConst.URL_POSTER + getIntent().getStringExtra(Const.poster_path))
                    .resize(Helpers.getWidthPoster(this) * 2, 0).into(ivPoster);
        }

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void showDetail() {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
