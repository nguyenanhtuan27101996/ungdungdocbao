package com.example.tuanpc.appnews.Models;

/**
 * Created by tuanpc on 9/23/2017.
 */

public class NewsGiaiTri {
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

    public NewsGiaiTri(String title, String content, String image) {

        this.title = title;
        this.content = content;
        this.image = image;
    }
}
