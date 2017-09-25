package com.ritam.splitthebill.splitthebill.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.ritam.splitthebill.splitthebill.R;
import com.ritam.splitthebill.splitthebill.activity.OthersProfile;
import com.ritam.splitthebill.splitthebill.activity.Splitters;
import com.ritam.splitthebill.splitthebill.apphelper.AppData;
import com.ritam.splitthebill.splitthebill.apphelper.RoundedTransformation;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import setergeter.SpliterList_SetterGetter;

/**
 * Created by ritam on 21/07/17.
 */

public class SplitterlistAdapter extends RecyclerView.Adapter<SplitterlistAdapter.ViewHolder>{

    ArrayList<SpliterList_SetterGetter> SplitterListDetails;
    Context context;
    Splitters splitters;
    int Page = 1;

    public SplitterlistAdapter(ArrayList<SpliterList_SetterGetter> splitterListDetails, Context context) {
        SplitterListDetails = splitterListDetails;
        this.context = context;
        splitters = (Splitters) context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_splitterlist, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {


        if (SplitterListDetails.get(position).getImage() != null){

            Picasso.with(context)
                    .load(SplitterListDetails.get(position).getImage())
                    .placeholder(R.drawable.appicon_round)
                    .error(R.drawable.appicon_round)
                    .transform(new RoundedTransformation())
                    .into(holder.SplitterImage);

        }



        holder.SplitterName.setText(SplitterListDetails.get(position).getName());
        holder.SplitsCount.setText(SplitterListDetails.get(position).getSplits());
        holder.SplitterCount.setText(SplitterListDetails.get(position).getSplitters());

        holder.SplitterRating.setRating(Float.parseFloat(SplitterListDetails.get(position).getRating()));

        SharedPreferences sharedPreferences =  context.getSharedPreferences("AutoLogin",context.MODE_PRIVATE);

        if (sharedPreferences.getString("UserId","").equals(SplitterListDetails.get(position).getUserId())){

            holder.Button_FollowUnfollow.setVisibility(View.GONE);

        }else {

            holder.Button_FollowUnfollow.setVisibility(View.VISIBLE);

        }


        if (SplitterListDetails.get(position).getStatusCheck() == 0){

            holder.FollowText.setVisibility(View.VISIBLE);
            holder.UnfollowText.setVisibility(View.GONE);
            holder.RequestText.setVisibility(View.GONE);

        }else if (SplitterListDetails.get(position).getStatusCheck() == 1){

            holder.FollowText.setVisibility(View.GONE);
            holder.UnfollowText.setVisibility(View.VISIBLE);
            holder.RequestText.setVisibility(View.GONE);

        }else if (SplitterListDetails.get(position).getStatusCheck() == 2){

            holder.FollowText.setVisibility(View.GONE);
            holder.UnfollowText.setVisibility(View.GONE);
            holder.RequestText.setVisibility(View.VISIBLE);

        }

        holder.Button_FollowUnfollow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Vol_FollowOrUnfollow(SplitterListDetails.get(position).getUserId(),holder.Button_FollowUnfollow, holder.SplitterProgress,position);

            }
        });

        holder.Button_Splitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context,Splitters.class);
                intent.putExtra("UserId",SplitterListDetails.get(position).getUserId());
                context.startActivity(intent);

            }
        });


        holder.SplitterImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context, OthersProfile.class);
                intent.putExtra("UserId", SplitterListDetails.get(position).getUserId());
                context.startActivity(intent);

            }
        });

        if (SplitterListDetails.size() > 9){

            if (position > SplitterListDetails.size() - 2){

                Page = Page + 1;

                splitters.Vol_Splitters(Page);

            }

        }

    }

    @Override
    public int getItemCount() {
        return SplitterListDetails.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView SplitterImage;
        TextView SplitterName,SplitsCount,SplitterCount;
        RatingBar SplitterRating;
        LinearLayout Button_Splitter;
        TextView FollowText,UnfollowText,RequestText;
        RelativeLayout Button_FollowUnfollow;

        ProgressBar SplitterProgress;

        public ViewHolder(View itemView) {
            super(itemView);

            SplitterImage = (ImageView) itemView.findViewById(R.id.iv_proimage);

            SplitterName = (TextView) itemView.findViewById(R.id.tv_proname);
            SplitsCount = (TextView) itemView.findViewById(R.id.tv_followers);
            SplitterCount = (TextView) itemView.findViewById(R.id.tv_following);

            SplitterRating = (RatingBar) itemView.findViewById(R.id.rating_bar);

            Button_Splitter = (LinearLayout) itemView.findViewById(R.id.ll_splitter);

            FollowText = (TextView) itemView.findViewById(R.id.tv_follow);
            UnfollowText = (TextView) itemView.findViewById(R.id.tv_unfollow);
            RequestText = (TextView) itemView.findViewById(R.id.tv_requested);

            Button_FollowUnfollow = (RelativeLayout) itemView.findViewById(R.id.rl_followunfollow);

            SplitterProgress = (ProgressBar) itemView.findViewById(R.id.progressBar);
        }
    }

    public void Update(ArrayList<SpliterList_SetterGetter> SplitterListDetails){

        SplitterListDetails.addAll(SplitterListDetails);
        notifyDataSetChanged();

    }


    public void Vol_FollowOrUnfollow(String toid, final RelativeLayout Button_FollowOrUnFollow, final ProgressBar ProfileProgress , final int pos){

        ProfileProgress.setVisibility(View.VISIBLE);

        Button_FollowOrUnFollow.setClickable(false);
        Button_FollowOrUnFollow.setEnabled(false);

        SharedPreferences sharedPreferences =  context.getSharedPreferences("AutoLogin",context.MODE_PRIVATE);

        String url = AppData.DomainUrlForProfile+"follow_control?to_id="+toid+"&from_id="+sharedPreferences.getString("UserId","");

        Log.v("FollowUrl:::",url);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                Log.v("FollowResponse:::",response.toString());

                String message = null;

                try {

                    String status = response.getString("status");
                    message = response.getString("message");

                    if (status.equals("success")){

                        if (SplitterListDetails.get(pos).getStatusCheck() == 0){

                            SplitterListDetails.get(pos).setStatusCheck(2);

                            notifyDataSetChanged();

                            //PrivateAccountText.setVisibility(View.GONE);



                        }else if (SplitterListDetails.get(pos).getStatusCheck() == 1){

                            SplitterListDetails.get(pos).setStatusCheck(0);

                            notifyDataSetChanged();




                        }else if (SplitterListDetails.get(pos).getStatusCheck() == 2){

                            SplitterListDetails.get(pos).setStatusCheck(0);

                            notifyDataSetChanged();

                        }

                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }

                Button_FollowOrUnFollow.setClickable(true);
                Button_FollowOrUnFollow.setEnabled(true);

                ProfileProgress.setVisibility(View.GONE);

                Toast.makeText(context,message,Toast.LENGTH_SHORT).show();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Button_FollowOrUnFollow.setClickable(true);
                Button_FollowOrUnFollow.setEnabled(true);

                ProfileProgress.setVisibility(View.GONE);

            }
        });

        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                50000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        AppData.getInstance().addToRequestQueue(jsonObjectRequest);

    }

}
