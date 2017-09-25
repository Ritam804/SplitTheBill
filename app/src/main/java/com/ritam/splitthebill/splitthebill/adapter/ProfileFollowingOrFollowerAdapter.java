package com.ritam.splitthebill.splitthebill.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ritam.splitthebill.splitthebill.R;

/**
 * Created by ritam on 19/04/17.
 */





public class ProfileFollowingOrFollowerAdapter extends RecyclerView.Adapter<ProfileFollowingOrFollowerAdapter.Viewholder> {

    int FromPage;

    public ProfileFollowingOrFollowerAdapter(int fromPage) {
        FromPage = fromPage;
    }

    @Override
    public Viewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.row_profile_follower_following,parent,false);

        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(Viewholder holder, int position) {

    }

    @Override
    public int getItemCount() {

        if (FromPage == 1){

            return 10;

        }else if (FromPage == 2){

            return 1;

        }else if (FromPage == 3){

            return 2;

        }else if (FromPage == 4){

            return 5;

        }else {

            return 10;

        }


    }

    public class Viewholder extends RecyclerView.ViewHolder {
        public Viewholder(View itemView) {
            super(itemView);
        }
    }
}