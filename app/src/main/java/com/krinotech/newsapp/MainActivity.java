package com.krinotech.newsapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.krinotech.newsapp.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<News>> {
    private ActivityMainBinding binding;
    private NewsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        setUpAdapter();
        getSupportLoaderManager().initLoader(1, null, this)
                .forceLoad();
    }

    private void setUpAdapter() {
        adapter = new NewsAdapter();

        LinearLayoutManager linearLayoutManager =
                new LinearLayoutManager(
                        this,
                        LinearLayoutManager.VERTICAL,
                        false);

        binding.rvMain.setHasFixedSize(true);
        binding.rvMain.setLayoutManager(linearLayoutManager);
        binding.rvMain.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent settingsIntent = new Intent(this, SettingsActivity.class);
            startActivity(settingsIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @NonNull
    @Override
    public Loader<List<News>> onCreateLoader(int id, @Nullable Bundle args) {
        return new NewsLoader(this);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<List<News>> loader, List<News> data) {
        if(data == null) {
            showErrorText(getString(R.string.data_error));
        }
        else if(data.isEmpty()) {
            showErrorText(getString(R.string.no_results));
        }
        else {
            adapter.setNews(data);
            showNews();
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<List<News>> loader) {
        adapter.setNews(new ArrayList<News>());
    }

    private void showErrorText(String text) {
        binding.pbMain.setVisibility(View.INVISIBLE);
        binding.tvErrorText.setVisibility(View.VISIBLE);
        binding.tvErrorText.setText(text);
        binding.rvMain.setVisibility(View.INVISIBLE);
    }

    private void showNews() {
        binding.pbMain.setVisibility(View.GONE);
        binding.tvErrorText.setVisibility(View.GONE);
        binding.rvMain.setVisibility(View.VISIBLE);
    }
}
