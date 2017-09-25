package com.ritam.splitthebill.splitthebill.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.ritam.splitthebill.splitthebill.R;
import com.ritam.splitthebill.splitthebill.activity.EventRating;
import com.ritam.splitthebill.splitthebill.apphelper.RoundedTransformation;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import setergeter.EventRating_Splitters_SetterGetter;

/**
 * Created by ritam on 19/07/17.
 */

public class EventRating_Splitters_Adapter extends RecyclerView.Adapter<EventRating_Splitters_Adapter.ViewHolder>{


    ArrayList<EventRating_Splitters_SetterGetter> SplittersDetails;
    Context context;
    EventRating eventRating;
    int EventRatedStatus;


    public EventRating_Splitters_Adapter(ArrayList<EventRating_Splitters_SetterGetter> splittersDetails, Context context, EventRating eventRating,int eventratedstatus) {
        SplittersDetails = splittersDetails;
        this.context = context;
        this.eventRating = eventRating;
        EventRatedStatus = eventratedstatus;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_eventrating_splitters, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        Picasso.with(context)
                .load(SplittersDetails.get(position).getUserImage())
                .placeholder(R.drawable.appicon_round)
                .error(R.drawable.appicon_round)
                .transform(new RoundedTransformation())
                .into(holder.UserImage);

        if (EventRatedStatus == 1){

            holder.UserRating.setIsIndicator(true);

        }else {

            holder.UserRating.setIsIndicator(false);

        }

        holder.UserName.setText(SplittersDetails.get(position).getUserName());

        holder.UserRating.setRating(Float.parseFloat(String.valueOf(SplittersDetails.get(position).getUserRating())));

        holder.UserRating.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {

                eventRating.EventRatingSplittersDetails.get(position).setUserRating((int)v);

            }
        });


    }

    @Override
    public int getItemCount() {
        return SplittersDetails.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{


        ImageView UserImage;

        TextView UserName;

        RatingBar UserRating;

        public ViewHolder(View itemView) {
            super(itemView);

            UserImage = (ImageView) itemView.findViewById(R.id.iv_eventrating_splittersimage);

            UserName = (TextView) itemView.findViewById(R.id.tv_eventrating_username);

            UserRating = (RatingBar) itemView.findViewById(R.id.row_eventrating_rating_bar);

        }
    }
}
