package com.example.eventratingapp.database;

import com.example.eventratingapp.models.Event;

import java.util.List;

public interface EventListCallback {
    void onCallBack(List<Event> list);
}
