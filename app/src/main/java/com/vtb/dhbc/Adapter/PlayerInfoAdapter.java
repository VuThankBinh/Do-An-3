package com.vtb.dhbc.Adapter;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.vtb.dhbc.CSDL;
import com.vtb.dhbc.ClassDL.ThongTinNguoiChoi;
import com.vtb.dhbc.R;

import java.util.List;

public class PlayerInfoAdapter extends RecyclerView.Adapter<PlayerInfoAdapter.PlayerInfoViewHolder> {
    private CSDL database;
    private List<ThongTinNguoiChoi> playerInfoList;
    private String infoName;
    private Context context;

    public PlayerInfoAdapter(Context context, List<ThongTinNguoiChoi> playerInfoList, String infoName) {
        this.context = context;
        this.playerInfoList = playerInfoList;
        this.infoName = infoName;
    }

    @NonNull
    @Override
    public PlayerInfoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.player_info_item, parent, false);
        database = new CSDL(context);
        return new PlayerInfoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlayerInfoViewHolder holder, int position) {
        ThongTinNguoiChoi playerInfo = playerInfoList.get(position);
        String fileAvt = "avt"+String.valueOf(playerInfo.getAvt_id()); // Lấy tên tệp ảnh từ đối tượng baiHat
        holder.tvName.setText(playerInfo.getName());
        // Kiểm tra xem id của hình ảnh có hợp lệ không
        int resourceId = context.getResources().getIdentifier(fileAvt, "drawable", context.getPackageName()); // Tìm ID tài nguyên dựa trên tên tệp ảnh
        if (resourceId != 0) {
            // Nếu id hợp lệ, đặt hình ảnh vào ImageView
            holder.ivAvatar.setImageResource(resourceId);
        } else {
            // Nếu không tìm thấy hình ảnh, xử lý theo nhu cầu của bạn, ví dụ đặt một hình ảnh mặc định
//                holder.ivImage.setImageResource(R.drawable.default_image);
        }
        switch (infoName) {
            case("ruby"):
                holder.tvInfo.setText(playerInfo.getRuby());
                holder.ivImage.setImageResource(R.drawable.ruby);
                break;
            case("level"):
                holder.tvInfo.setText(playerInfo.getLevel());
                holder.ivImage.setImageResource(R.drawable.level);
                break;
        }
        // Bind other views if necessary
    }

    @Override
    public int getItemCount() {
        return playerInfoList.size();
    }

    public class PlayerInfoViewHolder extends RecyclerView.ViewHolder {
        ImageView ivAvatar, ivImage;
        TextView tvName, tvInfo;
        public PlayerInfoViewHolder(@NonNull View itemView) {
            super(itemView);
            ivAvatar = itemView.findViewById(R.id.iv_leaderboard_info_avatar);
            ivImage = itemView.findViewById(R.id.iv_leaderboard_info_image);
            tvName = itemView.findViewById(R.id.tv_leaderboard_info_name);
            tvInfo = itemView.findViewById(R.id.tv_leaderboard_info_info);
        }
    }
}