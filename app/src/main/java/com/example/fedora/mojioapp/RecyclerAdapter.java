package com.example.fedora.mojioapp;

import android.annotation.TargetApi;
import android.content.Context;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.icu.util.TimeZone;
import android.support.v7.widget.RecyclerView;
import android.icu.text.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import io.moj.java.sdk.model.Trip;

import java.util.List;

/**
 * Created by Fedora on 2016-10-18.
 */
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder>{

    private List<Trip> tripList;
    private Context context;
    private View listItemView, container;
    private ViewHolder viewHolder;
    private ItemClickCallback itemClickCallback;

    public RecyclerAdapter(Context context1, List<Trip> SubjectValues){

        tripList = SubjectValues;
        context = context1;
    }

    @Override
    public RecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){

        listItemView = LayoutInflater.from(context).inflate(R.layout.recycler_list_item, parent, false);

        viewHolder = new ViewHolder(listItemView);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerAdapter.ViewHolder holder, int position) {
        holder.dateTV.setText(getDate(position));
        holder.startLocationTV.setText(tripList.get(position).getStartLocation().getAddress().getFormattedAddress());
        holder.endLocationTV.setText(tripList.get(position).getEndLocation().getAddress().getFormattedAddress());
    }

    @Override
    public int getItemCount() {
        return tripList.size();
    }

    public interface ItemClickCallback {
        void onItemClick(int p);
    }

    public void setItemClickCallback(final ItemClickCallback itemClickCallback) {
        this.itemClickCallback = itemClickCallback;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView startLocationTV, endLocationTV, dateTV;

        public ViewHolder(View v){

            super(v);

            startLocationTV = (TextView)v.findViewById(R.id.start_location);
            endLocationTV = (TextView)v.findViewById(R.id.end_location);
            dateTV = (TextView)v.findViewById(R.id.date);

            container = listItemView.findViewById(R.id.recycler_item_root);
            container.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            itemClickCallback.onItemClick(getAdapterPosition());
        }
    }

    @TargetApi(24)
    private String getDate(int position) {
        DateFormat sdf = new SimpleDateFormat("MMMM dd, yyyy' 'H:mm a");
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(tripList.get(position).getEndTimestamp());
        TimeZone tz = TimeZone.getDefault();
        sdf.setTimeZone(tz);
        return sdf.format(calendar.getTime());
    }

}
