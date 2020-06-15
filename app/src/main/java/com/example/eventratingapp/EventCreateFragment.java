package com.example.eventratingapp;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.eventratingapp.database.DataBaseCommunication;
import com.example.eventratingapp.database.MessageCallback;

import java.util.Date;


public class EventCreateFragment extends Fragment {

    private Context context;
    private NavController navController;
    private DataBaseCommunication dataBaseCommunication;


    EditText nameInput;
    EditText descriptionInput;
    EditText startDateInput;
    EditText endDateInput;
    Button createButton;

    public EventCreateFragment() {
        // Required empty public constructor
    }

    public static EventCreateFragment newInstance() {

        return new EventCreateFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_event_create, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        context = view.getContext();
        navController = Navigation.findNavController(view);
        dataBaseCommunication = DataBaseCommunication.getInstance();

        nameInput = view.findViewById(R.id.nameInput);
        descriptionInput = view.findViewById(R.id.descriptionInput);
        startDateInput = view.findViewById(R.id.startDateInput);
        endDateInput = view.findViewById(R.id.endDateInput);
        createButton = view.findViewById(R.id.createButton);
        createButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                onClickCreate();
            }
        });
    }

    private void onClickCreate() {
        if (checkFields()) {
            DataBaseCommunication.getInstance().addNewEvent(
                    nameInput.getText().toString(),
                    descriptionInput.getText().toString(),
                    startDateInput.getText().toString(),
                    endDateInput.getText().toString(),
                    new MessageCallback() {
                        @Override
                        public void onCallBack(String message) {
                            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                            getActivity().onBackPressed();
                        }
                    });
        }
        else {
            Toast.makeText(context, "Incorrecte waarden opgegeven", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean checkFields() {
        if (!(
                nameInput.getText().toString().length() > 0
                        && descriptionInput.getText().toString().length() > 0
                        && startDateInput.getText().toString().length() > 0
                        && endDateInput.getText().toString().length() > 0
        )) {
            return false;
        }
        else {
            return true;
        }
    }
}