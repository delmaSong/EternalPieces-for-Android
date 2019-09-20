package com.example.myapplication.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.myapplication.Items.ItemChat;
import com.example.myapplication.R;

import java.util.Date;
import java.util.List;

/**
 * Created by Delma Song on 2019-05-06
 */
public class ChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private List<ItemChat> mDataList;
    public Context context;
    public ChatAdapter(List<ItemChat> mDataList) {
        this.mDataList = mDataList;
    }

    //메시지 누가 보낸건지 분기에 따라 아이템 다르게 띄워줌.
    private static final int MSG_TYPE_MINE = 0;
    private static final int MSG_TYPE_YOURS = 1;

    @Override
    public int getItemViewType(int position){
        return position % 2 == 0 ? MSG_TYPE_MINE : MSG_TYPE_YOURS ;
    }



    //my message view holder
    public class MyMsgRecycleViewHolder extends RecyclerView.ViewHolder {

        TextView item_msg;
        TextView item_now_time;
//        RadioGroup rdoGroup;
//        RadioButton rdo_who;

        public MyMsgRecycleViewHolder(@NonNull View itemView) {
            super(itemView);
            item_msg = (TextView)itemView.findViewById(R.id.item_my_msg);
            item_now_time = (TextView)itemView.findViewById(R.id.item_now_time);
//            rdoGroup = (RadioGroup)itemView.findViewById(R.id.rdo_who);
//            rdo_who = (RadioButton)itemView.findViewById(rdoGroup.getCheckedRadioButtonId());
        }


    }

    //your message view holder
    public static class  YourMsgRecyclerViewHodler extends RecyclerView.ViewHolder{
        TextView item_msg;
        TextView item_now_time2;
//        RadioGroup rdoGroup;
//        RadioButton rdo_who;

        public YourMsgRecyclerViewHodler(@NonNull View itemView) {
            super(itemView);
            item_msg = (TextView)itemView.findViewById(R.id.item_your_msg);
            item_now_time2 = (TextView)itemView.findViewById(R.id.item_now_time);
//            rdoGroup = (RadioGroup)itemView.findViewById(R.id.rdo_who);
//            rdo_who = (RadioButton)itemView.findViewById(rdoGroup.getCheckedRadioButtonId());
        }

    }



    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        if(i == MSG_TYPE_MINE){
            View view = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.item_chat_me, viewGroup, false);
            context = viewGroup.getContext();
            return new MyMsgRecycleViewHolder(view);
        }else{
            View view = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.item_chat_you, viewGroup, false);
            context = viewGroup.getContext();
            return new YourMsgRecyclerViewHodler(view);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        if(viewHolder instanceof MyMsgRecycleViewHolder){
            final MyMsgRecycleViewHolder holder = (MyMsgRecycleViewHolder)viewHolder;
            holder.item_msg.setText(mDataList.get(i).getItem_my_msg());
            holder.item_now_time.setText(mDataList.get(i).getItem_now_time());
       //     holder.rdo_who.getText().toString();
        }else {
            final YourMsgRecyclerViewHodler hodler = (YourMsgRecyclerViewHodler)viewHolder;
            hodler.item_msg.setText(mDataList.get(i).getItem_my_msg());
            hodler.item_now_time2.setText(mDataList.get(i).getItem_now_time());
       //     hodler.rdo_who.getText().toString();
        }

    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }



}
