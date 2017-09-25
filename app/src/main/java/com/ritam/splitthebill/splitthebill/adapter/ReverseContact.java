package com.ritam.splitthebill.splitthebill.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ritam.splitthebill.splitthebill.R;
import com.ritam.splitthebill.splitthebill.activity.ReverseGuest;
import com.ritam.splitthebill.splitthebill.apphelper.AppData;
import com.ritam.splitthebill.splitthebill.apphelper.RoundedTransformation;
import com.squareup.picasso.Picasso;

/**
 * Created by su on 29/4/17.
 */

public class ReverseContact extends RecyclerView.Adapter<ReverseContact.ViewHolder> {

    ReverseGuest instance;

    public ReverseContact(ReverseGuest reverseGuest) {
        instance=reverseGuest;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.spliters_layout,viewGroup,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        if (AppData.selectedContact.get(position).getPhoto_uri()==null){
            holder.image.setVisibility(View.GONE);
            holder.text_image.setVisibility(View.VISIBLE);

            String s=AppData.selectedContact.get(position).getName().toString().trim();
            int pos=s.lastIndexOf(' ');


            holder.text_image.setText(AppData.selectedContact.get(position).getName().substring(0,1).toUpperCase()
                    +AppData.selectedContact.get(position).getName().substring(pos+1,pos+2).toUpperCase());
        }else{
            holder.image.setVisibility(View.VISIBLE);
            holder.text_image.setVisibility(View.GONE);
            Picasso.with(instance)
                    .load(AppData.selectedContact.get(position).getPhoto_uri())
                    .placeholder(R.drawable.appicon_round)
                    .error(R.drawable.appicon_round)
                    .transform(new RoundedTransformation())
                    .into(holder.image);

        }

        holder.name.setText(AppData.selectedContact.get(position).getName());

//        holder.cross.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                AppData.selectedContact.remove(position);
//                notifyDataSetChanged();
//            }
//        });

        holder.cross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (position == 0){

                    if (AppData.selectedContact.size() == 1){

                        if (AppData.selectedContact.get(position).getSelectedPosition() < 1){

                            if (AppData.selectedContact.get(position).getPupose().equals("Phone")){

                                AppData.contactVOList.get(0).setCheckStatus(false);

                            }else {

                                AppData.Splitter_contactVOList.get(0).setCheckStatus(false);

                            }


                            AppData.selectedContact.remove(position);
                            instance.HideInviteList();

                        }else {

                            if (AppData.selectedContact.get(position).getPupose().equals("Phone")){

                                AppData.contactVOList.get(AppData.selectedContact.get(position).getSelectedPosition()).setCheckStatus(false);


                            }else {

                                AppData.Splitter_contactVOList.get(AppData.selectedContact.get(position).getSelectedPosition()).setCheckStatus(false);

                            }

                            AppData.selectedContact.remove(position);
                            instance.HideInviteList();

                        }



                    }else {

                        if (AppData.selectedContact.get(position).getPupose().equals("Phone")){

                            AppData.contactVOList.get(AppData.selectedContact.get(position).getSelectedPosition()).setCheckStatus(false);


                        }else {

                            AppData.Splitter_contactVOList.get(AppData.selectedContact.get(position).getSelectedPosition()).setCheckStatus(false);

                        }

                        AppData.selectedContact.remove(position);
                        notifyDataSetChanged();

                    }



                }else {

                    if (AppData.selectedContact.get(position).getPupose().equals("Phone")){

                        AppData.contactVOList.get(AppData.selectedContact.get(position).getSelectedPosition()).setCheckStatus(false);


                    }else {

                        AppData.Splitter_contactVOList.get(AppData.selectedContact.get(position).getSelectedPosition()).setCheckStatus(false);

                    }

                    AppData.selectedContact.remove(position);
                    notifyDataSetChanged();

                }


            }
        });

    }

    @Override
    public int getItemCount() {
        return AppData.selectedContact.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image,cross;
        TextView name,text_image;

        public ViewHolder(View itemView) {
            super(itemView);

            image= (ImageView) itemView.findViewById(R.id.iv);
            cross= (ImageView) itemView.findViewById(R.id.cross);
            name= (TextView) itemView.findViewById(R.id.name);
            text_image= (TextView) itemView.findViewById(R.id.text_image);
        }
    }
}
