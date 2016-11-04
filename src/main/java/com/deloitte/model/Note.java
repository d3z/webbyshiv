/*
 * Copyright 2016 Instil Software.
 */
package com.deloitte.model;

public class Note {

    private final String title;
    private final String text;
    private final String date;

    public Note(String title, String text, String date) {
        this.title = title;
        this.text = text;
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }

    public String getDate() {
        return date;
    }
}
