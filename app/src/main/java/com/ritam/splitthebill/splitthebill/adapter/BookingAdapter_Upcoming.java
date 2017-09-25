package com.ritam.splitthebill.splitthebill.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ritam.splitthebill.splitthebill.R;
import com.ritam.splitthebill.splitthebill.activity.EventDetails;
import com.ritam.splitthebill.splitthebill.activity.EventFullDetails;
import com.ritam.splitthebill.splitthebill.activity.OthersProfile;
import com.ritam.splitthebill.splitthebill.apphelper.AppData;
import com.ritam.splitthebill.splitthebill.fragments.Booking;
import com.ritam.splitthebill.splitthebill.fragments.Profile;

import java.util.ArrayList;

import setergeter.Booking_SetterGetter;

/**
 * Created by su on 17/4/17.
 */

public class BookingAdapter_Upcoming extends RecyclerView.Adapter<BookingAdapter_Upcoming.Viewholder> {

    ArrayList<Booking_SetterGetter> BookingValues;
    Context context;
    Booking booking;
    String Status;
    int Page = 1;
    boolean FromPrevious;
    Profile profile;
    OthersProfile othersProfile;
    String FromPage;

    public BookingAdapter_Upcoming(ArrayList<Booking_SetterGetter> bookingValues, Context context , Booking booking, String Status, boolean fromprevious, String frompage) {
        BookingValues = bookingValues;
        this.context = context;
        this.booking = booking;
        this.Status = Status;
        this.FromPrevious = fromprevious;
        this.FromPage = frompage;
    }

    public BookingAdapter_Upcoming(ArrayList<Booking_SetterGetter> bookingValues, Context context , Profile profile, String Status, boolean fromprevious, String frompage) {
        BookingValues = bookingValues;
        this.context = context;
        this.profile = profile;
        this.Status = Status;
        this.FromPrevious = fromprevious;
        this.FromPage = frompage;
    }

    public BookingAdapter_Upcoming(ArrayList<Booking_SetterGetter> bookingValues, Context context , OthersProfile othersProfile, String Status, boolean fromprevious, String frompage) {
        BookingValues = bookingValues;
        this.context = context;
        this.othersProfile = othersProfile;
        this.Status = Status;
        this.FromPrevious = fromprevious;
        this.FromPage = frompage;
    }

