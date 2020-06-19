package com.gusrifarisyudaalhafis.uas.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.gusrifarisyudaalhafis.uas.R;
import com.gusrifarisyudaalhafis.uas.model.movie.Movie;

import java.util.Objects;

public class DetailActivity extends AppCompatActivity {
    //inisialisasi
    private Toolbar toolbar;
    private TextView title, description, originalLanguage, releaseDate, voteAverage;
    private ImageView poster, backdrop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent = getIntent();
        Movie movie = intent.getParcelableExtra("movie");

        //binding komponen yang digunakan
        toolbar = findViewById(R.id.toolbar_detail);
        title = findViewById(R.id.title);
        description = findViewById(R.id.overview);
        originalLanguage = findViewById(R.id.original_language);
        releaseDate = findViewById(R.id.release_date);
        voteAverage = findViewById(R.id.vote_average);
        poster = findViewById(R.id.poster);
        backdrop = findViewById(R.id.backdrop);

        initToolbar();
        showDetails(Objects.requireNonNull(movie));
    }

    //beri title pada toolbar
    private void initToolbar() {
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        setTitle(R.string.details);
    }

    //memasukkan data pada komponen untuk ditampilkan
    private void showDetails(Movie movie) {
        title.setText(movie.getTitle());
        originalLanguage.setText(movie.getOriginalLanguage());
        description.setText(movie.getDescription());
        releaseDate.setText(movie.getReleaseDate());
        voteAverage.setText(movie.getVoteAverage());
        Glide.with(this).load("https://image.tmdb.org/t/p/w185" + movie.getPoster()).into(poster);
        Glide.with(this).load("https://image.tmdb.org/t/p/w185" + movie.getPoster()).into(backdrop);
    }

    //beri fungsi ketika tombol back pada toolbar diklik
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            this.finish();
        }
        return true;
    }
}
