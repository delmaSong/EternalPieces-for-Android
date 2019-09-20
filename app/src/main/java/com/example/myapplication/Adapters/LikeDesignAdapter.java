package com.example.myapplication.Adapters;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.example.myapplication.Activities.DesignActivity;
import com.example.myapplication.Items.ItemLikeArtist;
import com.example.myapplication.Items.ItemLikeDesign;
import com.example.myapplication.R;
import com.example.myapplication.SharedData.Design;
import com.example.myapplication.SharedData.Like;
import com.example.myapplication.SharedData.LikeArtist;
import com.example.myapplication.SharedData.LikeDesign;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Delma Song on 2019-05-04
 */
public class LikeDesignAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Design> mDataList;
    public Context context;

    public LikeDesignAdapter(List<Design> dataList) {
        mDataList = dataList;
    }

    //for sharedPreference
    SharedPreferences sp_like, sp_login;
    SharedPreferences.Editor like_editor;
    String json;
    Gson gson = new Gson();

    String userId;
    String dataLike;
    LikeDesign likeDesign;

    List<String> alreadyList = new ArrayList<>();
    boolean doShared = false;


    public static class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView item_img;
        ToggleButton item_tb1;
        TextView item_title, item_contents;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            item_img = (ImageView)itemView.findViewById(R.id.item_img);
            item_tb1 = (ToggleButton)itemView.findViewById(R.id.item_tb1);
            item_title = (TextView)itemView.findViewById(R.id.item_title);
            item_contents = (TextView)itemView.findViewById(R.id.item_contents);
        }
    }


    public void setShared(){
        //settings for SharedPreference
        sp_login = context.getSharedPreferences("login_ok", Context.MODE_PRIVATE);
        if(sp_login.contains("login_ok")){
            userId = sp_login.getString("login_ok", "");
        }

        sp_like = context.getSharedPreferences("like_design", Context.MODE_PRIVATE);
        like_editor = sp_like.edit();

        dataLike = sp_like.getString(userId, "");
        likeDesign = gson.fromJson(dataLike, LikeDesign.class);


        //쉐어드에 도안 좋아요 이미 누른게 있다면
        if(likeDesign != null){
            //alreadList에 하나씩 넣어라
            if (likeDesign.getDesignId() != null){
                for(int i=0; i<likeDesign.getDesignId().size(); i++){
                    alreadyList.add(likeDesign.getDesignId().get(i));
                }
            }
        }

    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_like_design, viewGroup, false);
        context = viewGroup.getContext();
        if(!doShared){

            setShared();
            Log.d("sharedsetting", "onCreate때 쉐어드 되나");
            doShared = true;
        }
        return new MyViewHolder(view);
    }



    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int i) {

        final MyViewHolder holder = (MyViewHolder)viewHolder;
        holder.item_img.setImageURI(mDataList.get(i).changeUri());
        holder.item_title.setText(mDataList.get(i).getName());
        holder.item_contents.setText(mDataList.get(i).getComment());
        holder.item_tb1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.item_tb1.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.no_like));

                alreadyList.remove(mDataList.get(i).getDesignId());

                likeDesign.setDesignId(alreadyList);
                json = gson.toJson(likeDesign);
                like_editor.putString(userId, json);
                like_editor.apply();

                Intent intent = new Intent(context, com.example.myapplication.Activities.LikeDesign.class);
                context.startActivity(intent);


            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DesignActivity.class);
                intent.putExtra("design_key",mDataList.get(i).getDesignId());
                context.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }
}
