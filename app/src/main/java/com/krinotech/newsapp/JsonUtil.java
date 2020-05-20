package com.krinotech.newsapp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

class JsonUtil {
    private static final String RESULTS_ARRAY = "results";
    private static final String REFERENCES_ARRAY = "references";
    private static final String SECTION_NAME_PRIMITIVE = "sectionName";
    private static final String WEB_TITLE_PRIMITIVE = "webTitle";
    private static final String WEB_PUBLICATION_DATE_PRIMITIVE = "webPublicationDate";
    private static final String WEB_URL_PRIMITIVE = "webUrl";
    private static final String RESPONSE_OBJECT = "response";
    private static final String AUTHOR_NAME_PRIMITIVE = "author";


    public static List<News> search(String jsonResponse) {
        List<News> news = null;
        if(jsonResponse != null) {
            try {
                JSONObject jsonObject = new JSONObject(jsonResponse);
                JSONObject response = jsonObject.getJSONObject(RESPONSE_OBJECT);
                JSONArray jsonArray = response.getJSONArray(RESULTS_ARRAY);
                news = new ArrayList<>();
                for(int index = 0; index < jsonArray.length(); index++) {
                    JSONObject objectFromArray = jsonArray.getJSONObject(index);
                    String sectionName = objectFromArray.getString(SECTION_NAME_PRIMITIVE);
                    String webTitle = objectFromArray.getString(WEB_TITLE_PRIMITIVE);
                    String date = objectFromArray.getString(WEB_PUBLICATION_DATE_PRIMITIVE);
                    String urlToStory = objectFromArray.getString(WEB_URL_PRIMITIVE);
                    JSONArray referencesArray = objectFromArray.getJSONArray(REFERENCES_ARRAY);
                    String author;
                    StringBuilder stringBuilder = new StringBuilder();
                    for(int index2 = 0; index2 < referencesArray.length(); index2++) {
                        String authorFromArray = referencesArray
                                .getJSONObject(index2)
                                .getString(AUTHOR_NAME_PRIMITIVE);
                        if(index2 != 0 && !authorFromArray.isEmpty()) {
                            stringBuilder.append(", ");
                        }
                        stringBuilder.append(authorFromArray);
                    }
                    author = stringBuilder.toString();
                    news.add(new News(webTitle, sectionName, author, date, urlToStory));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return news;
    }
}
