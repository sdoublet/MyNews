package com.example.doubl.mynews.Controller.Views.RecyclerViews;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.doubl.mynews.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecyclerviewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.image_view_all_fragment_item)ImageView imageView;
    @BindView(R.id.text_view_section)TextView textViewSection;
    @BindView(R.id.text_view_body)TextView textViewBody;

    public RecyclerviewHolder(@NonNull View itemView) {
        super(itemView);
        ButterKnife.bind(itemView);
    }

    private void updateRecyclerViewWithMostPopularArticle(){
UpdateAllFragmentItem
    }
}
