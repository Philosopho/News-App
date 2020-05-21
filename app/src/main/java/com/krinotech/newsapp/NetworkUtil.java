package com.krinotech.newsapp;

import android.net.Uri;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class NetworkUtil {
    private static final String BASE_URL = "http://content.guardianapis.com/";
    private static final String SEARCH_URL = BASE_URL + "search";

    private static final String API_KEY_VALUE = "test";
    private static final String SEARCH_PARAM = "q";
    private static final String API_KEY_PARAM = "api-key";
    private static final String ORDER_BY_PARAM = "order-by";
    private static final String SHOW_REFERENCES_PARAM = "show-references";
    private static final String AUTHOR_QUERY = "author";
    private static final String GET_REQUEST = "GET";


    public static URL search(String searchQuery, String orderBy) {
        Uri uri = Uri.parse(SEARCH_URL)
                .buildUpon()
                .appendQueryParameter(SEARCH_PARAM, searchQuery)
                .appendQueryParameter(ORDER_BY_PARAM, orderBy)
                .appendQueryParameter(API_KEY_PARAM, API_KEY_VALUE)
                .appendQueryParameter(SHOW_REFERENCES_PARAM, AUTHOR_QUERY)
                .build();

        try {
            return new URL(uri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<News> getSearchResults(String search, String orderBy) {
        List<News> news = null;
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection =
                    (HttpURLConnection) NetworkUtil
                            .search(search, orderBy)
                            .openConnection();
            urlConnection.setRequestMethod(GET_REQUEST);
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            inputStream = new BufferedInputStream(urlConnection.getInputStream());
            String jsonResponse = convertStreamToString(inputStream);
            news = JsonUtil.search(jsonResponse);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(urlConnection != null) {
                urlConnection.disconnect();
            }
            if(inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return news;
        }
    }

    /**
     * Udacity convertStreamToString method from HttpHandler
     */
    private static String convertStreamToString(InputStream inputStream) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder stringBuilder = new StringBuilder();

        String line;
        try {
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line).append('\n');
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return stringBuilder.toString();
    }
}
