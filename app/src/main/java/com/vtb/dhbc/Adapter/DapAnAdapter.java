package com.vtb.dhbc.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.vtb.dhbc.Interface.ItemClick_dapan;
import com.vtb.dhbc.R;

import java.util.ArrayList;

public class DapAnAdapter extends  RecyclerView.Adapter<DapAnAdapter.ViewHolder> {
    private Context context;
    private ItemClick_dapan mclick;
    public DapAnAdapter(Context context, ArrayList<String> list,ItemClick_dapan dd) {
        this.context = context;
        this.list = list;
        this.mclick=dd;
    }
    ArrayList<String> list;

    @NonNull
    @Override
    public DapAnAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v=LayoutInflater.from(context).inflate(R.layout.dongdapan,parent,false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull DapAnAdapter.ViewHolder holder, int i) {
        holder.txt.setText(list.get(i).toUpperCase());

        holder.txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mclick.onItemClick(i);

            }
        });
        //bắt sự kiện xóa, sửa


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
