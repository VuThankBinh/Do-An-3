package com.example.dhbc.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dhbc.Interface.ItemClick_cauhoi;
import com.example.dhbc.R;

public class Round2_cauhoiAdapter extends  RecyclerView.Adapter<Round2_cauhoiAdapter.ViewHolder> {
    private Context context;
    private ItemClick_cauhoi mclick;
    private String[]list;

    public Round2_cauhoiAdapter(Context context, String[] list, ItemClick_cauhoi mclick) {
        this.context = context;
        this.mclick = mclick;
        this.list = list;
    }

    @NonNull
    @Override
    public Round2_cauhoiAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.dong_cauhoi_round2,parent,false);

        return new Round2_cauhoiAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull Round2_cauhoiAdapter.ViewHolder holder, int i) {
        if ("1".equals(list[i].toUpperCase().trim())) {
            holder.txt.setText(list[i].toUpperCase());
            holder.txt.setBackgroundResource(R.drawable.ochur2);
            holder.txt.setText("");

        } else {
            holder.txt.setBackgroundResource(R.drawable.otraloir2);
            holder.txt.setText(list[i]);
            holder.txt.setTextColor(Color.parseColor("#FF9800"));

        }
        holder.txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mclick.onItemCauHoiClick(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txt;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txt=itemView.findViewById(R.id.ktu);
        }
    }
}
