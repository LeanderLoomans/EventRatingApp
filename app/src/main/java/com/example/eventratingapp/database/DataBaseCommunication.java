package com.example.eventratingapp.database;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.eventratingapp.models.Event;
import com.example.eventratingapp.models.EventRating;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DataBaseCommunication {

    static DataBaseCommunication instance = new DataBaseCommunication();
    private static Map<String, Map> rating = createRatingMap( new EventRating());
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/YYY HH:mm");

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

                            Log.d("dumpAhs", document.getData().toString());
                            Event eventPojo = new Event();
                            eventPojo.id = document.getId();
                            eventPojo.startDate = dateFormat.parse(document.getString("startDate"));
                            eventPojo.endDate = dateFormat.parse(document.getString("endDate"));
                            eventPojo.description = document.getString("description");
                            eventPojo.name = document.getString("name");
                            eventPojo.rating = new EventRating(
                                    (int) (long) document.get("rating.green.counter"),
                                    (int) (long) document.get("rating.red.counter"),
                                    (int) (long) document.get("rating.yellow.counter")
                            );
                            eventList.add(eventPojo);
                        }
                        eventListCallback.onCallBack(eventList);
                        messageCallback.onCallBack("Successfully retrieved events");
                    } catch (Exception e) {
                        eventListCallback.onCallBack(null);
                        messageCallback.onCallBack("Failed to retrieve events");
                        e.printStackTrace();
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
        newEvent.put("rating", rating);
        newEvent.put("startDate", startDate.trim());
        newEvent.put("endDate", endDate.trim());


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

    private static Map<String, Map> createRatingMap(EventRating rating) {
        Map<String, Map> ratingTemplate = new HashMap<>();
        Map<String, Integer> green = new HashMap<>();
        green.put("counter", rating.green.counter);
        Map<String, Integer> red = new HashMap<>();
        red.put("counter", rating.red.counter);
        Map<String, Integer> yellow = new HashMap<>();
        yellow.put("counter", rating.yellow.counter);
        ratingTemplate.put("green", green);
        ratingTemplate.put("red", red);
        ratingTemplate.put("yellow", yellow);
        return ratingTemplate;
    }

    public void updateEvent(Event event, MessageCallback callback) {
        Map<String, Object> updatedEvent = convertEventToMap(event);
        db.collection("Events").document(event.id).update(updatedEvent).addOnCompleteListener(task -> {
            if(task.isSuccessful()) {
                callback.onCallBack("Event updated");
            }
        });
    }

    private Map<String, Object> convertEventToMap(Event event) {
        Map<String, Object> eventMap = new HashMap<>();
        eventMap.put("name", event.name);
        eventMap.put("description", event.description);
        eventMap.put("rating", event.rating);
        eventMap.put("startDate", dateFormat.format(event.startDate));
        eventMap.put("endDate", dateFormat.format(event.endDate));

        return eventMap;
    }
}
