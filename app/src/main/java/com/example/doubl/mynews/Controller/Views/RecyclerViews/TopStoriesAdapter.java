package com.example.doubl.mynews.Controller.Views.RecyclerViews;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.RequestManager;
import com.example.doubl.mynews.Controller.Models.ResultTopStories;
import com.example.doubl.mynews.R;

import java.util.List;

public class TopStoriesAdapter extends RecyclerView.Adapter<RecyclerviewHolder> {

    private List<ResultTopStories> resultTopStoriesList;
    private Context context;
    private RequestManager glide;
    @NonNull
    @Override
    public RecyclerviewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.all_fragment_item, viewGroup, false);
        return new RecyclerviewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerviewHolder recyclerviewHolder, int position) {
recyclerviewHolder.updateRecyclerViewWithTopStories(this.resultTopStoriesList.get(position), glide);
    }

    @Override
    public int getItemCount() {
        return resultTopStoriesList.size();
    }
}
