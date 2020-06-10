package com.example.eventratingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.eventratingapp.database.DataBaseCommunication;
import com.example.eventratingapp.database.EventListCallback;
import com.example.eventratingapp.database.MessageCallback;
import com.example.eventratingapp.models.Event;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    DataBaseCommunication dataBaseCommunication;

    ListView list;

    ArrayList<Event> eventList = new ArrayList<>();

    String[] data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.dataBaseCommunication = new DataBaseCommunication();

        updateEventList();
    }

    public void makeView() {
        setContentView(R.layout.activity_main);

        MyListAdapter adapter = new MyListAdapter(this, data, eventList.toArray(new Event[0]));
        list = (ListView) findViewById(R.id.event_list);
        list.setAdapter(adapter);


        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // TODO Auto-generated method stub
                if (position == 0) {
                    //code specific to first list item
                    Toast.makeText(getApplicationContext(), "Place Your First Option Code", Toast.LENGTH_SHORT).show();
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
        dataBaseCommunication.readAllEventsAsObjects(new EventListCallback() {
            @Override
            public void onCallBack(ArrayList<Event> list) {
                if (list != null && list.size() > 0) {
                    eventList = list;
                    fillData();
                    setContentView(R.layout.activity_main);
                    makeView();
                }
            }
        }, new MessageCallback() {
            @Override
            public void onCallBack(String message) {
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void fillData() {
        String[] arr = new String[eventList.size()];
        Arrays.fill(arr, "1");
        this.data = arr;
    }

}
