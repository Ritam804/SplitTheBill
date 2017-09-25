package com.ritam.splitthebill.splitthebill.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ritam.splitthebill.splitthebill.R;
import com.ritam.splitthebill.splitthebill.activity.BookingAsHost;
import com.ritam.splitthebill.splitthebill.apphelper.AppData;

import java.util.ArrayList;

import setergeter.Promoter_SetterGetter;
import setergeter.SelectTableSetterGetter;

/**
 * Created by su on 17/4/17.
 */

public class SelectTableAdapter extends RecyclerView.Adapter<SelectTableAdapter.ViewHolder> {

    Context context;
    ArrayList<SelectTableSetterGetter> SelectTableArray;

    public SelectTableAdapter(Context context, ArrayList<SelectTableSetterGetter> SelectTableArray) {
        this.context = context;
        this.SelectTableArray = SelectTableArray;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_select_table, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {


        holder.Text_Table.setText(SelectTableArray.get(position).getTableName());
        holder.Text_Price.setText("$"+SelectTableArray.get(position).getCost());


        if (SelectTableArray.get(position).getWhatToDo().equals("host")) {

            holder.Button_Text.setText("HOST THE TABLE");

        } else if (SelectTableArray.get(position).getWhatToDo().equals("contact")) {


            SharedPreferences sharedPreferences = context.getSharedPreferences("AutoLogin", context.MODE_PRIVATE);

            if (SelectTableArray.get(position).getHostId().equals(sharedPreferences.getString("UserId", ""))) {

                holder.Button_Text.setText("BOOKED AS THE HOST");

            } else {


                if (SelectTableArray.get(position).getBookingStatus() == 1){

                    holder.Button_Text.setText("ALREADY REQUESTED");

                }else {

                    if (SelectTableArray.get(position).getRequestStatus() == 1){

                        holder.Button_Text.setText("ALREADY REQUESTED");

                    }else {

                        holder.Button_Text.setText("CONTACT THE HOST");
                    }

                }

            }


        }


        if (SelectTableArray.get(position).getDeclineStatus() == 1) {

            holder.Button_ContactTheHost.setBackground(context.getResources().getDrawable(R.drawable.disable_button_mold_rectangle));
            holder.Button_ContactTheHost.setEnabled(false);
            holder.Button_ContactTheHost.setClickable(false);

        } else {


//            if (SelectTableArray.get(position).getBookingStatus() == 1 || SelectTableArray.get(position).getRequestStatus() == 1) {
//
//                holder.Button_ContactTheHost.setBackground(context.getResources().getDrawable(R.drawable.disable_button_mold_rectangle));
//                holder.Button_ContactTheHost.setEnabled(false);
//                holder.Button_ContactTheHost.setClickable(false);
//
//            } else {
//
//                if (SelectTableArray.get(position).getRequestStatus() == 1) {
//
//                    holder.Button_ContactTheHost.setBackground(context.getResources().getDrawable(R.drawable.disable_button_mold_rectangle));
//                    holder.Button_ContactTheHost.setEnabled(false);
//                    holder.Button_ContactTheHost.setClickable(false);
//
//                } else {
//
//                    holder.Button_ContactTheHost.setBackground(context.getResources().getDrawable(R.drawable.red_mold_rectangle));
//                    holder.Button_ContactTheHost.setEnabled(true);
//                    holder.Button_ContactTheHost.setClickable(true);
//
//                }
//
//            }

        }


        holder.mProgressBar.setMax(Integer.parseInt(SelectTableArray.get(position).getTotalNumberOfSit()));
        holder.mProgressBar.setProgress((Integer.parseInt(SelectTableArray.get(position).getTotalNumberOfSit()) - Integer.parseInt(SelectTableArray.get(position).getNumBerOfAvailableSit())));
        holder.Text_Progress.setText(String.valueOf((Integer.parseInt(SelectTableArray.get(position).getTotalNumberOfSit()) - Integer.parseInt(SelectTableArray.get(position).getNumBerOfAvailableSit()))) + "/" + SelectTableArray.get(position).getTotalNumberOfSit());


        if (SelectTableArray.get(position).getTableStatus().equals("Booked")) {


            holder.mProgressBar.setProgressDrawable(context.getResources().getDrawable(R.drawable.disable_progressbar_style));
            holder.Button_Area.setVisibility(View.GONE);

            holder.Text_Table.setTextColor(Color.parseColor("#909090"));
            holder.Text_Available.setTextColor(Color.parseColor("#909090"));
            holder.Text_Available.setText("Booked");
            holder.Text_Price.setTextColor(Color.parseColor("#909090"));
            holder.Text_Progress.setTextColor(Color.parseColor("#909090"));


        }

        holder.Button_ContactTheHost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (SelectTableArray.get(position).getWhatToDo().equals("host")) {

                    Intent intent = new Intent(context, BookingAsHost.class);
                    intent.putExtra("VenueName",SelectTableArray.get(position).getVenueName());
                    AppData.FromButton = "HostTheTable";
                    AppData.TableId = SelectTableArray.get(position).getTableId();
                    AppData.HostName = SelectTableArray.get(position).getHostName();
                    AppData.HostId = SelectTableArray.get(position).getHostId();
                    AppData.MaximumTotalMember = Integer.parseInt(SelectTableArray.get(position).getTotalNumberOfSit());
                    AppData.NumberOfAvailableSeat = Integer.parseInt(SelectTableArray.get(position).getNumBerOfAvailableSit());
                    AppData.MinimumAmount = Integer.parseInt(SelectTableArray.get(position).getMinAmount());
                    AppData.MaximumAmount = Integer.parseInt(SelectTableArray.get(position).getCost());
                    AppData.GroupId = SelectTableArray.get(position).getGroupId();
                    context.startActivity(intent);
                    AppData.Contact_Block = false;

                } else if (SelectTableArray.get(position).getWhatToDo().equals("contact")) {

                    if (SelectTableArray.get(position).getBookingStatus() == 1 || SelectTableArray.get(position).getRequestStatus() == 1) {

                        SharedPreferences sharedPreferences = context.getSharedPreferences("AutoLogin", context.MODE_PRIVATE);

                        if (SelectTableArray.get(position).getHostId().equals(sharedPreferences.getString("UserId", ""))) {

                            //Toast.makeText(context,"You already BOOKED AS THE HOST",Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(context, BookingAsHost.class);
                            intent.putExtra("Purpose","Edit");
                            AppData.FromButton = "ContactTheHost";
                            AppData.TableId = SelectTableArray.get(position).getTableId();
                            AppData.HostName = SelectTableArray.get(position).getHostName();
                            AppData.HostId = SelectTableArray.get(position).getHostId();
                            AppData.MaximumTotalMember = Integer.parseInt(SelectTableArray.get(position).getTotalNumberOfSit());
                            AppData.NumberOfAvailableSeat = Integer.parseInt(SelectTableArray.get(position).getNumBerOfAvailableSit());
                            AppData.MinimumAmount = 50;
                            AppData.MaximumAmount = Integer.parseInt(SelectTableArray.get(position).getRemainingAmount());
                            AppData.GroupId = SelectTableArray.get(position).getGroupId();
                            context.startActivity(intent);
                            AppData.Contact_Block = false;

                        } else {

                            //Toast.makeText(context,"You already CONTACT THE HOST",Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(context, BookingAsHost.class);
                            intent.putExtra("Purpose","Edit");
                            AppData.FromButton = "ContactTheHost";
                            AppData.TableId = SelectTableArray.get(position).getTableId();
                            AppData.HostName = SelectTableArray.get(position).getHostName();
                            AppData.HostId = SelectTableArray.get(position).getHostId();
                            AppData.MaximumTotalMember = Integer.parseInt(SelectTableArray.get(position).getTotalNumberOfSit());
                            AppData.NumberOfAvailableSeat = Integer.parseInt(SelectTableArray.get(position).getNumBerOfAvailableSit());
                            AppData.MinimumAmount = 50;
                            AppData.MaximumAmount = Integer.parseInt(SelectTableArray.get(position).getRemainingAmount());
                            AppData.GroupId = SelectTableArray.get(position).getGroupId();
                            context.startActivity(intent);
                            AppData.Contact_Block = false;

                        }

                    }else {

                        Intent intent = new Intent(context, BookingAsHost.class);
                        intent.putExtra("Purpose","Contact");
                        AppData.FromButton = "ContactTheHost";
                        AppData.TableId = SelectTableArray.get(position).getTableId();
                        AppData.HostName = SelectTableArray.get(position).getHostName();
                        AppData.HostId = SelectTableArray.get(position).getHostId();
                        AppData.MaximumTotalMember = Integer.parseInt(SelectTableArray.get(position).getTotalNumberOfSit());
                        AppData.NumberOfAvailableSeat = Integer.parseInt(SelectTableArray.get(position).getNumBerOfAvailableSit());
                        AppData.MinimumAmount = 50;
                        AppData.MaximumAmount = Integer.parseInt(SelectTableArray.get(position).getRemainingAmount());
                        AppData.GroupId = SelectTableArray.get(position).getGroupId();
                        context.startActivity(intent);
                        AppData.Contact_Block = false;

                    }



                }


            }
        });

    }

    @Override
    public int getItemCount() {
        return SelectTableArray.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        LinearLayout Button_ContactTheHost;
        RelativeLayout Button_Area;
        TextView Button_Text, Text_Table, Text_Available, Text_Price, Text_Progress;
        ProgressBar mProgressBar;

        public ViewHolder(View itemView) {
            super(itemView);

            Button_ContactTheHost = (LinearLayout) itemView.findViewById(R.id.ll_contactthehost);
            Button_Area = (RelativeLayout) itemView.findViewById(R.id.rl_buttonarea);
            Button_Text = (TextView) itemView.findViewById(R.id.tv_buttontext);
            Text_Table = (TextView) itemView.findViewById(R.id.tv_table);
            Text_Available = (TextView) itemView.findViewById(R.id.tv_available);
            Text_Price = (TextView) itemView.findViewById(R.id.payment);
            Text_Progress = (TextView) itemView.findViewById(R.id.rating);
            mProgressBar = (ProgressBar) itemView.findViewById(R.id.pbar);

        }
    }
}
