package com.example.eventratingapp;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.eventratingapp.models.Event;

import java.lang.reflect.Array;
import java.util.ArrayList;

class MyListAdapter extends ArrayAdapter<String> {
    private final Activity context;
    private final Event[] events;
    ;

    public MyListAdapter(Activity context, String[] data, Event[] events) {
        super(context, R.layout.layout_test, data);
        this.context = context;
        this.events = events;
    }
//
//    public View getView(int position, View view, ViewGroup parent) {
//        LayoutInflater inflater=context.getLayoutInflater();
//        View rowView= inflater.inflate(R.layout.layout_test, null,true);
//
//        TextView titleText = (TextView) rowView.findViewById(R.id.title);
//        TextView subtitleText = (TextView) rowView.findViewById(R.id.subtitle);
//        TextView date = (TextView) rowView.findViewById(R.id.date);
//
//        titleText.setText(events[position].getTitle());
//        subtitleText.setText(events[position].getDescription());
//        date.setText(events[position].getDate());
//
//        return rowView;
//    };
}
