package com.example.eventratingapp.database;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eventratingapp.R;
import com.example.eventratingapp.models.Event;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataBaseComm extends AppCompatActivity {

    FirebaseFirestore db;

    TextView txtDisplay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = FirebaseFirestore.getInstance();
    }

    /**
     * @param docId the ID of the event you wish to obtain
     * @param eventCallback pass a new instance of EventCallback
     */
    public void readSingleEventObject(String docId, final EventCallback eventCallback) {
        DocumentReference event = db.collection("Events").document(docId);
        event.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

                Event receivedEvent = documentSnapshot.toObject(Event.class);
                eventCallback.onCallBack(receivedEvent);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(
                        DataBaseComm.this,
                        "Failed to acquire event",
                        Toast.LENGTH_SHORT)
                        .show();
            }
        });
    }

    public void readAllEvents(final EventListCallback eventListCallback) {
        CollectionReference event = db.collection("Events");
        event.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    List<Event> eventList = new ArrayList<>();
                    for (DocumentSnapshot document : task.getResult()) {
                        eventList.add(document.toObject(Event.class));
                    }
                    eventListCallback.onCallBack(eventList);
                }
                else {
                    Toast.makeText(
                            DataBaseComm.this,
                            "Failed to acquire events",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void readSingleEvent() {
        DocumentReference event = db.collection("Events").document("1");
        event.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()) {
                    DocumentSnapshot doc = task.getResult();
                    StringBuilder data = new StringBuilder("");
                    data.append("Naam: ").append(doc.getString("Name"));
                    data.append("\nOmschrijving: ").append(doc.getString("Description"));
                    data.append("\nDatum: ").append(doc.getString("Date"));
                    txtDisplay.setText(data.toString());


//                    newEvent.put("Description", "Vieren overwinning IKPMD");
//                    newEvent.put("Date", "25-01-2020 16:30 tot 25-01-2020 18:00");
                }
            }
        });
    }

    public void addNewEvent(String title, String description, Date date) {
        // Add new Evenement to Evenementlijst

        Map<String, Object> newEvent = new HashMap<>();
        newEvent.put("title", title);
        newEvent.put("description", description);
        newEvent.put("date", date.toString());

        db  .collection("Events")
                .document("1")
                .set(newEvent)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(
                                DataBaseComm.this,
                                "Added new event",
                                Toast.LENGTH_SHORT)
                                .show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(
                                DataBaseComm.this,
                                "Failed to add new event",
                                Toast.LENGTH_SHORT)
                                .show();
                    }
                });
    }
}
