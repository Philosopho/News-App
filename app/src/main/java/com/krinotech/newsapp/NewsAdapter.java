package com.krinotech.newsapp;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.krinotech.newsapp.databinding.NewsItemBinding;

import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsViewHolder> {
    private List<News> news;

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        NewsItemBinding binding =
                DataBindingUtil
                        .inflate(layoutInflater,
                                R.layout.news_item,
                                parent,
                                false);

        return new NewsViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, int position) {
        News news = this.news.get(position);
        holder.bind(news);
    }

    @Override
    public int getItemCount() {
        if(news != null) {
            return news.size();
        }
        return 0;
    }

    public void setNews(List<News> news) {
        this.news = news;
        notifyDataSetChanged();
    }
}
