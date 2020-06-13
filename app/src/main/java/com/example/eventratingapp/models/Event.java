package com.example.eventratingapp.models;

/**
 * Event pojo object
 */
public class Event {
    private String title, description, date;

    //public no-arg constructor required by Firebase
    public Event() {

    }

    /**
     * construct a new Event object.
     * @param title
     * @param description
     * @param date
     */
    public Event(String title, String description, String date) {
        this.title = title;
        this.description = description;
        this.date = date;
    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String name) {
        this.title = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

}
