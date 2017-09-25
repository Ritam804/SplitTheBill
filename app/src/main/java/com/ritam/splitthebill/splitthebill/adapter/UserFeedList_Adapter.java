package com.ritam.splitthebill.splitthebill.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.ritam.splitthebill.splitthebill.R;
import com.ritam.splitthebill.splitthebill.activity.EventDetails;
import com.ritam.splitthebill.splitthebill.activity.EventFullDetails;
import com.ritam.splitthebill.splitthebill.activity.OthersProfile;
import com.ritam.splitthebill.splitthebill.apphelper.AppData;
import com.ritam.splitthebill.splitthebill.apphelper.RoundedTransformation;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import setergeter.FeedList_SetterGetter;

/**
 * Created by ritam on 19/07/17.
 */

public class UserFeedList_Adapter extends RecyclerView.Adapter<UserFeedList_Adapter.ViewHolder> {


    ArrayList<FeedList_SetterGetter> FeedListDetails;
    Context context;

    public UserFeedList_Adapter(ArrayList<FeedList_SetterGetter> feedListDetails, Context context) {
        FeedListDetails = feedListDetails;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_profile_follower_following, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {


        SharedPreferences sharedPreferences = context.getSharedPreferences("AutoLogin", context.MODE_PRIVATE);

        if (sharedPreferences.getString("UserId", "").equals(FeedListDetails.get(position).getHostId())) {

            holder.Price.setVisibility(View.VISIBLE);
            holder.HostText.setVisibility(View.VISIBLE);

        } else {

            holder.HostText.setVisibility(View.GONE);

            if (sharedPreferences.getString("UserId", "").equals(FeedListDetails.get(position).getUserId())) {

                holder.Price.setVisibility(View.VISIBLE);

            } else {

                holder.Price.setVisibility(View.GONE);

            }


        }

        holder.UserName.setText(FeedListDetails.get(position).getFolloName());
        holder.EventName.setText(FeedListDetails.get(position).getEventName());
        holder.EventRating.setRating(Float.parseFloat(String.valueOf(FeedListDetails.get(position).getRating())));
        holder.EventDate.setText(FeedListDetails.get(position).getBookedDate());
        holder.TableName.setText(FeedListDetails.get(position).getTableName());
        holder.Price.setText("$ " + FeedListDetails.get(position).getPayment());
        holder.SplitsCount.setText(FeedListDetails.get(position).getSplitsCount());

        Picasso.with(context)
                .load(FeedListDetails.get(position).getFollowImage())
                .placeholder(R.drawable.appicon_round)
                .error(R.drawable.appicon_round)
                .transform(new RoundedTransformation())
                .into(holder.UserImage);


        holder.UserImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context, OthersProfile.class);
                intent.putExtra("UserId", FeedListDetails.get(position).getUserId());
                context.startActivity(intent);

            }
        });

        holder.UserName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context, OthersProfile.class);
                intent.putExtra("UserId", FeedListDetails.get(position).getUserId());
                context.startActivity(intent);

            }
        });

        holder.TotalView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (FeedListDetails.get(position).getExpireStatus().equals("1")){

                    AppData.EventId = FeedListDetails.get(position).getEventId();
                    AppData.FromPage = "Booking";
                    Intent intent = new Intent(context,EventDetails.class);
                    intent.putExtra("FromPrevious",false);
                    context.startActivity(intent);

                }else {

                    AppData.EventId = FeedListDetails.get(position).getEventId();
                    AppData.TableId = FeedListDetails.get(position).getTableId();
                    Intent intent = new Intent(context, EventFullDetails.class);
//                intent.putExtra("FromPrevious", false);
//                AppData.FromPage = "Booking";
                    context.startActivity(intent);

                }


            }
        });


    }

    @Override
    public int getItemCount() {
        return FeedListDetails.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView UserName, EventName, EventDate, TableName, Price, SplitsCount, HostText;
        RatingBar EventRating;
        ImageView UserImage;

        View TotalView;

        public ViewHolder(View itemView) {
            super(itemView);

            UserName = (TextView) itemView.findViewById(R.id.tv_name);
            EventName = (TextView) itemView.findViewById(R.id.title);
            EventRating = (RatingBar) itemView.findViewById(R.id.rating);
            EventDate = (TextView) itemView.findViewById(R.id.tv_eventdate);
            TableName = (TextView) itemView.findViewById(R.id.tv_tableno);
            Price = (TextView) itemView.findViewById(R.id.tv_price);
            UserImage = (ImageView) itemView.findViewById(R.id.iv_userimage);
            SplitsCount = (TextView) itemView.findViewById(R.id.tv_splitscount);
            HostText = (TextView) itemView.findViewById(R.id.tv_host);

            TotalView = itemView;

        }
    }

}
