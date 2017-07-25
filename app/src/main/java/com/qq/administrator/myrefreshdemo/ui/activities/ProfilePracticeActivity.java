package com.qq.administrator.myrefreshdemo.ui.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.qq.administrator.myrefreshdemo.R;
import com.qq.administrator.myrefreshdemo.utils.StatusBarUtil;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by 崔琦 on 2017/7/12 0012.
 * Describe : 个人中心
 */
public class ProfilePracticeActivity extends AppCompatActivity {

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practice_profile);
        ButterKnife.bind(this);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        //状态栏透明和间距处理
        StatusBarUtil.immersive(this);
        StatusBarUtil.setPaddingSmart(this, toolbar);
        StatusBarUtil.setPaddingSmart(this, findViewById(R.id.profile));
        StatusBarUtil.setPaddingSmart(this, findViewById(R.id.blurview));
    }
}
