package com.ritam.splitthebill.splitthebill.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.ritam.splitthebill.splitthebill.R;
import com.ritam.splitthebill.splitthebill.activity.OthersProfile;
import com.ritam.splitthebill.splitthebill.apphelper.RoundedTransformation;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import setergeter.Splits_SetterGetter;
import setergeter.Splits_SplittersList_SetterGetter;

/**
 * Created by ritam on 24/07/17.
 */

public class SplitsAdapter extends RecyclerView.Adapter<SplitsAdapter.ViewHolder>{


    ArrayList<Splits_SetterGetter> AllSplitsValue;
    Context context;
    ArrayList<Splits_SplittersList_SetterGetter> AllSplitterValue;

    public SplitsAdapter(ArrayList<Splits_SetterGetter> allSplitsValue, Context context) {
        AllSplitsValue = allSplitsValue;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.row_splits,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {


        holder.EventName.setText(AllSplitsValue.get(position).getEventName());
        holder.TableName.setText(AllSplitsValue.get(position).getTableName());
        holder.Price.setText(AllSplitsValue.get(position).getCost());
        holder.HostRating.setRating(Float.parseFloat(AllSplitsValue.get(position).getHostRating()));

        String[] year = AllSplitsValue.get(position).getEventFullDate().split("-");

        holder.EventDate.setText(AllSplitsValue.get(position).getEventMonth()+" "+AllSplitsValue.get(position).getEventDate()+" "+year[0]);

        holder.HostName.setText(AllSplitsValue.get(position).getHostName());
        holder.HostMaleCount.setText(AllSplitsValue.get(position).getHostMale());
        holder.HostFemaleCount.setText(AllSplitsValue.get(position).getHostFemale());



        if (AllSplitsValue.get(position).getHostImage() != null){

            Picasso.with(context)
                    .load(AllSplitsValue.get(position).getHostImage())
                    .placeholder(R.drawable.appicon_round)
                    .error(R.drawable.appicon_round)
                    .transform(new RoundedTransformation())
                    .into(holder.HostImage);

        }

        SharedPreferences sharedPreferences =  context.getSharedPreferences("AutoLogin",context.MODE_PRIVATE);

        if (sharedPreferences.getString("UserId","").equals(AllSplitsValue.get(position).getHostId())){

            holder.HostPaymentStatus.setText(AllSplitsValue.get(position).getHostPayment());

        }else {

            holder.HostPaymentStatus.setText("Paid");

        }

        if (AllSplitsValue.get(position).getAllSplitters().length() > 0){

            AllSplitterValue  = new ArrayList<Splits_SplittersList_SetterGetter>();

            for (int i=0;i<AllSplitsValue.get(position).getAllSplitters().length();i++){

                try {

                    JSONObject SplitterList = AllSplitsValue.get(position).getAllSplitters().getJSONObject(i);

                    Splits_SplittersList_SetterGetter splits_splittersList_setterGetter = new Splits_SplittersList_SetterGetter(SplitterList.getString("splitters_id"),SplitterList.getString("splitters_payment"),SplitterList.getString("splitters_name"),SplitterList.getString("splitters_male"),SplitterList.getString("splitters_female"),SplitterList.getString("splitters_rating"),SplitterList.getString("splitters_image"),AllSplitsValue.get(position).getHostId());
                    AllSplitterValue.add(splits_splittersList_setterGetter);


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }


            Splits_SplitterListAdapter splits_splitterListAdapter = new Splits_SplitterListAdapter(AllSplitterValue,context);
            holder.SpltterList.setAdapter(splits_splitterListAdapter);

        }


        holder.HostImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context, OthersProfile.class);
                intent.putExtra("UserId", AllSplitsValue.get(position).getHostId());
                context.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return AllSplitsValue.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView EventDate,EventName,TableName,Price;

        TextView HostName,HostPaymentStatus,HostMaleCount,HostFemaleCount;

        ImageView HostImage;

        RecyclerView SpltterList;

        RatingBar HostRating;

        public ViewHolder(View itemView) {
            super(itemView);

            EventDate = (TextView) itemView.findViewById(R.id.tv_date);
            EventName = (TextView) itemView.findViewById(R.id.tv_eventname);
            TableName = (TextView) itemView.findViewById(R.id.tv_tablename);
            Price = (TextView) itemView.findViewById(R.id.tv_price);
            HostRating = (RatingBar) itemView.findViewById(R.id.rating_bar);

            HostName = (TextView) itemView.findViewById(R.id.tv_hostname);
            HostPaymentStatus = (TextView) itemView.findViewById(R.id.tv_hostpayement);
            HostMaleCount = (TextView) itemView.findViewById(R.id.tv_malecounter);
            HostFemaleCount = (TextView) itemView.findViewById(R.id.tv_femalecounter);

            HostImage = (ImageView) itemView.findViewById(R.id.iv_hostimage);

            SpltterList = (RecyclerView) itemView.findViewById(R.id.rv_splits_splits);
            SpltterList.setHasFixedSize(true);
            SpltterList.setLayoutManager(new LinearLayoutManager(context));



        }
    }
}
