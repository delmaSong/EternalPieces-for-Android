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

import com.example.myapplication.Activities.TattooistMypageDesign;
import com.example.myapplication.Items.ItemFindArtist;
import com.example.myapplication.R;
import com.example.myapplication.SharedData.Design;
import com.example.myapplication.SharedData.Like;
import com.example.myapplication.SharedData.LikeArtist;
import com.example.myapplication.SharedData.LikeDesign;
import com.example.myapplication.SharedData.Tattooist;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Delma Song on 2019-05-03
 */
public class FindArtistAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private List<Tattooist> mDataList;
    private Context context;

    //for sharedPreference
    SharedPreferences sp_like_artist, sp_login;
    SharedPreferences.Editor like_editor;

    String userId;
    String json;
    Gson gson = new Gson();
    String dataLike;
    LikeArtist likeArtist;

    //like 객체에 넣어줄 리스트
    List<String> likeList = new ArrayList<>();
    List<String> alreadyList = new ArrayList<>();




    public FindArtistAdapter(Context context, List<Tattooist> mDataList) {
        this.context = context;
        this.mDataList = mDataList;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView item_img;
        TextView item_id;
        TextView item_contents;
        ToggleButton tb;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            item_id = (TextView)itemView.findViewById(R.id.item_id);
            item_contents = (TextView)itemView.findViewById(R.id.item_contents);
            tb = (ToggleButton)itemView.findViewById(R.id.item_tb1);
            item_img = (ImageView)itemView.findViewById(R.id.item_img);
        }
    }

    public void setShared(){
        //settings for SharedPreference
        sp_login = context.getSharedPreferences("login_ok", Context.MODE_PRIVATE);
        if(sp_login.contains("login_ok")){
            userId = sp_login.getString("login_ok", "");
        }

        sp_like_artist = context.getSharedPreferences("like_artist", Context.MODE_PRIVATE);
        like_editor = sp_like_artist.edit();

        dataLike = sp_like_artist.getString(userId, "");
        likeArtist = gson.fromJson(dataLike, LikeArtist.class);


        //쉐어드에 도안 좋아요 이미 누른게 있다면
        if(likeArtist != null){
            //alreadList에 하나씩 넣어라
            if (likeArtist.getArtistId() != null){
                for(int i=0; i<likeArtist.getArtistId().size(); i++){
                    alreadyList.add(likeArtist.getArtistId().get(i));
                }
            }
        }

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_find_artist, viewGroup, false);
        context = viewGroup.getContext();

        setShared();

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int i) {



        dataLike = sp_like_artist.getString(userId, "");
        likeArtist = gson.fromJson(dataLike, LikeArtist.class);

        likeArtist = new LikeArtist();

//        get_json = sp_like.getString(userId, "");
//        dataLike = get_json;
//        like = gson.fromJson(get_json, Like.class);


        final MyViewHolder holder =(MyViewHolder)viewHolder;

        holder.item_img.setImageURI(mDataList.get(i).changeUri());
        holder.item_id.setText(mDataList.get(i).getId());
        holder.item_contents.setText(mDataList.get(i).getProfile());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, TattooistMypageDesign.class);
                intent.putExtra("tattooist_key", mDataList.get(i).getId());
                Log.d("intent로 보내는 타투이스트아이디값", mDataList.get(i).getId()+"");
                context.startActivity(intent);
            }
        });






        holder.tb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(userId == null || userId == ""){

                }else{

                    holder.tb.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.like));
                    try{


                        //이미 좋아요 눌렀던 놈이라면
                        if(alreadyList.contains(mDataList.get(i).getId()) || likeList.contains(mDataList.get(i).getId())){
                            likeList.remove(mDataList.get(i).getId());
                            holder.tb.setBackgroundDrawable(
                                    context.getResources().getDrawable(R.drawable.no_like)
                            );
                            Log.d("안좋아요", mDataList.get(i).getId());
                        }else if(!alreadyList.contains(mDataList.get(i).getId()) || !likeList.contains(mDataList.get(i).getId())) { //처음 좋아요 누르는 거라면
                            likeList.add(mDataList.get(i).getId());
                            holder.tb.setBackgroundDrawable(
                                    context.getResources().getDrawable(R.drawable.like)
                            );
                            Log.d("좋아요", mDataList.get(i).getId());

                        }


                        //likeList를 like 객체에 setDesign한다
                        likeArtist.setArtistId(likeList);
                        json = gson.toJson(likeArtist);
                        like_editor.putString(userId, json);
//                    like_editor.clear();
                        like_editor.apply();
                        Log.d("like_design쉐어드에 저장값", likeArtist.toString());




                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        });

        //쉐어드에 담겨진 디자인키값과 뿌려지는 디자인 키값 중 같은 것은 체크 표시
        if(dataLike.contains(mDataList.get(i).getId().toString()) || likeList.contains(mDataList.get(i).getId())){
            Log.d("하트표시", mDataList.get(i).getId().toString());
            holder.tb.setBackgroundDrawable(
                    context.getResources().getDrawable(R.drawable.like)
            );
        }
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }



}
