package com.example.eventratingapp.models;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Event POJO object
 */
public class Event implements Parcelable {

    public String id;
    public String name;
    public String description;
    public Date startDate;
    public Date endDate;
    public EventRating rating;

    private static SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yy hh:mm", Locale.ENGLISH);

    public Event(){}

    public Event(String name, String description, Date startDate, Date endDate, EventRating rating){
        this.name = name;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.rating = rating;
    }


    protected Event(Parcel in) {
        id = in.readString();
        name = in.readString();
        description = in.readString();
        long tmpStartDate = in.readLong();
        startDate = tmpStartDate != -1 ? new Date(tmpStartDate) : null;
        long tmpEndDate = in.readLong();
        endDate = tmpEndDate != -1 ? new Date(tmpEndDate) : null;
        rating = (EventRating) in.readValue(EventRating.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(description);
        dest.writeLong(startDate != null ? startDate.getTime() : -1L);
        dest.writeLong(endDate != null ? endDate.getTime() : -1L);
        dest.writeValue(rating);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Event> CREATOR = new Parcelable.Creator<Event>() {
        @Override
        public Event createFromParcel(Parcel in) {
            return new Event(in);
        }

        @Override
        public Event[] newArray(int size) {
            return new Event[size];
        }
    };

    public String dateFormatted() {
        return Event.dateFormat.format(this.startDate);
    }
}