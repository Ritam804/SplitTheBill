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
import com.ritam.splitthebill.splitthebill.activity.OthersProfile;
import com.ritam.splitthebill.splitthebill.apphelper.RoundedTransformation;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import setergeter.Splits_SplittersList_SetterGetter;

/**
 * Created by ritam on 24/07/17.
 */

public class Splits_SplitterListAdapter extends RecyclerView.Adapter<Splits_SplitterListAdapter.ViewHolder>{

    ArrayList<Splits_SplittersList_SetterGetter> AllSplitterValue;
    Context context;

    public Splits_SplitterListAdapter(ArrayList<Splits_SplittersList_SetterGetter> allSplitterValue, Context context) {
        AllSplitterValue = allSplitterValue;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.row_splits_splitters,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {


        if (AllSplitterValue.get(position).getSpltterImage() != null){

            Picasso.with(context)
                    .load(AllSplitterValue.get(position).getSpltterImage())
                    .placeholder(R.drawable.appicon_round)
                    .error(R.drawable.appicon_round)
                    .transform(new RoundedTransformation())
                    .into(holder.SplitterImage);

        }

        holder.SplitterName.setText(AllSplitterValue.get(position).getSplitterName());
        holder.SplitterMaleCount.setText(AllSplitterValue.get(position).getSpltterMale());
        holder.SplitterFemaleCount.setText(AllSplitterValue.get(position).getSplitterFemale());
        holder.SplitterRating.setRating(Float.parseFloat(AllSplitterValue.get(position).getSplitterRating()));


        SharedPreferences sharedPreferences =  context.getSharedPreferences("AutoLogin",context.MODE_PRIVATE);

        if (sharedPreferences.getString("UserId","").equals(AllSplitterValue.get(position).getHostId())){

            holder.SplitterPayment.setText(AllSplitterValue.get(position).getSplitterPayment());

        }else {

            if (sharedPreferences.getString("UserId","").equals(AllSplitterValue.get(position).getSplitterId())){

                holder.SplitterPayment.setText(AllSplitterValue.get(position).getSplitterPayment());

            }else {

                holder.SplitterPayment.setText("Paid");

            }

        }


        holder.SplitterImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context, OthersProfile.class);
                intent.putExtra("UserId", AllSplitterValue.get(position).getSplitterId());
                context.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return AllSplitterValue.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView SplitterImage;

        TextView SplitterName,SplitterPayment,SplitterMaleCount,SplitterFemaleCount;

        RatingBar SplitterRating;

        public ViewHolder(View itemView) {
            super(itemView);

            SplitterImage = (ImageView) itemView.findViewById(R.id.iv_splits_splttername);

            SplitterName = (TextView) itemView.findViewById(R.id.tv_splits_splittername);
            SplitterPayment = (TextView) itemView.findViewById(R.id.tv_splits_splitterpayement);
            SplitterMaleCount = (TextView) itemView.findViewById(R.id.tv_splits_splittermalecounter);
            SplitterFemaleCount = (TextView) itemView.findViewById(R.id.tv_splits_splitterfemalecounter);
            SplitterRating = (RatingBar) itemView.findViewById(R.id.rating_bar);

        }
    }

}
