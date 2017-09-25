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
import com.ritam.splitthebill.splitthebill.apphelper.AppData;
import com.ritam.splitthebill.splitthebill.apphelper.RoundedTransformation;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import setergeter.UserSearch_SetterGetter;

/**
 * Created by ritam on 29/07/17.
 */

public class SearchUserListAdapter extends RecyclerView.Adapter<SearchUserListAdapter.ViewHolder> {


    ArrayList<UserSearch_SetterGetter> AllUserValue;
    Context context;
    int FollowOrUnFollow;

    public SearchUserListAdapter(ArrayList<UserSearch_SetterGetter> allUserValue, Context context) {
        AllUserValue = allUserValue;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_searchuser, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        holder.UserName.setText(AllUserValue.get(position).getName());
        holder.UserRating.setRating(Float.parseFloat(AllUserValue.get(position).getRating()));

        Picasso.with(context)
                .load(AllUserValue.get(position).getImage())
                .placeholder(R.drawable.appicon_round)
                .error(R.drawable.appicon_round)
                .transform(new RoundedTransformation())
                .into(holder.UserImage);


        if (AllUserValue.get(position).getFollowStatus() == 0) {

            holder.Button_Follow.setVisibility(View.VISIBLE);
            holder.Button_UnFollow.setVisibility(View.GONE);
            holder.Button_Requested.setVisibility(View.GONE);

        } else if (AllUserValue.get(position).getFollowStatus() == 1) {

            holder.Button_Follow.setVisibility(View.GONE);
            holder.Button_UnFollow.setVisibility(View.VISIBLE);
            holder.Button_Requested.setVisibility(View.GONE);

        } else if (AllUserValue.get(position).getFollowStatus() == 2) {

            holder.Button_Follow.setVisibility(View.GONE);
            holder.Button_UnFollow.setVisibility(View.GONE);
            holder.Button_Requested.setVisibility(View.VISIBLE);

        }


        holder.TotalView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context, OthersProfile.class);
                intent.putExtra("UserId", AllUserValue.get(position).getUserId());
                context.startActivity(intent);

            }
        });


        holder.Button_FollowUnFollow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Vol_FollowOrUnfollow(AllUserValue.get(position).getUserId(), holder.Button_FollowUnFollow, holder.UserProgress,position);

            }
        });

    }

    @Override
    public int getItemCount() {
        return AllUserValue.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView Button_Follow, Button_UnFollow, Button_Requested, UserName;
        RatingBar UserRating;
        ImageView UserImage;

        View TotalView;

        RelativeLayout Button_FollowUnFollow;

        ProgressBar UserProgress;

        public ViewHolder(View itemView) {
            super(itemView);

            TotalView = itemView;

            Button_Follow = (TextView) itemView.findViewById(R.id.tv_follow);
            Button_UnFollow = (TextView) itemView.findViewById(R.id.tv_unfollow);
            Button_Requested = (TextView) itemView.findViewById(R.id.tv_requested);
            UserName = (TextView) itemView.findViewById(R.id.tv_proname);

            UserImage = (ImageView) itemView.findViewById(R.id.iv_userImage);

            UserRating = (RatingBar) itemView.findViewById(R.id.rating_bar);

            Button_FollowUnFollow = (RelativeLayout) itemView.findViewById(R.id.rl_follow);

            UserProgress = (ProgressBar) itemView.findViewById(R.id.progressBar);
        }
    }


    public void Vol_FollowOrUnfollow(String toId, final RelativeLayout button, final ProgressBar ProfileProgress, final int pos) {

        ProfileProgress.setVisibility(View.VISIBLE);

        button.setClickable(false);
        button.setEnabled(false);

        SharedPreferences sharedPreferences = context.getSharedPreferences("AutoLogin", context.MODE_PRIVATE);

        String url = AppData.DomainUrlForProfile + "follow_control?to_id=" + toId + "&from_id=" + sharedPreferences.getString("UserId", "");

        Log.v("FollowUrl:::", url);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                Log.v("FollowResponse:::", response.toString());

                String message = null;

                try {

                    String status = response.getString("status");
                    message = response.getString("message");

                    if (status.equals("success")) {

                        if (AllUserValue.get(pos).getFollowStatus() == 0) {

                            FollowOrUnFollow = 1;

                            AllUserValue.get(pos).setFollowStatus(2);
                            notifyDataSetChanged();

                            //PrivateAccountText.setVisibility(View.GONE);


                        } else if (AllUserValue.get(pos).getFollowStatus() == 1) {

                            FollowOrUnFollow = 0;

                            AllUserValue.get(pos).setFollowStatus(0);
                            notifyDataSetChanged();

                            //PrivateAccountText.setVisibility(View.VISIBLE);


                        } else if (AllUserValue.get(pos).getFollowStatus() == 2) {

                            FollowOrUnFollow = 0;

                            AllUserValue.get(pos).setFollowStatus(0);
                            notifyDataSetChanged();

                            //PrivateAccountText.setVisibility(View.VISIBLE);

                        }

                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }

                button.setClickable(true);
                button.setEnabled(true);

                ProfileProgress.setVisibility(View.GONE);

                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                button.setClickable(true);
                button.setEnabled(true);

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
