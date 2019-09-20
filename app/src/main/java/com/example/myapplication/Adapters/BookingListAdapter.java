package com.example.myapplication.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.Activities.WriteReview;
import com.example.myapplication.Items.ItemTattooerBookingList;
import com.example.myapplication.R;
import com.example.myapplication.SharedData.TattooistBooking;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.example.myapplication.Activities.BookingList.reviewUserId;

/**
 * Created by Delma Song on 2019-05-04
 */
public class BookingListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<TattooistBooking> mDataList;
    public Context context;
    Calendar calendar = Calendar.getInstance();
    Date date = new Date();
    SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy년 MM월 dd일");
    Date newDate;

    public BookingListAdapter(List<TattooistBooking> mDataList) {
        this.mDataList = mDataList;
    }

    public static class MyRecycleViewHolder extends RecyclerView.ViewHolder{
        ImageView item_img;
        TextView  item_booker2, item_date2, item_adrs2, item_sum2, item_worker2, item_part2, item_size2, item_req2;
        Button btn_write_review;

        public MyRecycleViewHolder(@NonNull View itemView) {
            super(itemView);
            item_img = (ImageView)itemView.findViewById(R.id.item_img);

            item_date2 = (TextView)itemView.findViewById(R.id.item_date2);
            item_booker2 = (TextView)itemView.findViewById(R.id.item_booker2);
            item_adrs2 = (TextView)itemView.findViewById(R.id.item_adrs2);
            item_sum2 = (TextView)itemView.findViewById(R.id.item_sum2);
            item_worker2 = (TextView)itemView.findViewById(R.id.item_worker2);
            item_part2 = (TextView)itemView.findViewById(R.id.item_part2);
            item_size2 = (TextView)itemView.findViewById(R.id.item_size2);
            item_req2 = (TextView)itemView.findViewById(R.id.item_req2);

            btn_write_review = (Button)itemView.findViewById(R.id.btn_write_review);



        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_booking_tattooer, viewGroup, false);
        context = viewGroup.getContext();
        return new MyRecycleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int i) {
        final MyRecycleViewHolder holder = (MyRecycleViewHolder)viewHolder;
        holder.item_img.setImageURI(mDataList.get(i).chageUri());       //사진 설정
        holder.item_date2.setText(mDataList.get(i).getDate() + mDataList.get(i).getTime());       //날짜,시간 설정

        holder.item_adrs2.setText(mDataList.get(i).getAddress());       //주소 설정
        holder.item_sum2.setText(mDataList.get(i).getPrice() + " 만원");
        holder.item_worker2.setText(mDataList.get(i).getTattooistId());
        holder.item_booker2.setText(mDataList.get(i).getBookerId());
        holder.item_part2.setText(mDataList.get(i).getBodyPart());
        holder.item_size2.setText(mDataList.get(i).getSize());
        holder.item_req2.setText(mDataList.get(i).getBookerComment());

        holder.btn_write_review.setActivated(false);
        holder.btn_write_review.setEnabled(false);
        holder.btn_write_review.setBackgroundColor(Color.parseColor("#DBDBDB"));

        try {
//            newDate = new SimpleDateFormat("yyyy년 MM월 dd일").parse(mDataList.get(i).getDate().substring(0,13));
            newDate = timeFormat.parse(mDataList.get(i).getDate().substring(0, 13));
            Log.d("원래들어간날짜값", mDataList.get(i).getDate().substring(0, 13));
            Log.d("newDate is", newDate.toString());
            Log.d("calendar is", calendar.getTime().toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Log.d("tattooistId is", mDataList.get(i).getTattooistId());
        Log.d("reviewUserId is ", reviewUserId);
        //타투 해주는 사람하고 현재 로그인한 유저하고 아이디 같다면
        if(mDataList.get(i).getTattooistId().equals(reviewUserId)){
            holder.btn_write_review.setActivated(false);
//            holder.btn_write_review.setEnabled(false);
           Log.d("타투이스트==로그인유저", "같으므로 버튼 안뜨도록");
        }else {
            Log.d("타투이스트!=로그인 유저", "다르므로 버튼 뜨도록");
            if(calendar.getTime().compareTo(newDate) == 1 || calendar.getTime().compareTo(newDate) == 0) {
                holder.btn_write_review.setActivated(true);
                holder.btn_write_review.setEnabled(true);
                holder.btn_write_review.setBackgroundColor(Color.parseColor("#F8D362"));
                holder.btn_write_review.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, WriteReview.class);
                        intent.putExtra("writer", reviewUserId);
                        intent.putExtra("tattooistId", mDataList.get(i).getTattooistId());
                        context.startActivity(intent);
                    }
                });
            }
        }




    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }
}
