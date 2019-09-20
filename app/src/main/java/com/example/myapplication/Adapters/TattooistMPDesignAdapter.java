package com.example.myapplication.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.myapplication.Activities.DesignActivity;
import com.example.myapplication.Items.ItemTattooistMPDesign;
import com.example.myapplication.R;
import com.example.myapplication.SharedData.Design;

import java.util.List;

/**
 * Created by Delma Song on 2019-05-03
 */
public class TattooistMPDesignAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Design> mDataList;
    public Context context;
    String design_key;

    public TattooistMPDesignAdapter(List<Design> mDataList) {
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
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_design_and_work, viewGroup, false);
        context = viewGroup.getContext();
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int i) {

        design_key = mDataList.get(i).getDesignId();        //design_key 변수에 데이터 리스트에 담겨진 값 넣어줌

        final MyViewHolder holder = (MyViewHolder)viewHolder;
        holder.item_img.setImageURI(mDataList.get(i).changeUri());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DesignActivity.class);
                intent.putExtra("design_key", mDataList.get(i).getDesignId());
                Log.d("intent로 보내는 디자인키값", mDataList.get(i).getDesignId()+"");
                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }
}
