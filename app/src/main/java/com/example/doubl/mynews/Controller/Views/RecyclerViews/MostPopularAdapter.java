package com.example.doubl.mynews.Controller.Views.RecyclerViews;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.bumptech.glide.RequestManager;
import com.example.doubl.mynews.Controller.Models.ResultMostPopular;

import java.util.List;

public class MostPopularAdapter extends RecyclerView.Adapter<RecyclerviewHolder> {

    private List<ResultMostPopular> resultMostPopularList;
    private Context context;
    private RequestManager glide;

    public MostPopularAdapter(List<ResultMostPopular> resultMostPopularList, Context context, RequestManager glide) {
        this.resultMostPopularList = resultMostPopularList;
        this.context = context;
        this.glide = glide;
    }

    @NonNull
    @Override
    public RecyclerviewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerviewHolder recyclerviewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
