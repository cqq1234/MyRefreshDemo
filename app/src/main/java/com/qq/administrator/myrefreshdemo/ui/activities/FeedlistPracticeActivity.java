package com.qq.administrator.myrefreshdemo.ui.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.qq.administrator.myrefreshdemo.R;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by 崔琦 on 2017/7/12 0012.
 * Describe : 微博列表
 */
public class FeedlistPracticeActivity extends AppCompatActivity {

    private static boolean isFirstEnter = true;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.smartLayout)
    SmartRefreshLayout smartLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practice_feedlist);
        ButterKnife.bind(this);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        if (isFirstEnter) {
            isFirstEnter = false;
            smartLayout.autoRefresh();
        }
    }
}
