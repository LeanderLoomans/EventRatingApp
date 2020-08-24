package com.example.eventratingapp;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.eventratingapp.database.DataBaseCommunication;
import com.example.eventratingapp.database.MessageCallback;

import java.util.Calendar;
import java.util.Date;


public class EventCreateFragment extends Fragment {

    private Context context;
    private NavController navController;
    private DataBaseCommunication dataBaseCommunication;


    EditText nameInput;
    EditText descriptionInput;
    TextView startDateInput;
    TextView startTimeInput;
    TextView endDateInput;
    TextView endTimeInput;

    DatePickerDialog.OnDateSetListener onStartDateSetListener;
    TimePickerDialog.OnTimeSetListener onStartTimeSetListener;

    DatePickerDialog.OnDateSetListener onEndDateSetListener;
    TimePickerDialog.OnTimeSetListener onEndTimeSetListener;

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
        startDateInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeDatePickerDialog(onStartDateSetListener);
            }
        });
        onStartDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month++;
                String date = dateItemFormatter(day) + "/" + dateItemFormatter(month) + "/" + year;
                startDateInput.setText(date);
            }
        };

        startTimeInput = view.findViewById(R.id.startTimeInput);
        startTimeInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeTimePickerDialog(onStartTimeSetListener);
            }
        });
        onStartTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hour, int minute) {
                String time = dateItemFormatter(hour) + ":" + dateItemFormatter(minute);
                startTimeInput.setText(time);
            }
        };

        endDateInput = view.findViewById(R.id.endDateInput);
        endDateInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeDatePickerDialog(onEndDateSetListener);
            }
        });
        onEndDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month++;
                String date = dateItemFormatter(day) + "/" + dateItemFormatter(month) + "/" + year;
                endDateInput.setText(date);
            }
        };

        endTimeInput = view.findViewById(R.id.endTimeInput);
        endTimeInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeTimePickerDialog(onEndTimeSetListener);
            }
        });
        onEndTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hour, int minute) {
                String time = dateItemFormatter(hour) + ":" + dateItemFormatter(minute);
                endTimeInput.setText(time);
            }
        };

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
                    startDateInput.getText().toString()
                            + " " + startTimeInput.getText().toString(),
                    endDateInput.getText().toString()
                            + " " + endTimeInput.getText().toString(),
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

    private void makeDatePickerDialog(DatePickerDialog.OnDateSetListener dateSetListener) {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dialog = new DatePickerDialog(
                context,
                android.R.style.Theme_Holo_Dialog_MinWidth,
                dateSetListener,
                year, month, day);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }

    private void makeTimePickerDialog(TimePickerDialog.OnTimeSetListener timeSetListener) {
        Calendar cal = Calendar.getInstance();

        TimePickerDialog dialog = new TimePickerDialog(
                context,
                android.R.style.Theme_Holo_Dialog_MinWidth,
                timeSetListener,
                0, 0,
                true);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }

    private String dateItemFormatter(int dateItem) {
        String item = dateItem + "";
        if (item.length() < 2) {
            item = "0" + item;
        }
        return item;
    }

    private boolean checkFields() {
        if (!(
                nameInput.getText().toString().length() > 0
                        && descriptionInput.getText().toString().length() > 0
                        && startDateInput.getText().toString().length() > 0
                        && endDateInput.getText().toString().length() > 0
                        && startTimeInput.getText().toString().length() > 0
                        && endTimeInput.getText().toString().length() > 0
        )) {
            return false;
        }
        else {
            return true;
        }
    }
}