package com.example.eventratingapp.models;

/**
 * Event pojo object
 */
public class Event {
    public String title;
    public String description;
    public String date;

    /**
     * construct a new Event object.
     * @param title
     * @param description
     * @param date
     */
    public Event(String title, String description, String date){
        this.title = title;
        this.description = description;
        this.date = date;
    }
}
