package com.ritam.splitthebill.splitthebill.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ritam.splitthebill.splitthebill.R;
import com.ritam.splitthebill.splitthebill.activity.Payment;
import com.ritam.splitthebill.splitthebill.apphelper.AppData;

import java.util.ArrayList;

import setergeter.CardDetails_SetterGetter;

/**
 * Created by su on 25/4/17.
 */

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.ViewHolder> {

    ArrayList<CardDetails_SetterGetter> CardDetailsArray;
    Payment payment;


    public CardAdapter(Payment payment,ArrayList<CardDetails_SetterGetter> CardDetailsArray) {

        this.payment = payment;
        this.CardDetailsArray = CardDetailsArray;

    }

    @Override
    public CardAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.card_layout,parent,false);

        return new CardAdapter.ViewHolder(view);    }

    @Override
    public void onBindViewHolder(CardAdapter.ViewHolder holder, final int position) {


        holder.CardType.setText(CardDetailsArray.get(position).getCardType());
        holder.CardHolderName.setText(CardDetailsArray.get(position).getCardHolderName());
        holder.LastDigit.setText("****"+" "+"****"+" "+"****"+" "+CardDetailsArray.get(position).getCardNumber());

        if (CardDetailsArray.get(position).isSelectable()){

            holder.UnChecked.setVisibility(View.GONE);
            holder.Checked.setVisibility(View.VISIBLE);

            AppData.CardId = CardDetailsArray.get(position).getCardId();

        }else {

            holder.UnChecked.setVisibility(View.VISIBLE);
            holder.Checked.setVisibility(View.GONE);

        }


        holder.TotalView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (CardDetailsArray.get(position).isSelectable()){

                    CardDetailsArray.get(position).setSelectable(false);

                }else {



                    for (int j=0;j<CardDetailsArray.size();j++){

                        if (j != position){

                            if (CardDetailsArray.get(j).isSelectable()){

                                CardDetailsArray.get(j).setSelectable(false);

                            }

                        }

                    }


                    CardDetailsArray.get(position).setSelectable(true);

                    notifyDataSetChanged();

                }



                notifyDataSetChanged();

            }
        });

    }

    @Override
    public int getItemCount() {
        return CardDetailsArray.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView Checked,UnChecked;

        TextView CardType,CardHolderName,LastDigit;

        View TotalView;

        public ViewHolder(View itemView) {
            super(itemView);


            TotalView = itemView;

            Checked = (ImageView) itemView.findViewById(R.id.iv_check);
            UnChecked = (ImageView) itemView.findViewById(R.id.iv_uncheck);

            CardType = (TextView) itemView.findViewById(R.id.tv_cardtype);
            CardHolderName = (TextView) itemView.findViewById(R.id.tv_cardholder);
            LastDigit = (TextView) itemView.findViewById(R.id.tv_lastdigit);

        }
    }
}
