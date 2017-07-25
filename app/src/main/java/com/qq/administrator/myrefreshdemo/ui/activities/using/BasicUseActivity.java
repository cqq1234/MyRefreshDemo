package com.qq.administrator.myrefreshdemo.ui.activities.using;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.qq.administrator.myrefreshdemo.R;
import com.qq.administrator.myrefreshdemo.ui.adapter.BaseRecyclerAdapter;
import com.qq.administrator.myrefreshdemo.ui.adapter.SmartViewHolder;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.Arrays;
import java.util.Collection;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;

import static android.R.layout.simple_list_item_2;

/**
 * Created by 崔琦 on 2017/7/14 0014.
 * Describe : 基本使用方法
 */
public class BasicUseActivity extends AppCompatActivity {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.listview)
    ListView listview;
    @Bind(R.id.smartLayout)
    SmartRefreshLayout smartLayout;
    private BaseRecyclerAdapter<Void> mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic_use);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        listview.setAdapter(mAdapter = new BaseRecyclerAdapter<Void>(simple_list_item_2) {
            @Override
            protected void onBindViewHolder(SmartViewHolder holder, Void model, int position) {
                holder.text(android.R.id.text1, String.format(Locale.CHINA, "第%02d条数据", position));
                holder.text(android.R.id.text2, String.format(Locale.CHINA, "这是测试的第%02d条数据", position));
                holder.textColorId(android.R.id.text2, R.color.colorTextAssistant);
            }

            @Override
            public boolean areAllItemsEnabled() {
                return false;
            }
        });

        smartLayout.setEnableAutoLoadmore(true);//开启自动加载功能 不是必须
        smartLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                ((View) refreshlayout).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mAdapter.refresh(initData());
                        smartLayout.finishRefresh();
                        smartLayout.setLoadmoreFinished(false);
                    }
                },2000);
            }
        });
        smartLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                ((View) refreshlayout).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mAdapter.loadmore(initData());
                        smartLayout.finishLoadmore();
                        if (mAdapter.getItemCount() > 60){
                            Toast.makeText(getApplication(), "数据全部加载完毕", Toast.LENGTH_SHORT).show();
                            smartLayout.setLoadmoreFinished(true);
                        }
                    }
                },2000);
            }
        });
        smartLayout.autoRefresh();
    }
    private Collection<Void> initData() {
        return Arrays.asList(null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null);
    }
}
