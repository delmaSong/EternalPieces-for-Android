package com.example.myapplication.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.myapplication.Items.ItemTattooistMPReview;
import com.example.myapplication.R;
import com.example.myapplication.SharedData.Review;
import com.example.myapplication.SharedData.Work;
import com.google.gson.Gson;

import java.util.List;
import java.util.Map;

/**
 * Created by Delma Song on 2019-05-04
 */
public class TattooistMPReviewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Review> mDataList;
    public Context context;

    SharedPreferences sp_review, sp_login;
    Gson gson = new Gson();
    String json;
    Review review;

    String userId;
    String writer;
    String realWriter;

    public TattooistMPReviewAdapter(List<Review> mDataList) {
        this.mDataList = mDataList;
    }

    public static class MyRecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener{

        TextView tt_title, tt_contents;
        ImageView item_img1, item_img2, item_img3;
        TextView tt_user_id, tt_write_date;
        RatingBar rating_bar;


        public MyRecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            tt_title = (TextView)itemView.findViewById(R.id.tt_title);
            tt_contents = (TextView)itemView.findViewById(R.id.tt_contents);
            item_img1 = (ImageView)itemView.findViewById(R.id.item_img1);
//            item_img2 = (ImageView)itemView.findViewById(R.id.item_img2);
//            item_img3 = (ImageView)itemView.findViewById(R.id.item_img3);
            tt_user_id = (TextView)itemView.findViewById(R.id.tt_user_id);
            tt_write_date = (TextView)itemView.findViewById(R.id.tt_write_date);
            rating_bar = (RatingBar)itemView.findViewById(R.id.rating_bar);


        }

        @Override
        public boolean onLongClick(View v) {


            return false;
        }
    }

    @NonNull
    @Override
    public MyRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_review, viewGroup, false);
        context = viewGroup.getContext();

        sp_login = context.getSharedPreferences("login_ok", Context.MODE_PRIVATE);
        userId = sp_login.getString("login_ok", "");

        sp_review = context.getSharedPreferences("review", Context.MODE_PRIVATE);

        return new MyRecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int i) {
        final MyRecyclerViewHolder holder = (MyRecyclerViewHolder)viewHolder;
        holder.tt_title.setText(mDataList.get(i).getTitle());
        holder.tt_contents.setText(mDataList.get(i).getContents());
        holder.tt_user_id.setText(mDataList.get(i).getWriter());
        holder.tt_write_date.setText(mDataList.get(i).getDate());
//        if(mDataList.get(i).chageUri() != null){

            holder.item_img1.setImageURI(mDataList.get(i).chageUri());
//        }
//        holder.item_img2.setImageResource(mDataList.get(i).getItem_img2());
//        holder.item_img3.setImageResource(mDataList.get(i).getItem_img3());
        holder.rating_bar.setRating(mDataList.get(i).getStar());

        writer = mDataList.get(i).getWriter();

        if(writer.equals(userId)){
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("삭제하시겠습니까?");
                    builder.setPositiveButton("네", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            mDataList.remove(i);
                            notifyDataSetChanged();
                        }
                    });
                    builder.setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            return;
                        }
                    });
                    builder.show();
                    return true;
                }
            });
        }

    }



    @Override
    public int getItemCount() {
        return mDataList.size();
    }



}