    @Override
    public Viewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.recyler_booking_layout,parent,false);

        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(Viewholder holder, final int position) {


//        if (Status.equals("3")){
//
//            holder.ConfirmedSign.setVisibility(View.GONE);
//            holder.PastSign.setVisibility(View.GONE);
//            holder.PendingSign.setVisibility(View.VISIBLE);
//
//
//        }else if (Status.equals("1")){
//
//            holder.PendingSign.setVisibility(View.GONE);
//            holder.PastSign.setVisibility(View.GONE);
//            holder.ConfirmedSign.setVisibility(View.VISIBLE);
//
//        }else if (Status.equals("2")){
//
//            holder.PendingSign.setVisibility(View.GONE);
//            holder.ConfirmedSign.setVisibility(View.GONE);
//            holder.PastSign.setVisibility(View.VISIBLE);
//
//        }

        holder.EventName.setText(BookingValues.get(position).getEventName());
        holder.VenueName.setText(BookingValues.get(position).getVenueName());
        holder.VenueAddress.setText(BookingValues.get(position).getVenueAddress());
        holder.ReferenceId.setText(BookingValues.get(position).getReferenceId());
        holder.TableName.setText(BookingValues.get(position).getTableName());
        holder.EventMonth.setText(BookingValues.get(position).getEventMonth());
        holder.EventDate.setText(BookingValues.get(position).getEventDate());
        holder.EventTime.setText(BookingValues.get(position).getEventTime());


        if (BookingValues.size() >= 10){

            if (position > BookingValues.size() - 2){

                Page = Page + 1;

                if (FromPage.equals("Booking")){

                    booking.BookingHistory(Status,String.valueOf(Page));

                }else if (FromPage.equals("Profile")){

                    profile.BookingHistory(Status,String.valueOf(Page));

                }else if (FromPage.equals("OtherProfile")){

                    othersProfile.BookingHistory(Status,String.valueOf(Page));
                }



            }

        }

        holder.TotalView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


//                Intent intent = new Intent(context, EventDetails.class);
//                AppData.EventId = BookingValues.get(position).getEventId();
//                intent.putExtra("FromPrevious",FromPrevious);
//                intent.putExtra("FromPage","Booking");
//                AppData.FromPage = "Booking";
//                context.startActivity(intent);

                if (Status.equals("2")){

                    AppData.EventId = BookingValues.get(position).getEventId();
                    AppData.FromPage = "Booking";
                    Intent intent = new Intent(context,EventDetails.class);
                    intent.putExtra("FromPrevious",false);
                    context.startActivity(intent);


                }else {

                    if (Status.equals("1")){

                        Intent intent = new Intent(context,EventFullDetails.class);
                        intent.putExtra("Table_Id",BookingValues.get(position).getTableId());
                        intent.putExtra("Host_Name",BookingValues.get(position).getHostName());
                        intent.putExtra("Host_Id",BookingValues.get(position).getHostId());
                        intent.putExtra("Event_Id",BookingValues.get(position).getEventId());
                        intent.putExtra("TotalSeat",BookingValues.get(position).getTotalNoOfSeat());
                        intent.putExtra("AvailableSeat",BookingValues.get(position).getNumberOfAvailableSeat());
                        intent.putExtra("TableCost",BookingValues.get(position).getMaximumMount());
                        intent.putExtra("GroupId",BookingValues.get(position).getGroupId());
                        intent.putExtra("Purpose","Upcoming");
                        AppData.TableId = BookingValues.get(position).getTableId();
                        AppData.EventId = BookingValues.get(position).getEventId();
                        context.startActivity(intent);

                    }else {

                        Intent intent = new Intent(context,EventFullDetails.class);
                        intent.putExtra("Table_Id",BookingValues.get(position).getTableId());
                        intent.putExtra("Host_Name",BookingValues.get(position).getHostName());
                        intent.putExtra("Host_Id",BookingValues.get(position).getHostId());
                        intent.putExtra("Event_Id",BookingValues.get(position).getEventId());
                        intent.putExtra("TotalSeat",BookingValues.get(position).getTotalNoOfSeat());
                        intent.putExtra("AvailableSeat",BookingValues.get(position).getNumberOfAvailableSeat());
                        intent.putExtra("TableCost",BookingValues.get(position).getMaximumMount());
                        intent.putExtra("GroupId",BookingValues.get(position).getGroupId());
                        intent.putExtra("Purpose","Pending");
                        AppData.TableId = BookingValues.get(position).getTableId();
                        AppData.EventId = BookingValues.get(position).getEventId();
                        context.startActivity(intent);

                    }



                }






            }
        });


    }

    @Override
    public int getItemCount() {
        return BookingValues.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {

        TextView EventName,VenueName,VenueAddress,ReferenceId,TableName,EventMonth,EventDate,EventTime;
        ImageView PendingSign,ConfirmedSign,PastSign;
        View TotalView;

        public Viewholder(View itemView) {
            super(itemView);

            EventName = (TextView) itemView.findViewById(R.id.tv_eventname);
            VenueName = (TextView) itemView.findViewById(R.id.tv_venuename);
            VenueAddress = (TextView) itemView.findViewById(R.id.tv_venueaddress);
            ReferenceId = (TextView) itemView.findViewById(R.id.tv_referenceid);
            TableName = (TextView) itemView.findViewById(R.id.tv_tablename);
            EventMonth = (TextView) itemView.findViewById(R.id.tv_eventmonth);
            EventDate = (TextView) itemView.findViewById(R.id.tv_eventdate);
            EventTime = (TextView) itemView.findViewById(R.id.tv_eventtime);
            PendingSign = (ImageView) itemView.findViewById(R.id.iv_pending);
            ConfirmedSign = (ImageView) itemView.findViewById(R.id.iv_confirmed);
            PastSign = (ImageView) itemView.findViewById(R.id.iv_past);

            TotalView = itemView;


        }
    }

    public void LoadMore(ArrayList<Booking_SetterGetter> extra){

        BookingValues.addAll(extra);
        notifyDataSetChanged();

    }
}
