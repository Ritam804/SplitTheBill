package com.ritam.splitthebill.splitthebill.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.futuremind.recyclerviewfastscroll.SectionTitleProvider;
import com.ritam.splitthebill.splitthebill.R;
import com.ritam.splitthebill.splitthebill.activity.ContactPage;
import com.ritam.splitthebill.splitthebill.apphelper.AppData;
import com.ritam.splitthebill.splitthebill.apphelper.RoundedTransformation;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import setergeter.Contacts_SetterGetter;
import setergeter.Phone_Selected_SetterGetter;
import setergeter.Splitter_Selected_SetterGetter;
import setergeter.Spltter_Contacts_SetterGetter;

/**
 * Created by su on 25/4/17.
 */

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ViewHolder> implements SectionTitleProvider{

    Activity activity;
    ContactPage inastance;
    ArrayList<Contacts_SetterGetter> contactVOList;
    ArrayList<Spltter_Contacts_SetterGetter> Splitter_ContactVoList;
    String FromPage;
    int totalSelect=0;
    int curenttotalselect=0;
    int totalunselect=0;
    boolean allselectstatus=false;

    public ContactAdapter(ContactPage contactPage, ArrayList<Contacts_SetterGetter> contactVOList1, String fromPage) {
        activity=contactPage;
        inastance=contactPage;
        FromPage = fromPage;
        contactVOList = contactVOList1;
    }


    public ContactAdapter(Activity activity, ContactPage inastance, ArrayList<Spltter_Contacts_SetterGetter> splitter_ContactVoList, String fromPage) {
        this.activity = activity;
        this.inastance = inastance;
        Splitter_ContactVoList = splitter_ContactVoList;
        FromPage = fromPage;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.contact_layout,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {


        if (FromPage.equals("Phone")){

            if (contactVOList.get(position).getStb_status().equals("1")){
                holder.image_border.setVisibility(View.VISIBLE);
            }else{
                holder.image_border.setVisibility(View.GONE);
            }

            if (contactVOList.get(position).getPhoto_uri()==null){
                holder.image.setVisibility(View.GONE);
                holder.text_image.setVisibility(View.VISIBLE);

                String s=contactVOList.get(position).getName().toString().trim();
                int pos=s.lastIndexOf(' ');
                // Log.d("pos",":::"+pos);


                holder.text_image.setText(contactVOList.get(position).getName().substring(0,1).toUpperCase()+contactVOList.get(position).getName().substring(pos+1,pos+2).toUpperCase());
            }else{
                holder.image.setVisibility(View.VISIBLE);
                holder.text_image.setVisibility(View.GONE);
                Picasso.with(activity)
                        .load(contactVOList.get(position).getPhoto_uri())
                        .placeholder(R.drawable.appicon_round)
                        .error(R.drawable.appicon_round)
                        .transform(new RoundedTransformation())
                        .into(holder.image);

            }

            holder.name.setText(contactVOList.get(position).getName());

            //Log.v("ContactAdapter::::",""+AppData.SplitterSelectedContacts.size());

            if (AppData.SplitterSelectedContacts.size() > 0){

                for (int i = 0;i<AppData.SplitterSelectedContacts.size();i++){


                    if (contactVOList.get(position).getNumber().equals(AppData.SplitterSelectedContacts.get(i).getNumber())){

                        contactVOList.get(position).setCheckStatus(true);
                        break;

                    }else {

                        contactVOList.get(position).setCheckStatus(false);
                        //break;

                    }

                }

            }

            if (contactVOList.get(position).getCheckStatus()==true){
                holder.check_id.setImageResource(R.drawable.check);
            }else{
                holder.check_id.setImageResource(R.drawable.uncheck);
            }


            holder.total_click.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (contactVOList.get(position).getCheckStatus()==false){
                        holder.check_id.setImageResource(R.drawable.check);
                        contactVOList.get(position).setCheckStatus(true);

                        Splitter_Selected_SetterGetter Splitter_Selected_SetterGetter = new Splitter_Selected_SetterGetter(contactVOList.get(position).getName(),contactVOList.get(position).getNumber(),contactVOList.get(position).getPhoto_uri(),contactVOList.get(position).getStb_status(),contactVOList.get(position).getCheckStatus());
                        AppData.SplitterSelectedContacts.add(Splitter_Selected_SetterGetter);

                        if(allselectstatus==true){
                            curenttotalselect++;
                            if(curenttotalselect==totalSelect) {
                                inastance.check_id.setImageResource(R.drawable.check);
                                inastance.check_status=false;
                            }

                        }else{

                            curenttotalselect++;
                            if(curenttotalselect==totalunselect)
                                inastance.check_id.setImageResource(R.drawable.check);

                        }

                    }else{
                        holder.check_id.setImageResource(R.drawable.uncheck);
                        contactVOList.get(position).setCheckStatus(false);

                        if(allselectstatus==true){
                            curenttotalselect--;
                            if(curenttotalselect<totalSelect) {
                                inastance.check_id.setImageResource(R.drawable.uncheck);
                                inastance.check_status=true;

                            }

                        }

                        if (AppData.SplitterSelectedContacts.size() > 0){

                            for (int i=0;i<AppData.SplitterSelectedContacts.size();i++){

                                if (contactVOList.get(position).getNumber().equals(AppData.SplitterSelectedContacts.get(i).getNumber())){

                                    AppData.SplitterSelectedContacts.remove(i);

//                                    if (AppData.PhoneSelectectedContacts.get(i).getListStatus() == null){
//
//                                        AppData.PhoneSelectectedContacts.remove(i);
//
//                                    }else {
//
//                                        AppData.Splitter_contactVOList.get(Integer.parseInt(AppData.PhoneSelectectedContacts.get(i).getListStatus())).setCheckStatus(false);
//                                        AppData.PhoneSelectectedContacts.remove(i);
//
//                                    }


                                    break;

                                }

                            }

                        }
                    }
                }
            });

        }else {

            if (Splitter_ContactVoList.get(position).getStb_status().equals("1")){
                holder.image_border.setVisibility(View.VISIBLE);
            }else{
                holder.image_border.setVisibility(View.GONE);
            }

            if (Splitter_ContactVoList.get(position).getPhoto_uri()==null){
                holder.image.setVisibility(View.GONE);
                holder.text_image.setVisibility(View.VISIBLE);

                String s=Splitter_ContactVoList.get(position).getName().toString().trim();
                int pos=s.lastIndexOf(' ');
                // Log.d("pos",":::"+pos);


                holder.text_image.setText(Splitter_ContactVoList.get(position).getName().substring(0,1).toUpperCase()+Splitter_ContactVoList.get(position).getName().substring(pos+1,pos+2).toUpperCase());
            }else{
                holder.image.setVisibility(View.VISIBLE);
                holder.text_image.setVisibility(View.GONE);
                Picasso.with(activity)
                        .load(Splitter_ContactVoList.get(position).getPhoto_uri())
                        .placeholder(R.drawable.appicon_round)
                        .error(R.drawable.appicon_round)
                        .transform(new RoundedTransformation())
                        .into(holder.image);

            }

            holder.name.setText(Splitter_ContactVoList.get(position).getName());


            if (AppData.SplitterSelectedContacts.size() > 0){

                for (int i = 0;i<AppData.SplitterSelectedContacts.size();i++){

                    if (Splitter_ContactVoList.get(position).getNumber().equals(AppData.SplitterSelectedContacts.get(i).getNumber())){


                        Splitter_ContactVoList.get(position).setCheckStatus(true);
                        break;

                    }else {

                        Splitter_ContactVoList.get(position).setCheckStatus(false);
                        //break;

                    }

                }

            }


            if (Splitter_ContactVoList.get(position).isCheckStatus()==true){
                holder.check_id.setImageResource(R.drawable.check);
            }else{
                holder.check_id.setImageResource(R.drawable.uncheck);
            }


            holder.total_click.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (Splitter_ContactVoList.get(position).isCheckStatus()==false){
                        holder.check_id.setImageResource(R.drawable.check);
                        Splitter_ContactVoList.get(position).setCheckStatus(true);

                        Splitter_Selected_SetterGetter splitter_selected_setterGetter = new Splitter_Selected_SetterGetter(Splitter_ContactVoList.get(position).getName(),Splitter_ContactVoList.get(position).getNumber(),Splitter_ContactVoList.get(position).getPhoto_uri(),Splitter_ContactVoList.get(position).getStb_status(),Splitter_ContactVoList.get(position).isCheckStatus());
                        AppData.SplitterSelectedContacts.add(splitter_selected_setterGetter);

                        if(allselectstatus==true){
                            curenttotalselect++;
                            if(curenttotalselect==totalSelect) {
                                inastance.check_id.setImageResource(R.drawable.check);
                                inastance.check_status=false;
                            }

                        }else{

                            curenttotalselect++;
                            if(curenttotalselect==totalunselect)
                                inastance.check_id.setImageResource(R.drawable.check);

                        }

                    }else{
                        holder.check_id.setImageResource(R.drawable.uncheck);
                        Splitter_ContactVoList.get(position).setCheckStatus(false);

                        if(allselectstatus==true){
                            curenttotalselect--;
                            if(curenttotalselect<totalSelect) {
                                inastance.check_id.setImageResource(R.drawable.uncheck);
                                inastance.check_status=true;

                            }

                        }

                        if (AppData.SplitterSelectedContacts.size() > 0){


                            for (int i = 0;i<AppData.SplitterSelectedContacts.size();i++){


                                if (Splitter_ContactVoList.get(position).getNumber().equals(AppData.SplitterSelectedContacts.get(i).getNumber())){

                                    AppData.SplitterSelectedContacts.remove(i);

//                                    if (AppData.SplitterSelectedContacts.get(i).getListStatus() == null){
//
//                                        AppData.SplitterSelectedContacts.remove(i);
//
//                                    }else {
//
//                                        AppData.contactVOList.get(Integer.parseInt(AppData.SplitterSelectedContacts.get(i).getListStatus())).setCheckStatus(false);
//                                        AppData.SplitterSelectedContacts.remove(i);
//
//                                    }


                                    break;

                                }

                            }

                        }

                    }
                }
            });

        }



    }



    @Override
    public int getItemCount() {

        if (FromPage.equals("Phone"))

        return contactVOList.size();

        else

            return Splitter_ContactVoList.size();
    }

    @Override
    public String getSectionTitle(int position) {

        if (FromPage.equals("Phone"))

        return contactVOList.get(position).getName().toUpperCase().substring(0, 1);

        else

            return Splitter_ContactVoList.get(position).getName().toUpperCase().substring(0, 1);

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView image,check_id,image_border;
        TextView name,text_image;
        RelativeLayout total_click;


        public ViewHolder(View itemView) {
            super(itemView);

            image= (ImageView) itemView.findViewById(R.id.iv);
            image_border= (ImageView) itemView.findViewById(R.id.image_border);
            check_id= (ImageView) itemView.findViewById(R.id.check_id);
            name= (TextView) itemView.findViewById(R.id.name);
            text_image= (TextView) itemView.findViewById(R.id.text_image);
            total_click= (RelativeLayout) itemView.findViewById(R.id.total_click);
        }
    }

    public void update(ArrayList<Contacts_SetterGetter> list){
        totalSelect=list.size();
        curenttotalselect=list.size();
        allselectstatus=true;
        contactVOList=list;

        notifyDataSetChanged();

    }
}
