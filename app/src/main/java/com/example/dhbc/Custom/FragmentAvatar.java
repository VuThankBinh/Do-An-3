package com.example.dhbc.Custom;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.dhbc.Adapter.SanPham_avt_Adapter;
import com.example.dhbc.CSDL;
import com.example.dhbc.ClassDL.SanPham;
import com.example.dhbc.Interface.ItemClick_dapan;
import com.example.dhbc.R;
import com.example.dhbc.views.cuahangvatpham;

import java.util.ArrayList;

public class FragmentAvatar extends Fragment implements ItemClick_dapan {
    CSDL csdl;
    SanPham_avt_Adapter adapter_avt;
    ArrayList<SanPham> dsAVT;
    RecyclerView recyclerView_avt;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_avatar, container, false);
        recyclerView_avt = view.findViewById(R.id.rv_avatar);
        csdl=new CSDL(getContext());
        CapNhatDuLieu();
        return view;
    }
    private void CapNhatDuLieu() {
        cuahangvatpham.CapNhatDuLieu(getContext());
        dsAVT=csdl.HienDS_AVT();
        adapter_avt=new SanPham_avt_Adapter(getContext(),dsAVT,this);
        GridLayoutManager layoutManager1 = new GridLayoutManager(getContext(), 3, GridLayoutManager.VERTICAL, false);

        recyclerView_avt.setLayoutManager(layoutManager1);
        recyclerView_avt.setAdapter(adapter_avt);

    }
    @Override
    public void onItemClick(int position) {
        if(dsAVT.get(position).getTinhtrang()==1){
            csdl.SuaThongTinNhanVat(csdl.HienThongTinNhanVat().getName(),dsAVT.get(position).getId(),csdl.HienThongTinNhanVat().getKhung_id());
            CapNhatDuLieu();
        }
        if(dsAVT.get(position).getTinhtrang()==0){
            if(csdl.HienThongTinNhanVat().getRuby()>=dsAVT.get(position).getPrice()){
                csdl.UpdateSanPham("avt",dsAVT.get(position).getId());
                csdl.UpdateRuby(getContext(),-dsAVT.get(position).getPrice());
                CapNhatDuLieu();
            }
            else {
                Toast.makeText(getContext(), "Bạn không đủ ruby để mua vật phẩm này", Toast.LENGTH_SHORT).show();
            }
        }
    }
}