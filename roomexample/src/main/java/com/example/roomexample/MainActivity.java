package com.example.roomexample;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.activity_main)
    RelativeLayout mActivityMain;

    ArrayList<String> arrayList = new ArrayList<>();
    private AlertDialog dialog;

    @Override
    public void setUplayout() {

    }

    @Override
    public void setDataInVIewObjects() {
        arrayList.clear();
        arrayList.add("Room Database with single table");
        arrayList.add("Room Database with multiple table");

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        MainActivityAdapter mainActivityAdapter = new MainActivityAdapter(arrayList);
        mRecyclerView.setAdapter(mainActivityAdapter);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main, new OnLayoutSet<View>() {
                @Override
                public void onSet(View view) {
                    ButterKnife.bind(MainActivity.this, view);
                    setUplayout();
                    setDataInVIewObjects();
                }
            });
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }



    public class MainActivityAdapter extends RecyclerView.Adapter<MainActivityAdapter.ViewHolder> {

        ViewHolder viewHolder;
        ArrayList<String> arrayList=new ArrayList<>();

        public MainActivityAdapter(ArrayList<String> arrayList) {
            this.arrayList = arrayList;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater= LayoutInflater.from(parent.getContext());
            View view=inflater.inflate(R.layout.item_main_activity,parent,false);
            viewHolder=new ViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            viewHolder.tv_main.setText(arrayList.get(position));
        }

        @Override
        public int getItemCount() {
            return arrayList.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            @BindView(R.id.tv_main)
            TextView tv_main;

            public ViewHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }


            @OnClick(R.id.tv_main)
            public void onViewClicked() {
                try {
                    startActivity(new Intent(MainActivity.this, RoomDataBaseWithSingelTableActivity.class));
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }


        }
    }

}
