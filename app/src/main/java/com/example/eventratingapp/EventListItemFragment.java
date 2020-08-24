package com.example.eventratingapp;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.eventratingapp.models.Event;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class EventListItemFragment extends Fragment implements View.OnClickListener {

    private EventViewModel mViewModel;
    private Event event;
    private NavController navController;

    public static EventListItemFragment newInstance() {
        return new EventListItemFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.event_fragment, container, false);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(EventViewModel.class);
        // TODO: Use the ViewModel
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);


        assert getArguments() != null;
        event = (Event) getArguments().getParcelable("event");

        ((TextView ) view.findViewById(R.id.event_detail_name)).setText(event.name);
        ((TextView ) view.findViewById(R.id.event_detail_description)).setText(event.description);
        ((TextView ) view.findViewById(R.id.event_detail_start_date)).setText(event.dateFormatted());

        // render ratings
        ((TextView ) view.findViewById(R.id.event_detail_rating_green)).setText(String.valueOf(event.rating.green.getCounter()));
        ((TextView ) view.findViewById(R.id.event_detail_rating_yellow)).setText(String.valueOf(event.rating.yellow.getCounter()));
        ((TextView ) view.findViewById(R.id.event_detail_rating_red)).setText(String.valueOf(event.rating.red.getCounter()));

        FloatingActionButton actionButton = ((FloatingActionButton) view.findViewById(R.id.add_rating));
        actionButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("event", event);
        navController.navigate(R.id.action_event_list_item_fragment_to_event_rating_fragment, bundle);
    }


}