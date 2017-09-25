package com.ritam.splitthebill.splitthebill.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ritam.splitthebill.splitthebill.R;
import com.ritam.splitthebill.splitthebill.activity.HomeActivity;
import com.ritam.splitthebill.splitthebill.apphelper.AppData;

import java.util.ArrayList;

import setergeter.SetterGetter_Places;


/**
 * Created by ritam on 16/03/17.
 */

public class PlacesAdapter extends RecyclerView.Adapter<PlacesAdapter.ViewHolder>{

    Context context;
    HomeActivity restaurantInformation;
    ArrayList<SetterGetter_Places> PlacesInfo;

    public PlacesAdapter(Context context, HomeActivity restaurantInformation, ArrayList<SetterGetter_Places> placesInfo) {
        this.context = context;
        this.restaurantInformation = restaurantInformation;
        PlacesInfo = placesInfo;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemLayoutView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_spinner_category, null);


        // create ViewHolder
        ViewHolder viewHolder = new ViewHolder(itemLayoutView);

        //ViewHolder viewHolder = new ViewHolder(itemLayoutView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        holder.FullAddress.setText(PlacesInfo.get(position).getFullAddress());

        holder.FullRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AppData.FilterLat = String.valueOf(PlacesInfo.get(position).getLat());
                AppData.FilterLong = String.valueOf(PlacesInfo.get(position).getLong());

                restaurantInformation.ChoosePlace(position);

            }
        });

    }

    @Override
    public int getItemCount() {
        return PlacesInfo.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{


        TextView FullAddress;
        View FullRow;

        public ViewHolder(View itemView) {
            super(itemView);

            FullAddress = (TextView) itemView.findViewById(R.id.tv_categoryname);
            FullRow = itemView;

        }
    }

}
