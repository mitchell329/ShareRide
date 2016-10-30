package com.android.shareride.View;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.shareride.R;
import com.android.shareride.View.PostedFragment.OnListFragmentInteractionListener;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link Trip} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyTripsRecyclerViewAdapter extends RecyclerView.Adapter<MyTripsRecyclerViewAdapter
        .ViewHolder> {

    private final List<Trip> tripList;
    private final OnListFragmentInteractionListener mListener;

    public MyTripsRecyclerViewAdapter(List<Trip> items, OnListFragmentInteractionListener
            listener) {
        tripList = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_trips, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.trip = tripList.get(position);
        holder.mFromView.setText(tripList.get(position).from);
        holder.mToView.setText(tripList.get(position).to);
        holder.mDepartureView.setText(tripList.get(position).date + " " + tripList.get(position).time);

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.trip);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return tripList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mFromView;
        public final TextView mToView;
        public final TextView mDepartureView;
        public com.android.shareride.View.Trip trip;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mFromView = (TextView) view.findViewById(R.id.fromTextView);
            mToView = (TextView) view.findViewById(R.id.toTextView);
            mDepartureView = (TextView) view.findViewById(R.id.departureTextView);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mFromView.getText() + "'";
        }
    }
}
