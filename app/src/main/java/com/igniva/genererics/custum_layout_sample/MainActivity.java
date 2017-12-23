package com.igniva.genererics.custum_layout_sample;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.igniva.genererics.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {
    @BindView(R.id.tv_text)
    TextView tvText;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_recycler_view, new OnLayoutSet<View>() {
            @Override
            public void onSet(View view) {
                ButterKnife.bind(MainActivity.this, view);
                setUplayout();
                setDataInVIewObjects();
            }
        });


    }

    @Override
    public void setUplayout() {


    }

    @Override
    public void setDataInVIewObjects() {
        tvText.setText("aa gya callback");
    }
}
