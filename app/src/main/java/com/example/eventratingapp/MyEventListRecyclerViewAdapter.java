package com.example.eventratingapp;

import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.eventratingapp.listeners.OnEventItemClick;
import com.example.eventratingapp.models.Event;

import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * {@link RecyclerView.Adapter} that can display a {@link Event}.
 */
public class MyEventListRecyclerViewAdapter extends RecyclerView.Adapter<MyEventListRecyclerViewAdapter.ViewHolder> {

    private final List<Event> mValues;
    private OnEventItemClick eventClickListener;

    public MyEventListRecyclerViewAdapter(List<Event> items, OnEventItemClick EventClickListener) {
        mValues = items;
        eventClickListener = EventClickListener;
    }
    private Context context;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_event_list_item, parent, false);
        return new ViewHolder(view, this.eventClickListener);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mTitleView.setText(mValues.get(position).name);
        holder.mDescriptionView.setText(mValues.get(position).description);
        holder.mStartDateView.setText(mValues.get(position).dateFormatted().substring(0, 2));
        holder.mStartDateMonthView.setText(mValues.get(position).dateFormatted().substring(3, 6));
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public void setItems(List<Event> events) {
        this.mValues.clear();
        this.mValues.addAll(events);
        this.notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public View mView;
        public Event mItem;
        public TextView mTitleView;
        public TextView mDescriptionView;
        public TextView mStartDateView;
        public TextView mStartDateMonthView;
        private OnEventItemClick clickListener;

        public ViewHolder(View view, OnEventItemClick clickListener) {
            super(view);
            mView = view;
            mTitleView = (TextView) view.findViewById(R.id.event_title);
            mDescriptionView = (TextView) view.findViewById(R.id.event_description);
            mStartDateView = (TextView) view.findViewById(R.id.event_start_date);
            mStartDateMonthView = (TextView) view.findViewById(R.id.event_start_date_month);
            this.clickListener = clickListener;
            view.setOnClickListener(this);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mDescriptionView.getText() + "'";
        }

        @Override
        public void onClick(View v) {
            this.clickListener.onEventClick(mItem);
        }
    }
}