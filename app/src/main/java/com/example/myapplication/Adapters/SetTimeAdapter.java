package com.example.myapplication.Adapters;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.myapplication.Activities.BookingActivity;
import com.example.myapplication.R;
import com.example.myapplication.SharedData.Tattooist;
import com.example.myapplication.SharedData.TattooistBooking;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.example.myapplication.Activities.BookingActivity.doItDate;
import static com.example.myapplication.Activities.BookingActivity.nowTattooist;
import static com.example.myapplication.Activities.BookingActivity.occupiedDateList;

/**
 * Created by Delma Song on 2019-05-25
 */
public class SetTimeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<String> mDataList;
    Context context;
    public static String setTime;

    SharedPreferences sp_tattooist_booking;
    Gson gson = new Gson();
    String tattooist_booking_json;
    TattooistBooking tattooistBooking = new TattooistBooking();
   List<String> bookingKeys = new ArrayList<>();
   List<String> occupiedTimeList = new ArrayList<>();


    public SetTimeAdapter(Context context, List<String> mDataList){
        this.context = context;
        this.mDataList = mDataList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        Button btn_time;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            btn_time = (Button)itemView.findViewById(R.id.btn_time);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_possible_time, viewGroup, false);
        context = viewGroup.getContext();

        getAllEntry();

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int i) {

        final MyViewHolder holder = (MyViewHolder)viewHolder;
        holder.btn_time.setText(mDataList.get(i));
        //예약잡혀있는 요일과 지금 선택된 요일이 같고 예약 잡혀있는 시간과 지금 뿌려주는 시간이 같을 때 버튼 notEnable
        if(occupiedDateList.contains(doItDate) && occupiedTimeList.contains(mDataList.get(i))){
            holder.btn_time.setEnabled(false);
        }
         holder.btn_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTime = mDataList.get(i).toString();
                Toast.makeText(context.getApplicationContext(), mDataList.get(i)+"를 선택하셨습니다", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }


    public String settingTime(){
        return setTime;
    }


    protected void getAllEntry(){
        sp_tattooist_booking = context.getSharedPreferences("tattooist_booking", Context.MODE_PRIVATE);

        Map<String, ?> allEntries = sp_tattooist_booking.getAll();

        for(Map.Entry<String, ?> entry : allEntries.entrySet()){

            tattooist_booking_json = sp_tattooist_booking.getString(entry.getKey(), "");
            tattooistBooking = gson.fromJson(tattooist_booking_json, TattooistBooking.class);


            //tattooist_booking.xml의 모든 키를 bookingKeys에 담음
            bookingKeys.add(entry.getKey());
            //해당 타투이스트의 예약 시간정보만 담음
            if(entry.getKey().contains(nowTattooist)){
                occupiedTimeList.add(tattooistBooking.getTime());
            }
        }
    }
}
