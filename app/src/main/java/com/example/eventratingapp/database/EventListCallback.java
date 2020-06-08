package com.example.eventratingapp.database;

import com.example.eventratingapp.models.Event;

import java.util.ArrayList;

public interface EventListCallback {
    void onCallBack(ArrayList<Event> list);
}
