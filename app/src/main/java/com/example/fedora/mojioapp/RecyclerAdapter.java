package com.example.fedora.mojioapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Fedora on 2016-10-18.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder>{

    private List<TripListData> tripList;
    private Context context;
    private View listItemView, container;
    private ViewHolder viewHolder;
    private ItemClickCallback itemClickCallback;

    public RecyclerAdapter(Context context1, List<TripListData> SubjectValues){

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
        holder.dateTV.setText(tripList.get(position).getTripDate());
        holder.startLocationTV.setText(tripList.get(position).getStartLocation());
        holder.endLocationTV.setText(tripList.get(position).getEndLocation());
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
}
