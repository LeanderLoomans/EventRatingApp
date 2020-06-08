package com.example.eventratingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.eventratingapp.database.DataBaseComm;
import com.example.eventratingapp.database.EventListCallback;
import com.example.eventratingapp.database.MessageCallback;
import com.example.eventratingapp.models.Event;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    DataBaseComm dataBaseComm;

    ListView list;

    ArrayList<Event> eventList = new ArrayList();

    Event[] events = {
            new Event("Title 1", "description 1",  "25 Feb 2020"),
            new Event("Title 2", "description 2",  "24 Feb 2020"),
            new Event("Title 3", "description 3",  "23 Feb 2020"),
            new Event("Title 4", "description 4",  "22 Feb 2020"),
            new Event("Title 5", "description 5",  "21 Feb 2020"),
            new Event("Title 6", "description 6",  "20 Feb 2020"),
            new Event("Title 7", "description 7",  "19 Feb 2020"),
            new Event("Title 7", "description 7",  "19 Feb 2020"),
            new Event("Title 7", "description 7",  "19 Feb 2020"),
            new Event("Title 7", "description 7",  "19 Feb 2020"),
            new Event("Title 7", "description 7",  "19 Feb 2020"),
            new Event("Title 7", "description 7",  "19 Feb 2020"),
    };
    String[] data = {
            "1","1","1","1","1","1","1","1","1","1","1"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.dataBaseComm = new DataBaseComm();

        updateEventList();

        setContentView(R.layout.activity_main);

        MyListAdapter adapter = new MyListAdapter(this, data, events);
        list = (ListView) findViewById(R.id.event_list);
        list.setAdapter(adapter);


        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // TODO Auto-generated method stub
                if (position == 0) {
                    //code specific to first list item
//                    Toast.makeText(getApplicationContext(), "Place Your First Option Code", Toast.LENGTH_SHORT).show();
                } else if (position == 1) {
                    //code specific to 2nd list item
                    Toast.makeText(getApplicationContext(), "Place Your Second Option Code", Toast.LENGTH_SHORT).show();
                } else if (position == 2) {

                    Toast.makeText(getApplicationContext(), "Place Your Third Option Code", Toast.LENGTH_SHORT).show();
                } else if (position == 3) {

                    Toast.makeText(getApplicationContext(), "Place Your Forth Option Code", Toast.LENGTH_SHORT).show();
                } else if (position == 4) {

                    Toast.makeText(getApplicationContext(), "Place Your Fifth Option Code", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public void updateEventList() {
        dataBaseComm.readAllEventsAsObjects(new EventListCallback() {
            @Override
            public void onCallBack(ArrayList<Event> list) {
                if (list != null) {
                    eventList = list;
                    System.out.println("\n list size 3: " + eventList.size());
                }
            }
        }, new MessageCallback() {
            @Override
            public void onCallBack(String message) {
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
