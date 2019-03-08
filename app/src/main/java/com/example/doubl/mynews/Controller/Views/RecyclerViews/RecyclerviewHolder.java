package com.example.doubl.mynews.Controller.Views.RecyclerViews;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;
import com.example.doubl.mynews.Controller.Models.ResultMostPopular;
import com.example.doubl.mynews.Controller.Models.ResultTopStories;
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
        ButterKnife.bind(this, itemView);
    }

    public void updateRecyclerViewWithMostPopularArticle(ResultMostPopular article, RequestManager glide) {
        UpdateAllFragmentItem updateAllFragmentItem = new UpdateAllFragmentItem();
        this.textViewSection.setText(updateAllFragmentItem.setSection(article));
        this.textViewBody.setText(updateAllFragmentItem.setBodyText(article));
        this.textViewDate.setText(updateAllFragmentItem.setPublisheddate(article));
        this.setImageMostPopular(article, glide);
    }

    public void updateRecyclerViewWithTopStories(ResultTopStories , RequestManager glide) {
        UpdateAllFragmentItem updateAllFragmentItem = new UpdateAllFragmentItem();
        this.textViewSection.setText(updateAllFragmentItem.setSection());
        this.textViewBody.setText(updateAllFragmentItem.setBodyText());
        this.textViewDate.setText(updateAllFragmentItem.setPublisheddate());
        this.setImageMostPopular(, glide);
    }


private void setImageMostPopular(ResultMostPopular article, RequestManager glide)
{
    if (article.getMedia()!=null){
        String urlImage = article.getMedia().get(0).getMediaMetadata().get(0).getUrl();
        if (urlImage.startsWith("images")){
            urlImage = "https://www.nytimes.com/" + urlImage;
        }
        glide.load(urlImage).apply(new RequestOptions().fallback(R.drawable.capitaine_flam)).into(imageView);
    }else
        imageView.setImageResource(R.drawable.albator);
    }
}
