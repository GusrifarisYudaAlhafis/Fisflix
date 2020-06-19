package com.gusrifarisyudaalhafis.uas.fragment.tv.onTv;

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

public class OnTVFragment extends Fragment {
    //inisialisasi
    private MovieAdapter adapter;
    private ShimmerFrameLayout mShimmerViewContainer;

    //constructor kosong
    public OnTVFragment() {
    }

    //buat View dan set adapter
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_tv_on_tv, container, false);
        RecyclerView list_tvShow = rootView.findViewById(R.id.list_mov);
        list_tvShow.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        adapter = new MovieAdapter(getActivity());
        list_tvShow.setAdapter(adapter);

        MainViewModel mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);
        //set kategori TV
        mainViewModel.setTv("on_the_air");
        mainViewModel.getTv().observe(getViewLifecycleOwner(), getTv);
        //gunakan library Shimmer untuk berkilau
        mShimmerViewContainer = rootView.findViewById(R.id.shimmer_view_container);
        mShimmerViewContainer.startShimmer();

        return rootView;
    }

    //ketika data didapat, tampilkan dan stop shimmer
    private Observer<ArrayList<Movie>> getTv = new Observer<ArrayList<Movie>>() {
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
