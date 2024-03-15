package com.example.dhbc;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CauHoiAdapter extends  RecyclerView.Adapter<CauHoiAdapter.ViewHolder> {
    private Context context;
    private ItemClick_cauhoi mclick;
    public CauHoiAdapter(Context context, ArrayList<String> list,ItemClick_cauhoi mclick) {
        this.context = context;
        this.list = list;
        this.mclick=mclick;
    }
    ArrayList<String> list;

    @NonNull
    @Override
    public CauHoiAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v=LayoutInflater.from(context).inflate(R.layout.dongcauhoi,parent,false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CauHoiAdapter.ViewHolder holder, int i) {



//        if(list.get(i).toUpperCase()=="1"){
//            holder.txt.setText(list.get(i).toUpperCase());
//            holder.txt.setBackgroundResource(R.drawable.bt_item_result);
//
//        }
//        if(list.get(i).toUpperCase()==""){
//            holder.txt.setBackgroundColor(Color.WHITE);
//            holder.txt.setText ("");
//        }
//        else{
//            holder.txt.setBackgroundResource(R.drawable.bt_item);
//            holder.txt.setText (list.get(i));
//        }
        if ("1".equals(list.get(i).toUpperCase())) {
            holder.txt.setText(list.get(i).toUpperCase());
            holder.txt.setBackgroundResource(R.drawable.bt_item_result);
            holder.txt.setText("");
        } else if (list.get(i).isEmpty()) {
            holder.txt.setBackgroundColor(Color.WHITE);
            holder.txt.setText("");
        } else {
            holder.txt.setBackgroundResource(R.drawable.bt_item);
            holder.txt.setText(list.get(i));
        }

        holder.txt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mclick.onItemCauHoiClick(i);
                }
            });

//
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public String getItem(int i) {
        return list.get(i).toString();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txt;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txt=itemView.findViewById(R.id.ktu);
        }
    }
}
