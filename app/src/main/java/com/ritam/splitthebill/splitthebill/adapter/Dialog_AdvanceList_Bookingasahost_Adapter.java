package com.ritam.splitthebill.splitthebill.adapter;

import android.app.Dialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ritam.splitthebill.splitthebill.R;
import com.ritam.splitthebill.splitthebill.activity.BookingAsHost;
import com.ritam.splitthebill.splitthebill.apphelper.AppData;

import java.util.ArrayList;

/**
 * Created by ritam on 02/06/17.
 */

public class Dialog_AdvanceList_Bookingasahost_Adapter extends RecyclerView.Adapter<Dialog_AdvanceList_Bookingasahost_Adapter.ViewHolder> {


    ArrayList<String> amountList;
    BookingAsHost bookingAsHost;
    Dialog dialog;
    TextView textView,textView2;

    public Dialog_AdvanceList_Bookingasahost_Adapter(ArrayList<String> amountList, BookingAsHost bookingAsHost, Dialog dialog, TextView textView, TextView textView2) {
        this.amountList = amountList;
        this.bookingAsHost = bookingAsHost;
        this.dialog = dialog;
        this.textView = textView;
        this.textView2 = textView2;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.row_dialog_bookingasahost_advancelist,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        holder.PriceText.setText(amountList.get(position));

        holder.TotalView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AppData.RateChanged = true;

                if (AppData.FromButton.equals("ContactTheHost")){

                    textView2.setText(amountList.get(position));

                }

                textView.setText(amountList.get(position));
                dialog.dismiss();

                AppData.SelectedAmount  = Integer.parseInt(amountList.get(position));

            }
        });

    }

    @Override
    public int getItemCount() {
        return amountList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{


        TextView PriceText;
        View TotalView;

        public ViewHolder(View itemView) {
            super(itemView);

            PriceText = (TextView) itemView.findViewById(R.id.tv_price);
            TotalView = itemView;

        }
    }

}
