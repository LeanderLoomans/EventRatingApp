package com.example.eventratingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eventratingapp.database.DataBaseCommunication;
import com.example.eventratingapp.database.MessageCallback;

import java.util.Date;

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

    private void onClickCreate() {
        if (checkFields()) {
            DataBaseCommunication.getInstance().addNewEvent(
                    nameInput.getText().toString(),
                    descriptionInput.getText().toString(), new Date(), new MessageCallback() {
                        @Override
                        public void onCallBack(String message) {
                            Toast.makeText(getApplicationContext(),message, Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    });
        }
        else {
            Toast.makeText(getApplicationContext(), "Incorrecte waarden opgegeven", Toast.LENGTH_SHORT).show();
        }
    }

    private void navigateBack() {

    }
}