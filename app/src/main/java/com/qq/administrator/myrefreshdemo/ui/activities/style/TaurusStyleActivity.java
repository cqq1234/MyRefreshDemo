package com.qq.administrator.myrefreshdemo.ui.activities.style;

import android.content.res.ColorStateList;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;

import com.qq.administrator.myrefreshdemo.R;
import com.qq.administrator.myrefreshdemo.ui.adapter.BaseRecyclerAdapter;
import com.qq.administrator.myrefreshdemo.ui.adapter.SmartViewHolder;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.Arrays;

import butterknife.Bind;
import butterknife.ButterKnife;

import static android.R.layout.simple_list_item_2;
import static android.support.v7.widget.DividerItemDecoration.VERTICAL;

/**
 * Created by 崔琦 on 2017/7/13 0013.
 * Describe : .....
 */
public class TaurusStyleActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.toolbar_layout)
    CollapsingToolbarLayout toolbarLayout;
    @Bind(R.id.app_bar)
    AppBarLayout appBar;
    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;
    @Bind(R.id.smartLayout)
    SmartRefreshLayout smartLayout;
    @Bind(R.id.fab)
    FloatingActionButton fab;
    private static boolean isFirstEnter = true;

    private enum Item {
        折叠("折叠AppBarLayout，变成正常的列表页面"),
        展开("展开AppBarLayout，变成可伸展头部的页面"),
        橙色主题("更改为橙色主题颜色"),
        红色主题("更改为红色主题颜色"),
        绿色主题("更改为绿色主题颜色"),
        蓝色主题("更改为蓝色主题颜色"),;
        public String name;

        Item(String name) {
            this.name = name;
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_style_taurus);
        ButterKnife.bind(this);
        initView();
    }
    private void initView(){
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        if (isFirstEnter){
            isFirstEnter = false;
            smartLayout.autoRefresh();
        }

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(new BaseRecyclerAdapter<Item>(Arrays.asList(Item.values()),simple_list_item_2,this) {

            @Override
            public boolean areAllItemsEnabled() {
                return false;
            }

            @Override
            protected void onBindViewHolder(SmartViewHolder holder, Item model, int position) {
                holder.text(android.R.id.text1, model.name());
                holder.text(android.R.id.text2, model.name);
                holder.textColorId(android.R.id.text2, R.color.colorTextAssistant);
            }
        });

        /**
         * 监听 AppBarLayout 的关闭和开启 给 FlyView（纸飞机） 和 ActionButton 设置关闭隐藏动画
         */
        appBar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {

            boolean misAppbarExpand = true;
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                int scrollRange = appBarLayout.getTotalScrollRange();
                float fraction = 1f * (scrollRange + verticalOffset) / scrollRange;
                if (fraction < 0.1 && misAppbarExpand){
                    misAppbarExpand = false;
                    fab.animate().scaleX(0).scaleY(0);
                }
                if (fraction >0.8 && !misAppbarExpand){
                    misAppbarExpand = true;
                    fab.animate().scaleX(1).scaleY(1);
                }
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (Item.values()[position]) {
            case 折叠:
                appBar.setExpanded(false, true);
                appBar.setEnabled(false);
                recyclerView.setNestedScrollingEnabled(false);
                break;
            case 展开:
                appBar.setEnabled(true);
                appBar.setExpanded(true, true);
                recyclerView.setNestedScrollingEnabled(true);
                break;
            case 蓝色主题:
                setThemeColor(R.color.colorPrimaryOne, R.color.colorPrimaryDarkOne);
                break;
            case 绿色主题:
                setThemeColor(android.R.color.holo_green_light, android.R.color.holo_green_dark);
                break;
            case 红色主题:
                setThemeColor(android.R.color.holo_red_light, android.R.color.holo_red_dark);
                break;
            case 橙色主题:
                setThemeColor(android.R.color.holo_orange_light, android.R.color.holo_orange_dark);
                break;
        }
        smartLayout.autoRefresh();
    }

    private void setThemeColor(int colorPrimary, int colorPrimaryDark) {
        toolbar.setBackgroundResource(colorPrimary);
        appBar.setBackgroundResource(colorPrimary);
        toolbarLayout.setContentScrimResource(colorPrimary);
        smartLayout.setPrimaryColorsId(colorPrimary, android.R.color.white);
        fab.setBackgroundColor(ContextCompat.getColor(this, colorPrimaryDark));
        fab.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, colorPrimaryDark)));
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(ContextCompat.getColor(this, colorPrimaryDark));
        }
    }
}
