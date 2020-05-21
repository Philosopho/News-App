package com.krinotech.newsapp;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;
import androidx.preference.PreferenceManager;

import java.util.List;

public class NewsLoader extends AsyncTaskLoader<List<News>> {

    public NewsLoader(@NonNull Context context) {
        super(context);
    }

    @Nullable
    @Override
    public List<News> loadInBackground() {
        if(NetworkConnectionHelper.isConnected(getContext())) {
            Context context = getContext();
            SharedPreferences sharedPreferences =
                    PreferenceManager.getDefaultSharedPreferences(context);

            String search = sharedPreferences.getString(
                    context.getString(R.string.topic_key),
                    context.getString(R.string.search_debates_default)
            );

            String orderBy = sharedPreferences.getString(
                    context.getString(R.string.order_key),
                    context.getString(R.string.order_default)
            );

            return NetworkUtil.getSearchResults(search, orderBy);
        }
        else {
            return null;
        }
    }
}
