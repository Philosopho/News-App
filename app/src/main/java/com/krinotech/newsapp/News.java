package com.krinotech.newsapp;

public class News {

    private String title;
    private String sectionName;
    private String authorName;
    private String datePublished;
    private String urlToStory;

    public News(String title, String sectionName, String authorName, String datePublished, String urlToStory) {
        this.title = title;
        this.sectionName = sectionName;
        this.authorName = authorName;
        this.datePublished = datePublished;
        this.urlToStory = urlToStory;
    }

    public String getTitle() {
        return title;
    }

    public String getSectionName() {
        return sectionName;
    }

    public String getAuthorName() {
        return authorName;
    }

    public String getDatePublished() {
        return datePublished;
    }

    public String getUrlToStory() {
        return urlToStory;
    }
}
