package com.ritam.splitthebill.splitthebill.adapter;

import android.content.Context;
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
 * Created by ritam on 22/09/17.
 */

public class PromoterList_Adapter extends RecyclerView.Adapter<PromoterList_Adapter.ViewHolder> {


    BookingAsHost bookingAsHost;
    Context context;

    public PromoterList_Adapter(BookingAsHost bookingAsHost, Context context) {
        this.bookingAsHost = bookingAsHost;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_dialog_bookingasahost_advancelist, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {


        holder.DollarSign.setVisibility(View.GONE);

        holder.PromoterName.setText(AppData.PromoterDetails.get(position).getPromoterName());

        holder.TotalView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                bookingAsHost.SetPromoter(position);

            }
        });

    }

    @Override
    public int getItemCount() {
        return AppData.PromoterDetails.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        TextView PromoterName, DollarSign;
        View TotalView;

        public ViewHolder(View itemView) {
            super(itemView);

            DollarSign = (TextView) itemView.findViewById(R.id.tv_doller);
            PromoterName = (TextView) itemView.findViewById(R.id.tv_price);

            TotalView = itemView;

        }
    }

}
