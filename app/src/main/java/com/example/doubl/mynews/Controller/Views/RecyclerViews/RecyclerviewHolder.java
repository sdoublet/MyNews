package com.example.doubl.mynews.Controller.Views.RecyclerViews;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.RequestManager;
import com.example.doubl.mynews.Controller.Models.ResultMostPopular;
import com.example.doubl.mynews.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecyclerviewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.image_view_all_fragment_item)
    ImageView imageView;
    @BindView(R.id.text_view_section)
    TextView textViewSection;
    @BindView(R.id.text_view_body)
    TextView textViewBody;
    @BindView(R.id.text_view_date)TextView textViewDate;

    public RecyclerviewHolder(@NonNull View itemView) {
        super(itemView);
        ButterKnife.bind(itemView);
    }

    public void updateRecyclerViewWithMostPopularArticle(ResultMostPopular article, RequestManager glide) {
        UpdateAllFragmentItem updateAllFragmentItem = new UpdateAllFragmentItem();
        this.textViewSection.setText(updateAllFragmentItem.setSection(article));
        this.textViewBody.setText(updateAllFragmentItem.setBodyText(article));
        this.textViewDate.setText(updateAllFragmentItem.setPublisheddate(article));
    }
}
