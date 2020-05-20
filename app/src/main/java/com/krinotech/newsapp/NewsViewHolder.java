package com.krinotech.newsapp;

import android.content.Intent;
import android.net.Uri;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import com.krinotech.newsapp.databinding.NewsItemBinding;

class NewsViewHolder extends RecyclerView.ViewHolder {
    private NewsItemBinding binding;

    public NewsViewHolder(final NewsItemBinding binding) {
        super(binding.getRoot());

        this.binding = binding;

        binding.tvNewsTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = (String) binding.tvNewsTitle.getTag();
                Uri uri = Uri.parse(url);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                v.getContext().startActivity(intent);
            }
        });
    }

    public void bind(News news) {
        binding.setNews(news);
        binding.tvNewsTitle.setTag(news.getUrlToStory());
        binding.executePendingBindings();
        if(!news.getDatePublished().isEmpty()) {
            binding.tvNewsDatePublishedHeader.setVisibility(View.VISIBLE);
            binding.tvNewsDatePublished.setVisibility(View.VISIBLE);
        }
        if(!news.getAuthorName().isEmpty()) {
            binding.tvNewsAuthorName.setVisibility(View.VISIBLE);
            binding.tvNewsAuthorNameHeader.setVisibility(View.VISIBLE);
        }
    }
}
