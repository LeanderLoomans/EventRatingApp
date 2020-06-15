package com.example.eventratingapp.database;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.sax.EndElementListener;
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

    static DataBaseCommunication instance = new DataBaseCommunication();
    private static Map<String, Map> rating = createRatingMap();


    private DataBaseCommunication() { }

    public static DataBaseCommunication getInstance() {
        return instance;
    }


    FirebaseFirestore db = FirebaseFirestore.getInstance();

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
     *
     * @param name
     * @param description
     * @param startDate
     * @param endDate
     * @param messageCallback
     */
    public void addNewEvent(String name, String description, String startDate, String endDate, final MessageCallback messageCallback) {

        //fill a new event object
        Map<String, Object> newEvent = new HashMap<>();
        newEvent.put("name", name);
        newEvent.put("description", description);
        newEvent.put("date", new Date().toString());
        newEvent.put("rating", rating);
        newEvent.put("startDate", startDate);
        newEvent.put("endDate", endDate);


        //send new event to DB
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

    private static Map<String, Map> createRatingMap() {
        Map<String, Map> ratingTemplate = new HashMap<>();
        Map<String, Integer> green = new HashMap<>();
        green.put("counter", 0);
        Map<String, Integer> red = new HashMap<>();
        red.put("counter", 0);
        Map<String, Integer> yellow = new HashMap<>();
        yellow.put("counter", 0);
        ratingTemplate.put("green", green);
        ratingTemplate.put("red", red);
        ratingTemplate.put("yellow", yellow);
        return ratingTemplate;
    }
}
