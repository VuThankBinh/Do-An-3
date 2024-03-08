package com.example.dhbc;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    RecyclerView listcauhoi,dapan;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ArrayList<String> arr = new ArrayList<String>() {{
            add("B");
            add("I");
            add("N");
            add("H");
            add(" ");
            add("B");
            add("O");
            add("N");
            add("K");
        }};
        ArrayList<String> arr2 = new ArrayList<String>() {{
            add("A");
            add("B");
            add("X");
            add("C");
            add("D");
            add("E");
            add("R");
            add("D");
            add("E");
        }};
        listcauhoi=findViewById(R.id.listcauhoi);
        dapan=findViewById(R.id.dapan);
        CauHoiAdapter adapter=new CauHoiAdapter(this,arr);
        DapAnAdapter adap=new DapAnAdapter(this,arr2);

        FlexboxLayoutManager layoutManager = new FlexboxLayoutManager(getApplicationContext());
        layoutManager.setFlexDirection(FlexDirection.ROW);
        layoutManager.setJustifyContent(JustifyContent.FLEX_START);
        FlexboxLayoutManager layoutManager2 = new FlexboxLayoutManager(getApplicationContext());
        layoutManager.setFlexDirection(FlexDirection.ROW);
        layoutManager.setJustifyContent(JustifyContent.FLEX_START);

//        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
//        LinearLayoutManager layoutManager2 = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);

        listcauhoi.setLayoutManager(layoutManager);
        dapan.setLayoutManager(layoutManager2);
        listcauhoi.setAdapter(adapter);
        dapan.setAdapter(adap);

    }
}
class CenterLayoutManager extends LinearLayoutManager {

    public CenterLayoutManager(Context context) {
        super(context);
    }

    public CenterLayoutManager(MainActivity context, int horizontal, boolean b) {
        super(context,horizontal,b);
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        super.onLayoutChildren(recycler, state);

        int totalHeight = getHeight();

        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            int width = getDecoratedMeasuredWidth(child);
            int height = getDecoratedMeasuredHeight(child);

            int left = (getWidth() - width) / 2;
            int top = (totalHeight - height) / 2;

            layoutDecorated(child, left, top, left + width, top + height);
        }
    }

    @Override
    public boolean checkLayoutParams(RecyclerView.LayoutParams lp) {
        return super.checkLayoutParams(lp);
    }

    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return super.generateDefaultLayoutParams();
    }

    @Override
    public RecyclerView.LayoutParams generateLayoutParams(ViewGroup.LayoutParams lp) {
        return super.generateLayoutParams(lp);
    }

    @Override
    public int scrollHorizontallyBy(int dx, RecyclerView.Recycler recycler, RecyclerView.State state) {
        return super.scrollHorizontallyBy(dx, recycler, state);
    }
}
