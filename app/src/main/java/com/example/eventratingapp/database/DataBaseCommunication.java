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
import java.util.Map;

public class DataBaseCommunication {

    FirebaseFirestore db;

    public DataBaseCommunication() {
        db = FirebaseFirestore.getInstance();
    }

    /**
     * @param docId the ID of the event you wish to obtain
     * @param eventCallback pass a new instance of EventCallback
     */
    public void readSingleEventObject(String docId, final EventCallback eventCallback, final MessageCallback messageCallback) {
        final DocumentReference event = db.collection("Events").document(docId);
        event.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Event receivedEvent = documentSnapshot.toObject(Event.class);
                System.out.println("\n receivedEvent: \n" + receivedEvent);
                eventCallback.onCallBack(receivedEvent);
                messageCallback.onCallBack("Successfully retrieved event");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                eventCallback.onCallBack(null);
                messageCallback.onCallBack("Failed to retrieve event data");
            }
        });
    }

    /**
     * @param eventListCallback Callback to receive list of events
     * @param messageCallback Callback to display feedback message
     */
    public void readAllEventsAsObjects(final EventListCallback eventListCallback, final MessageCallback messageCallback) {
        CollectionReference event = db.collection("Events");
        event.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    ArrayList<Event> eventList = new ArrayList<>();
                    try {
                        for (DocumentSnapshot document : task.getResult()) {
                            eventList.add(document.toObject(Event.class));
                        }
                        eventListCallback.onCallBack(eventList);
                        messageCallback.onCallBack("Successfully retrieved events");
                    } catch (Exception e) {
                        eventListCallback.onCallBack(null);
                        messageCallback.onCallBack("Failed to retrieve events");
                        throw e;
                    }
                }
                else {
                    eventListCallback.onCallBack(null);
                    messageCallback.onCallBack("Failed to retrieve events");
                }
            }
        });
    }

    /**
     * @param title Title of the new event
     * @param description Description of the new event
     * @param date Date that the new event will take place
     * @param messageCallback Callback to give user feedback
     */
    public void addNewEvent(String title, String description, Date date, final MessageCallback messageCallback) {

        Map<String, Object> newEvent = new HashMap<>();
        newEvent.put("title", title);
        newEvent.put("description", description);
        newEvent.put("date", date.toString());

        db  .collection("Events")
                .document()
                .set(newEvent)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        messageCallback.onCallBack("Added new event");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        messageCallback.onCallBack("Failed to add event");
                    }
                });
    }
}
