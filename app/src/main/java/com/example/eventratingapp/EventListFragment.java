package com.example.eventratingapp;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.eventratingapp.database.DataBaseCommunication;
import com.example.eventratingapp.database.EventListCallback;
import com.example.eventratingapp.database.MessageCallback;
import com.example.eventratingapp.listeners.OnEventItemClick;
import com.example.eventratingapp.models.Event;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

/**
 * A fragment representing a list of Items.
 */
// TODO clean it up. improve api call. update layout style.
public class EventListFragment extends Fragment implements OnEventItemClick {

    DataBaseCommunication dataBaseCommunication;

    private Context context;
    private MyEventListRecyclerViewAdapter adapter;
    private NavController navController;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public EventListFragment() {}

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static EventListItemFragment newInstance(int columnCount) {
        return new EventListItemFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.dataBaseCommunication = DataBaseCommunication.getInstance();
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_event_list, container, false);
        // Set the adapter
        if (view instanceof RecyclerView) {
            context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            List<Event> events = new ArrayList<>();
            adapter = new MyEventListRecyclerViewAdapter(events, this);
            updateEventList();
            recyclerView.setAdapter(adapter);
        }

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        navController = Navigation.findNavController(view);
        view.findViewById(R.id.addButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                  navController.navigate(R.id.action_event_list_fragment_to_eventCreateFragment);
            }
        });
    }

    public void updateEventList() {
        dataBaseCommunication.readAllEventsAsObjects(new EventListCallback() {
            @Override
            public void onCallBack(ArrayList<Event> list) {
                if (list != null && list.size() > 0) {
                    adapter.setItems(list);
                }
            }
        }, new MessageCallback() {
            @Override
            public void onCallBack(String message) {
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onEventClick(Event event) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("event", event);
        navController.navigate(R.id.action_event_list_fragment_to_event_list_item_fragment, bundle);
        Toast.makeText(context, event.description, Toast.LENGTH_SHORT).show();
    }
}