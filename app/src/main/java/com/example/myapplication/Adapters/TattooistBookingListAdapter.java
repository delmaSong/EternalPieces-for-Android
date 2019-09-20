package com.example.myapplication.Adapters;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.myapplication.Activities.TattooistBookingListDetail;
import com.example.myapplication.Items.ItemTattooistBookingList;
import com.example.myapplication.R;
import com.example.myapplication.SharedData.TattooistBooking;
import com.google.gson.Gson;

import java.util.List;

/**
 * Created by Delma Song on 2019-05-04
 */
public class TattooistBookingListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private List<TattooistBooking> mDataList;
    public Context context;


    public TattooistBookingListAdapter(List<TattooistBooking> mDataList, Context context) {
        this.mDataList = mDataList;
        this.context = context;
    }

    public static class MyRecyclerViewHolder extends RecyclerView.ViewHolder{
        TextView item_month;
        TextView item_date, item_time, item_subs, item_detail;

        public MyRecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            item_month = (TextView)itemView.findViewById(R.id.item_month);
            item_date = (TextView)itemView.findViewById(R.id.item_date);
            item_time = (TextView)itemView.findViewById(R.id.item_time);
            item_subs = (TextView)itemView.findViewById(R.id.item_subs);
            item_detail = (TextView)itemView.findViewById(R.id.item_detail);

        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_booking_tattooist, viewGroup, false);
        context = viewGroup.getContext();
        return new MyRecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        final MyRecyclerViewHolder holder =(MyRecyclerViewHolder)viewHolder;
//        holder.item_month.setText(mDataList.get(i).getItem_month());
//        holder.item_date.setText(mDataList.get(i).getItem_date());
//        holder.item_time.setText(mDataList.get(i).getItem_time());
//        holder.item_subs.setText(mDataList.get(i).getItem_subs());
//        holder.item_date.setText(mDataList.get(i).getItem_detail());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, TattooistBookingListDetail.class));
            }
        });

    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }
}
