package com.krinotech.newsapp;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

import java.util.List;

public class NewsLoader extends AsyncTaskLoader<List<News>> {

    public NewsLoader(@NonNull Context context) {
        super(context);
    }

    @Nullable
    @Override
    public List<News> loadInBackground() {
        if(NetworkConnectionHelper.isConnected(getContext())) {
            String search = getContext().getString(R.string.search_debates);
            return NetworkUtil.getSearchResults(search);
        }
        else {
            return null;
        }
    }
}
