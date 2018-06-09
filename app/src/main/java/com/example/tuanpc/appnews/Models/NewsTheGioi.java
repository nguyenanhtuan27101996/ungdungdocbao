package com.example.tuanpc.appnews.Models;

/**
 * Created by tuanpc on 9/22/2017.
 */

public class NewsTheGioi {
    private String title;
    private String content;
    private String image;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public NewsTheGioi(String title, String content, String image) {

        this.title = title;
        this.content = content;
        this.image = image;
    }
}
