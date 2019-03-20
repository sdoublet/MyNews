package com.example.doubl.mynews.Controller.Views.RecyclerViews;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.bumptech.glide.RequestManager;
import com.example.doubl.mynews.Controller.Models.ResultSearchApi;
import com.example.doubl.mynews.Controller.Models.SearchApi;
import com.example.doubl.mynews.R;

import java.util.List;

public class SearchArticleAdapter extends RecyclerView.Adapter<RecyclerviewHolder> {

    private List<ResultSearchApi> resultSearchApiList;
    private Context context;
    private RequestManager glide;


    public SearchArticleAdapter(List<ResultSearchApi> resultSearchApis, Context context, RequestManager glide) {
        this.resultSearchApiList = resultSearchApis;
        this.context = context;
        this.glide = glide;
    }


    @NonNull
    @Override
    public RecyclerviewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.all_fragment_item, viewGroup, false);
        return new RecyclerviewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerviewHolder recyclerviewHolder, int position) {
        recyclerviewHolder.updateRecyclerViewWithSearchArticle(this.resultSearchApiList.get(position), glide);
    }

    @Override
    public int getItemCount() {
        Log.e("itemCount", Integer.toString(resultSearchApiList.size()));
        return resultSearchApiList.size();

    }

    //ti configure itemClick
    public String getUrl(int position) {
        return resultSearchApiList.get(position).getWebUrl();
    }
}
