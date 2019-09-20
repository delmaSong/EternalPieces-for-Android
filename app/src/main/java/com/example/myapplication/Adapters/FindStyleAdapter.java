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
import com.example.myapplication.R;
import com.example.myapplication.SharedData.Design;
import com.example.myapplication.SharedData.Like;
import com.example.myapplication.SharedData.LikeDesign;
import com.google.gson.Gson;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Delma Song on 2019-05-02
 */
public class FindStyleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private List<Design> mDataList;
    Context context;

    //for sharedPreference
    SharedPreferences sp_like_design, sp_login;
    SharedPreferences.Editor like_editor;
    String json, json_like;

    String userId;
//    Like like = new Like();
    LikeDesign likeDesign;
    Gson gson = new Gson();
    String dataLike;

    //like 객체에 넣어줄 리스트
    List<String> likeList = new ArrayList<>();
    List<String> alreadyList = new ArrayList<>();

    boolean doShared = false;




    public FindStyleAdapter(Context context, List<Design> mDataList){
        this.context = context;
        this.mDataList = mDataList;

    }


    //뷰홀더 생성. 리사이클러뷰의 행(row)표시
    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView item_img;
        TextView item_title;
        TextView item_contents;
        ToggleButton tb;


        //전체 루트 레이아웃에 해당하는 뷰가 들어옴

        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            item_img = (ImageView)itemView.findViewById(R.id.item_img);
            item_title =  (TextView)itemView.findViewById(R.id.item_title);
            item_contents = (TextView)itemView.findViewById(R.id.item_contents);
            tb = (ToggleButton)itemView.findViewById(R.id.item_tb1);

        }


    }


    public void setShared(){
        //settings for SharedPreference
        sp_login = context.getSharedPreferences("login_ok", Context.MODE_PRIVATE);
        if(sp_login.contains("login_ok")){
            userId = sp_login.getString("login_ok", "");
        }

        sp_like_design = context.getSharedPreferences("like_design", Context.MODE_PRIVATE);
        like_editor = sp_like_design.edit();

        dataLike = sp_like_design.getString(userId, "");
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


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder (ViewGroup viewGroup, int i) {
       //inflater로 뷰를 얻음.
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_find_style, viewGroup, false);
        context = viewGroup.getContext();

        //뷰 만들어질때 alreadyList에 넣어놓음.
        if(!doShared){

            setShared();
            Log.d("sharedsetting", "onCreate때 쉐어드 되나");
            doShared = true;
        }

        return new MyViewHolder(view);
    }



    //데이터 셋팅
    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder viewHolder, final int i) {

        dataLike = sp_like_design.getString(userId, "");
        likeDesign = gson.fromJson(dataLike, LikeDesign.class);


//        like = new Like();
        likeDesign = new LikeDesign();

        final MyViewHolder holder = (MyViewHolder) viewHolder;
        holder.item_img.setImageURI(mDataList.get(i).changeUri());
        holder.item_title.setText(mDataList.get(i).getName());
        holder.item_contents.setText(mDataList.get(i).getComment());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                //    mListener.onItemClicked(i);
                    Intent intent = new Intent(context, DesignActivity.class);
                    intent.putExtra("design_key", mDataList.get(i).getDesignId());
                    Log.d("intent로 보내는 디자인키값", mDataList.get(i).getDesignId()+"");
                    context.startActivity(intent);
                }
            });

        holder.tb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(userId == null){

                }else{
                    try{    //좋아요 중복체크 or 해제 되도록,, 빈하트 만들어주기

                        //기존 좋아요 누른거 있을 때
                        if(alreadyList.size() > 0) {
                            //처음 좋아요 누르는 거라면 likeList에 추가 및 하트 색칠
                            if (!alreadyList.contains(mDataList.get(i).getDesignId())) {
                                alreadyList.add(mDataList.get(i).getDesignId());
                                holder.tb.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.like));
                                Log.d("어댑터에서 좋아욥", mDataList.get(i).getDesignId());
                                //이미 좋아요 눌렀던 놈 또누르는 거라면 likeList에서 제거 및 하트 색 제거
                            } else if (alreadyList.contains(mDataList.get(i).getDesignId())) {
                                alreadyList.remove(mDataList.get(i).getDesignId());
                                holder.tb.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.no_like));
                                Log.d("어댑터에서 안좋아요", mDataList.get(i).getDesignId());
                            }
                        }else {//아예 처음 좋아요 누른다면
                            alreadyList.add(mDataList.get(i).getDesignId());
                            holder.tb.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.like));
                        }


                        likeDesign.setDesignId(alreadyList);
                        json = gson.toJson(likeDesign);
                        like_editor.putString(userId, json);
//                    like_editor.clear();
                        like_editor.apply();
                        Log.d("like_design쉐어드에 저장값", likeDesign.toString());

                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        });



            //쉐어드에 담겨진 디자인키값과 뿌려지는 디자인 키값 중 같은 것은 체크 표시
            if(dataLike.contains(mDataList.get(i).getDesignId()) || likeList.contains(mDataList.get(i).getDesignId())){
                Log.d("하트표시", mDataList.get(i).getDesignId().toString());
                holder.tb.setBackgroundDrawable(
                        context.getResources().getDrawable(R.drawable.like)
                );
            }

}


    //이 어댑터가 가진 아이템 갯수 지정
    @Override
    public int getItemCount() {
        return mDataList.size();
    }



}
