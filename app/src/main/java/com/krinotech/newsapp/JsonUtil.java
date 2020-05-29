package com.krinotech.newsapp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

class JsonUtil {
    private static final String RESULTS_ARRAY = "results";
    private static final String TAGS_ARRAY = "tags";
    private static final String SECTION_NAME_PRIMITIVE = "sectionName";
    private static final String WEB_TITLE_PRIMITIVE = "webTitle";
    private static final String WEB_PUBLICATION_DATE_PRIMITIVE = "webPublicationDate";
    private static final String WEB_URL_PRIMITIVE = "webUrl";
    private static final String RESPONSE_OBJECT = "response";
    private static final String TYPE_PRIMITIVE = "type";

    private static final String CONTRIBUTOR_VALUE = "contributor";


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

                    String sectionName = objectFromArray.optString(SECTION_NAME_PRIMITIVE);
                    String webTitle = objectFromArray.optString(WEB_TITLE_PRIMITIVE);
                    String date = objectFromArray.optString(WEB_PUBLICATION_DATE_PRIMITIVE);
                    String urlToStory = objectFromArray.optString(WEB_URL_PRIMITIVE);
                    JSONArray referencesArray = objectFromArray.getJSONArray(TAGS_ARRAY);
                    String author = getAuthor(referencesArray);

                    news.add(new News(webTitle, sectionName, author, date, urlToStory));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return news;
    }

    private static String getAuthor(JSONArray referencesArray) throws JSONException {
        String author;
        StringBuilder stringBuilder = new StringBuilder();
        for(int index = 0; index < referencesArray.length(); index++) {
            JSONObject tagsObject = referencesArray
                    .getJSONObject(index);
            if(tagsObject.has(TYPE_PRIMITIVE)) {
                if(CONTRIBUTOR_VALUE.equals(tagsObject.optString(TYPE_PRIMITIVE))) {
                    String authorFromArray = tagsObject
                            .optString(WEB_TITLE_PRIMITIVE);
                    if(index != 0 && !authorFromArray.isEmpty()) {
                        stringBuilder.append(", ");
                    }
                    stringBuilder.append(authorFromArray);
                }
            }
        }
        author = stringBuilder.toString();
        return author;
    }
}
