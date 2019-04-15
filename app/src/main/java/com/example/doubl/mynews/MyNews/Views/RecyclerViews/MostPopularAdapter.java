package com.example.doubl.mynews.MyNews.Views.RecyclerViews;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.RequestManager;
import com.example.doubl.mynews.MyNews.Models.ResultMostPopular;
import com.example.doubl.mynews.R;

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
        LayoutInflater inflater=  LayoutInflater.from(context);
        View view= inflater.inflate(R.layout.all_fragment_item, viewGroup, false);
        return new RecyclerviewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerviewHolder recyclerviewHolder, int position) {
recyclerviewHolder.updateRecyclerViewWithMostPopularArticle(this.resultMostPopularList.get(position), glide);
    }

    @Override
    public int getItemCount() {
        return resultMostPopularList.size();
    }


    public String getUrl (int position){
        return resultMostPopularList.get(position).getUrl();
    }
}
