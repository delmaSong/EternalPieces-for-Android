package com.example.myapplication.Adapters;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.Activities.ChatWithDesign;
import com.example.myapplication.Items.ItemChatListTattooist;
import com.example.myapplication.R;

import java.util.List;

/**
 * Created by Delma Song on 2019-05-04
 */
public class ChatListTattooistAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private List<ItemChatListTattooist> mDataList;
    public Context context;

    public ChatListTattooistAdapter(List<ItemChatListTattooist> mDataList) {
        this.mDataList = mDataList;
    }

    public static class MyRecyclerViewHolder extends RecyclerView.ViewHolder{
        ImageView item_img;
        TextView item_time, item_count, item_userid, item_msg;

        public MyRecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
        item_img = (ImageView)itemView.findViewById(R.id.item_img);
        item_time = (TextView)itemView.findViewById(R.id.item_time);
        item_count = (TextView)itemView.findViewById(R.id.item_count);
        item_userid = (TextView)itemView.findViewById(R.id.item_userid);
        item_msg = (TextView)itemView.findViewById(R.id.item_msg);


        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_chat_list_tattooist, viewGroup, false);
        context = viewGroup.getContext();
        return new MyRecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        final MyRecyclerViewHolder holder = (MyRecyclerViewHolder)viewHolder;
        holder.item_img.setImageResource(mDataList.get(i).getItem_img());
        holder.item_time.setText(mDataList.get(i).getItem_time());
        holder.item_count.setText(mDataList.get(i).getItem_count());
        holder.item_userid.setText(mDataList.get(i).getItem_userid());
        holder.item_msg.setText(mDataList.get(i).getItem_msg());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, ChatWithDesign.class));
            }
        });

    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }
}
