package com.ritam.splitthebill.splitthebill.adapter;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ritam.splitthebill.splitthebill.R;
import com.ritam.splitthebill.splitthebill.activity.OthersProfile;
import com.ritam.splitthebill.splitthebill.apphelper.RoundedTransformation;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import setergeter.GroupMemberInfo_SetterGetter;

/**
 * Created by ritam on 09/05/17.
 */

public class GroupUserInfoAdapter extends RecyclerView.Adapter<GroupUserInfoAdapter.ViewHolder>{


    Context context;
    ArrayList<GroupMemberInfo_SetterGetter> GroupInfoArray;

    public GroupUserInfoAdapter(Context context, ArrayList<GroupMemberInfo_SetterGetter> groupInfoArray) {
        this.context = context;
        GroupInfoArray = groupInfoArray;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.row_groupmember_list,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {



        Picasso.with(context)
                .load(GroupInfoArray.get(position).getUserImage())
                .placeholder(R.drawable.appicon_round)
                .error(R.drawable.appicon_round)
                .transform(new RoundedTransformation())
                .into(holder.UserImage);

        holder.UserName.setText(GroupInfoArray.get(position).getUserName());

        if (GroupInfoArray.get(position).getHostStatus().equals("1")){

            holder.Button_Host.setVisibility(View.VISIBLE);

        }else {

            holder.Button_Host.setVisibility(View.GONE);

        }


        holder.Totalview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context, OthersProfile.class);
                intent.putExtra("UserId", GroupInfoArray.get(position).getUserId());
                context.startActivity(intent);

            }
        });


    }

    @Override
    public int getItemCount() {
        return GroupInfoArray.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView UserImage;
        TextView UserName;
        LinearLayout Button_Host;

        View Totalview;

        public ViewHolder(View itemView) {
            super(itemView);

            UserImage = (ImageView) itemView.findViewById(R.id.userimage);
            UserName = (TextView) itemView.findViewById(R.id.tv_username);
            Button_Host = (LinearLayout) itemView.findViewById(R.id.ll_host);
            Totalview = itemView;

        }
    }

}
