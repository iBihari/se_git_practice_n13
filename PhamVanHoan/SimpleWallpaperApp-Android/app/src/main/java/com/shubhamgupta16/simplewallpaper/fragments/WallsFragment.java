package com.shubhamgupta16.simplewallpaper.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.shubhamgupta16.simplewallpaper.MainApplication;
import com.shubhamgupta16.simplewallpaper.R;
import com.shubhamgupta16.simplewallpaper.data_source.DataService;
import com.shubhamgupta16.simplewallpaper.adapters.WallsAdapter;
import com.shubhamgupta16.simplewallpaper.models.WallsPOJO;

import java.util.ArrayList;

public class WallsFragment extends Fragment {

    private static final String TAG = "WallsFragment";

    public WallsFragment() {
        // Required empty public constructor
    }

    private View view;
    private ArrayList<WallsPOJO> list;
    private ArrayList<Integer> adPositionList;
    private WallsAdapter adapter;
    private DataService dataService;
    private boolean isScrollLoad = false;
    private int maxPage = 0, lastFetch = 0;
    private LinearLayout errorLayout;
    private ProgressBar progressBar;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_reycler, container, false);
        init();
        return view;
    }

    private void init() {
        Log.d("tagtag", "init");
        dataService = MainApplication.getDataService(requireActivity().getApplication());
        list = new ArrayList<>();
        adPositionList = new ArrayList<>();
        errorLayout = view.findViewById(R.id.errorLayout);
        progressBar = view.findViewById(R.id.progressBar);
        RecyclerView wallsRecycler = view.findViewById(R.id.recyclerView);
        final StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL);
//        final GridLayoutManager manager = new GridLayoutManager(getContext(), 2);
        wallsRecycler.setLayoutManager(manager);
        adapter = new WallsAdapter(getContext(), dataService, list, DataService.QueryType.NONE);
        adapter.setOnRemoveFromFavSection(this::handleErrorLayout);
        wallsRecycler.setAdapter(adapter);
        wallsRecycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int currentItems = manager.getChildCount();
                int totalItems = manager.getItemCount();
                int[] firstVisibleItemPositions = new int[list.size()];
                int scrollOutItems;
                if (list.size() >= 2) {
                    scrollOutItems = manager.findFirstVisibleItemPositions(firstVisibleItemPositions)[0];
                } else {
                    scrollOutItems = 0;
                }
                if (isScrollLoad && (currentItems + scrollOutItems >= totalItems)) {
                    dataService.getPagesCount(type, extras, count -> maxPage = count);
                    if (lastFetch < maxPage) {
                        isScrollLoad = false;
                        fetchWalls(lastFetch + 1);
                        Log.d(TAG, "scroll to bottom " + type);
                    }
                }
            }
        });
    }

    private DataService.QueryType type = DataService.QueryType.NONE;
    private String extras;

    @SuppressLint("NotifyDataSetChanged")
    public void setFragment(DataService.QueryType type, String extras) {
        Log.d("tagtag", "set" + type);
        this.type = type;
        this.extras = extras;
        lastFetch = 0;
        maxPage = 0;
        list.clear();
        adapter.setType(type);
        adapter.notifyDataSetChanged();
        dataService.getPagesCount(type, extras, count -> maxPage = count);
        errorLayout.setVisibility(View.GONE);
        setErrorLayout();
        fetchWalls(1);
    }

    private void setErrorLayout() {
        ImageView errorImage = errorLayout.findViewById(R.id.errorImage);
        TextView errorTitle = errorLayout.findViewById(R.id.errorTitle);
        if (type == DataService.QueryType.FAVORITE) {
            errorImage.setImageResource(R.drawable.no_fav);
            errorTitle.setText(R.string.no_fav);
        } else {
            errorImage.setImageResource(R.drawable.no_res);
            errorTitle.setText(R.string.no_res);
        }
    }

    private void handleErrorLayout() {
        if (list.isEmpty()) {
            errorLayout.setVisibility(View.VISIBLE);
        } else {
            errorLayout.setVisibility(View.GONE);
        }
    }

    private void fetchWalls(final int page) {
        Log.d("tagtag", "fetch page: " + page + "type" + type);
        if (page == 1) {
            errorLayout.setVisibility(View.GONE);
            progressBar.setVisibility(View.VISIBLE);
        }
        dataService.getWallpapers(page, type, extras, wallpapers -> handleRes(page, wallpapers));

    }

    private void handleRes(int page, ArrayList<WallsPOJO> walls) {
        if (page != 1) {
            if (list.size() >= 1) {
                list.remove(list.size() - 1);
            }
            if (list.size() >= 1) {
                list.remove(list.size() - 1);
            }
        } else {
            if (!list.isEmpty()) {
                int size = list.size();
                list.clear();
                adapter.notifyItemRangeRemoved(0, size);
            }
            progressBar.setVisibility(View.GONE);
        }
        int from = list.size();
        list.addAll(walls);
        if (page >= 1 && page != maxPage && !list.isEmpty()) {
            adPositionList.add(list.size());
            list.add(new WallsPOJO(false));
            list.add(new WallsPOJO(false));
        }
        adapter.notifyItemRangeChanged(from, 2);
        adapter.notifyItemRangeInserted(from + 2, list.size());

        lastFetch = page;
        isScrollLoad = true;

        handleErrorLayout();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void focus() {
        Log.d("tagtag", "focus, " + list.size() + " " + type);
        dataService.getPagesCount(type, extras, count -> maxPage = count);
        if (type == DataService.QueryType.FAVORITE) {
            adPositionList.clear();
            list.clear();
            adapter.notifyDataSetChanged();
            fetchWalls(1);
        } else
            adapter.notifyDataSetChanged();
    }

}