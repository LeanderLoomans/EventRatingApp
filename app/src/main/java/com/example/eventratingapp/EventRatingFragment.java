package com.example.eventratingapp;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.eventratingapp.database.DataBaseCommunication;
import com.example.eventratingapp.models.Counter;
import com.example.eventratingapp.models.Event;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EventRatingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EventRatingFragment extends Fragment {


    private Event event;
    private NavController navController;
    private DataBaseCommunication dataBaseCommunication;
    private Context context;

    public EventRatingFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EventRatingFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EventRatingFragment newInstance(String param1, String param2) {
        return new EventRatingFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_event_rating, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        dataBaseCommunication = DataBaseCommunication.getInstance();
        context = view.getContext();
        assert getArguments() != null;
        event = (Event) getArguments().getParcelable("event");

        ((Button) view.findViewById(R.id.green_button)).setOnClickListener(v -> onRatingButtonClick(event.rating.green));
        ((Button) view.findViewById(R.id.yellow_button)).setOnClickListener(v -> onRatingButtonClick(event.rating.yellow));
        ((Button) view.findViewById(R.id.red_button)).setOnClickListener(v ->  onRatingButtonClick(event.rating.red));
    }

    public void onRatingButtonClick(Counter counter){


        counter.increase(1);
        Bundle bundle = new Bundle();
        bundle.putParcelable("event", event);
        dataBaseCommunication.updateEvent(event, message -> {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
            navController.navigate(R.id.action_event_rating_fragment_to_event_list_item_fragment, bundle);
        });
    }
}