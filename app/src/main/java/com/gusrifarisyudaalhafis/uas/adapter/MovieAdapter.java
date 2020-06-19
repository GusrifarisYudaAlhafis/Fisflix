package com.gusrifarisyudaalhafis.uas.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.gusrifarisyudaalhafis.uas.R;
import com.gusrifarisyudaalhafis.uas.activity.DetailActivity;
import com.gusrifarisyudaalhafis.uas.model.movie.Movie;

import java.util.ArrayList;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {
    //inisialisasi
    private Context context;
    private ArrayList<Movie> movies = new ArrayList<>();

    //constructor dengan context
    public MovieAdapter(Context context) {
        this.context = context;
    }

    //mengeset movie
    public void setMovies(ArrayList<Movie> movies) {
        this.movies = movies;
        notifyDataSetChanged();
    }

    //buat view holder
    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new MovieViewHolder(mView);
    }

    //binding view holder
    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        holder.bind(movies.get(position));
    }

    //dapatkan jumlah item
    @Override
    public int getItemCount() {
        return movies.size();
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        //inisialisasi
        private ImageView poster;
        private TextView movieTitle;

        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            //binding komponen yang digunakan
            movieTitle = itemView.findViewById(R.id.title);
            poster = itemView.findViewById(R.id.poster);
            //berikan fungsi ketika tombol diklik
            itemView.setOnClickListener(this);
        }

        public void bind(Movie movie) {
            //atur gambar dengan glide dan berikan title
            Glide.with(context).load("https://image.tmdb.org/t/p/w185"+movie.getPoster()).apply(new RequestOptions()).into(poster);
            movieTitle.setText(movie.getTitle());
        }

        @Override
        public void onClick(View v) {
            //dapatkan data dan pindah activity
            Intent details = new Intent(context, DetailActivity.class);
            details.putExtra("movie", movies.get(getAdapterPosition()));
            context.startActivity(details);
        }
    }
}
