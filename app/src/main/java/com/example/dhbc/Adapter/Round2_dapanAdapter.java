package com.example.dhbc.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dhbc.Interface.ItemClick_dapan;
import com.example.dhbc.R;

public class Round2_dapanAdapter extends  RecyclerView.Adapter<Round2_dapanAdapter.ViewHolder>{
    private Context context;
    private ItemClick_dapan mclick;
    private String[]list;

    public Round2_dapanAdapter(Context context, String[] list, ItemClick_dapan mclick) {
        this.context = context;
        this.mclick = mclick;
        this.list = list;
    }

    @NonNull
    @Override
    public Round2_dapanAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.dong_dapan_round2,parent,false);

        return new Round2_dapanAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull Round2_dapanAdapter.ViewHolder holder, int i) {
        holder.txt.setText(list[i].toUpperCase());

        holder.txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mclick.onItemClick(i);

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
