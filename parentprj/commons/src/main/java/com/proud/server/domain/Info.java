package com.proud.server.domain;

/**
 * Info class
 * Created by fabry on 02/10/2016.
 */
public class Info {

    private final long id;
    private final String content;

    public Info(long id, String content) {
        this.id = id;
        this.content = content;
    }

    public long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }
}