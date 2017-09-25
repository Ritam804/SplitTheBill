package com.ritam.splitthebill.splitthebill.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.ritam.splitthebill.splitthebill.R;
import com.ritam.splitthebill.splitthebill.activity.EventDetails;
import com.ritam.splitthebill.splitthebill.apphelper.AppData;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.ExecutionException;

import setergeter.EventList_SetterGetter;

/**
 * Created by su on 13/4/17.
 */

public class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.ViewHolder> {

    Context context;
    ArrayList<EventList_SetterGetter> EventListArray;

    public EventsAdapter(Context context ,ArrayList<EventList_SetterGetter> eventListArray ) {
        this.context = context;
        this.EventListArray = eventListArray;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.row_eventlist,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {


        Log.v("EventDate::",EventListArray.get(position).getEventDate());
        Log.v("EventDate::",parseDateToddMMyyyy(EventListArray.get(position).getEventDate()));

        holder.EventName.setText(EventListArray.get(position).getEventName());
        holder.EventDate.setText(parseDateToddMMyyyy(EventListArray.get(position).getEventDate()));
        holder.VenueName.setText(EventListArray.get(position).getVenueName());
        holder.FollowerCount.setText(EventListArray.get(position).getAddress());

        try {

            Log.v("EventImage::",EventListArray.get(position).getEventImage());

            Picasso.with(context).load(EventListArray.get(position).getEventImage()).fit().into(holder.EventImage);


        }catch (Exception e){

        }




        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context, EventDetails.class);
                AppData.EventId = EventListArray.get(position).getEventId();
                intent.putExtra("FromPrevious",false);
                intent.putExtra("FromPage","Event");
                AppData.FromPage = "Event";
                context.startActivity(intent);


            }
        });

    }

    @Override
    public int getItemCount() {
        return EventListArray.size();
    }

    public String parseDateToddMMyyyy(String time) {
        String inputPattern = "yyyy-MM-dd hh:mm:ss";
        String outputPattern = "dd MMMM yyyy";
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

        Date date = null;
        String str = null;

        try {
            date = inputFormat.parse(time);
            str = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return str;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        View view;

        ImageView EventImage;
        TextView EventName,EventDate,VenueName,FollowerCount;
        RatingBar EventRating;

        public ViewHolder(View itemView) {
            super(itemView);

            view = itemView;

            EventName = (TextView) itemView.findViewById(R.id.event_name);
            EventDate = (TextView) itemView.findViewById(R.id.event_date);
            VenueName = (TextView) itemView.findViewById(R.id.venue_name);
            FollowerCount = (TextView) itemView.findViewById(R.id.tv_followercount);
            EventRating = (RatingBar) itemView.findViewById(R.id.rating_bar);

            EventImage = (ImageView) itemView.findViewById(R.id.eventimage);

        }
    }
}
