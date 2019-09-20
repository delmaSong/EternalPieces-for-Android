package com.example.myapplication.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.myapplication.Items.ItemTattooistMPWork;
import com.example.myapplication.R;
import com.example.myapplication.SharedData.Work;

import java.util.List;

/**
 * Created by Delma Song on 2019-05-03
 */
public class TattooistMPWorkAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Work> mDataList;
    public Context context;

    public TattooistMPWorkAdapter(List<Work> mDataList) {
        this.mDataList = mDataList;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView item_img;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            item_img = (ImageView)itemView.findViewById(R.id.item_img);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_design_and_work, viewGroup, false);
        context = view.getContext();
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        final MyViewHolder holder = (MyViewHolder)viewHolder;
        holder.item_img.setImageURI(mDataList.get(i).changeUri());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //이미지만 확대해서 위아래 이어져서 볼 수 있게끔,,와우,,
            }
        });

    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }
}
