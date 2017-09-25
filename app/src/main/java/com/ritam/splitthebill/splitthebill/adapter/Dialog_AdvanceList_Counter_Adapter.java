package com.ritam.splitthebill.splitthebill.adapter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ritam.splitthebill.splitthebill.R;
import com.ritam.splitthebill.splitthebill.activity.BookingAsHost;
import com.ritam.splitthebill.splitthebill.activity.CounterBit;
import com.ritam.splitthebill.splitthebill.activity.EventFullDetails;

import java.util.ArrayList;

import setergeter.MemberList_Settergetter;


public class Dialog_AdvanceList_Counter_Adapter extends RecyclerView.Adapter<Dialog_AdvanceList_Counter_Adapter.ViewHolder> {


    ArrayList<String> amountList;
    CounterBit bookingAsHost;
    Dialog dialog_prevous;
    TextView textView, textView2;
    Context context;
    String FromPage;


    EventFullDetails eventFullDetails;
    BookingAsHost bookingasHost;
    ArrayList<MemberList_Settergetter> MemberList;
    Dialog dialog_member;

    public Dialog_AdvanceList_Counter_Adapter(ArrayList<String> amountList, CounterBit bookingAsHost, Dialog dialog, TextView textView, Context context, String frompage) {
        this.amountList = amountList;
        this.bookingAsHost = bookingAsHost;
        this.dialog_prevous = dialog;
        this.textView = textView;
        this.context = context;
        this.FromPage = frompage;
    }

    public Dialog_AdvanceList_Counter_Adapter(String fromPage, EventFullDetails eventFullDetails, ArrayList<MemberList_Settergetter> memberList, Dialog dialog_member) {
        FromPage = fromPage;
        this.eventFullDetails = eventFullDetails;
        MemberList = memberList;
        this.dialog_member = dialog_member;
        this.context = eventFullDetails;
    }

    public Dialog_AdvanceList_Counter_Adapter(String fromPage, BookingAsHost eventFullDetails, ArrayList<MemberList_Settergetter> memberList, Dialog dialog_member) {
        FromPage = fromPage;
        this.bookingasHost = eventFullDetails;
        MemberList = memberList;
        this.dialog_member = dialog_member;
        this.context = eventFullDetails;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_dialog_bookingasahost_advancelist, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        if (FromPage.equals("Counter")) {

            holder.DollerSign.setVisibility(View.VISIBLE);
            holder.PriceText.setText(amountList.get(position));

        } else if (FromPage.equals("CancelBooking")) {

            holder.DollerSign.setVisibility(View.GONE);
            holder.PriceText.setText(MemberList.get(position).getName());

        } else if (FromPage.equals("BookingAsHost")){

            holder.DollerSign.setVisibility(View.GONE);
            holder.PriceText.setText(MemberList.get(position).getName());

        }


        holder.TotalView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (FromPage.equals("Counter")) {

                    textView.setText(amountList.get(position));

                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);

                    // Setting Dialog Title
                    //alertDialog.setTitle("Confirm Delete...");

                    // Setting Dialog Message
                    alertDialog.setMessage("Are you sure you want to counter offer the bid to " + "$" + amountList.get(position).toString() + " ?");

                    // Setting Icon to Dialog
                    //alertDialog.setIcon(R.drawable.delete);

                    // Setting Positive "Yes" Button
                    alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            // Write your code here to invoke YES event
                            //Toast.makeText(getApplicationContext(), "You clicked on YES", Toast.LENGTH_SHORT).show();
                            bookingAsHost.Vol_Counter(amountList.get(position));

                            dialog_prevous.dismiss();
                            dialog.dismiss();
                        }
                    });

                    // Setting Negative "NO" Button
                    alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // Write your code here to invoke NO event
                            //Toast.makeText(getApplicationContext(), "You clicked on NO", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        }
                    });

                    // Showing Alert Message
                    alertDialog.show();


                } else if (FromPage.equals("CancelBooking")) {


                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);

                    alertDialog.setMessage("Are you sure you want to cancel the booking?");

                    // Setting Icon to Dialog
                    //alertDialog.setIcon(R.drawable.delete);

                    // Setting Positive "Yes" Button
                    alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            // Write your code here to invoke YES event
                            //Toast.makeText(getApplicationContext(), "You clicked on YES", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                            dialog_member.dismiss();
                            eventFullDetails.Vol_CancelBooking(MemberList.get(position).getHostId());

                        }
                    });

                    // Setting Negative "NO" Button
                    alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // Write your code here to invoke NO event
                            //Toast.makeText(getApplicationContext(), "You clicked on NO", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        }
                    });

                    // Showing Alert Message
                    alertDialog.show();




                }else if (FromPage.equals("BookingAsHost")){

                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);

                    alertDialog.setMessage("Are you sure you want to cancel the booking?");

                    // Setting Icon to Dialog
                    //alertDialog.setIcon(R.drawable.delete);

                    // Setting Positive "Yes" Button
                    alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            // Write your code here to invoke YES event
                            //Toast.makeText(getApplicationContext(), "You clicked on YES", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                            dialog_member.dismiss();
                            bookingasHost.Vol_CancelBooking(MemberList.get(position).getHostId());

                        }
                    });

                    // Setting Negative "NO" Button
                    alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // Write your code here to invoke NO event
                            //Toast.makeText(getApplicationContext(), "You clicked on NO", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        }
                    });

                    // Showing Alert Message
                    alertDialog.show();

                }


            }
        });

    }

    @Override
    public int getItemCount() {
        if (FromPage.equals("Counter"))

            return amountList.size();

        else

            return MemberList.size();



    }

    public static class ViewHolder extends RecyclerView.ViewHolder {


        TextView PriceText, DollerSign;
        View TotalView;

        public ViewHolder(View itemView) {
            super(itemView);

            PriceText = (TextView) itemView.findViewById(R.id.tv_price);
            DollerSign = (TextView) itemView.findViewById(R.id.tv_doller);
            TotalView = itemView;

        }
    }

}

