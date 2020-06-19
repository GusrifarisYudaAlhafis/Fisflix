package com.gusrifarisyudaalhafis.uas.fragment.movies.comingSoon;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.gusrifarisyudaalhafis.uas.manager.MainViewModel;
import com.gusrifarisyudaalhafis.uas.R;
import com.gusrifarisyudaalhafis.uas.adapter.MovieAdapter;
import com.gusrifarisyudaalhafis.uas.model.movie.Movie;

import java.util.ArrayList;

public class MoviesComingSoonFragment extends Fragment {
    //inisialisasi
    private MovieAdapter adapter;
    private ShimmerFrameLayout mShimmerViewContainer;

    //constructor kosong
    public MoviesComingSoonFragment() {
    }

    //buat View dan set adapter
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_movies_coming_soon, container, false);
        RecyclerView list_mov = rootView.findViewById(R.id.list_mov);
        list_mov.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        adapter = new MovieAdapter(getActivity());
        list_mov.setAdapter(adapter);

        MainViewModel mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);
        //set kategori movie
        mainViewModel.setMovies("upcoming");
        mainViewModel.getMovies().observe(getViewLifecycleOwner(), getMovies);
        //gunakan library Shimmer untuk berkilau
        mShimmerViewContainer = rootView.findViewById(R.id.shimmer_view_container);
        mShimmerViewContainer.startShimmer();

        return rootView;
    }

    //ketika data didapat, tampilkan dan stop shimmer
    private Observer<ArrayList<Movie>> getMovies = new Observer<ArrayList<Movie>>() {
        @Override
        public void onChanged(ArrayList<Movie> movies) {
            if (movies != null) {
                mShimmerViewContainer.stopShimmer();
                mShimmerViewContainer.setVisibility(View.GONE);
                adapter.setMovies(movies);
            }
        }
    };
}
