package com.example.eventratingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class AddEventActivity extends AppCompatActivity {

    EditText nameInput;
    EditText descriptionInput;
    EditText startDateInput;
    EditText endDateInput;
    Button createButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);

        nameInput = findViewById(R.id.nameInput);
        descriptionInput = findViewById(R.id.descriptionInput);
        startDateInput = findViewById(R.id.startDateInput);
        endDateInput = findViewById(R.id.endDateInput);
        createButton = findViewById(R.id.createButton);
        createButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                onClickCreate();
            }
        });
    }
    private boolean checkFields() {
        if (!(
                nameInput.getText().length() > 0
                && descriptionInput.getText().length() > 0
                && startDateInput.getText().length() > 0
                && endDateInput.getText().length() > 0
        )) {
            return false;
        }
        else {
            return true;
        }
    }

    private void onClickCreate() {
        if (checkFields()) {
            System.out.println("Aangemaakte event: \n" + "naam: " + nameInput.getText() + "\nomschrijving: " + descriptionInput.getText() + "\ndatestart: " + startDateInput + "\ndateEnd: " + endDateInput.getText() );
        }
        else {
            Toast.makeText(getApplicationContext(), "Sommige velden zijn incorrect ingevuld", Toast.LENGTH_LONG);
        }
    }
}