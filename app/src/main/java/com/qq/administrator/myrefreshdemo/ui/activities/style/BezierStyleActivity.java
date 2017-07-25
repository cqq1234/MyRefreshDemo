package com.qq.administrator.myrefreshdemo.ui.activities.style;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
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

/**
 * Created by 崔琦 on 2017/7/13 0013.
 * Describe : .....
 */
public class BezierStyleActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;
    @Bind(R.id.smartLayout)
    SmartRefreshLayout smartLayout;

    public static boolean isFirstEnter = true;

    private enum Item {
        内容不偏移("下拉的时候列表内容停留在原位不动"),
        内容跟随偏移("下拉的时候列表内容跟随向下偏移"),
        橙色主题("更改为橙色主题颜色"),
        红色主题("更改为红色主题颜色"),
        绿色主题("更改为绿色主题颜色"),
        蓝色主题("更改为蓝色主题颜色"),
        ;
        public String name;
        Item(String name) {
            this.name = name;
        }
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_style_bezier);
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
        recyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
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
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        switch (Item.values()[position]) {
            case 内容不偏移:
                smartLayout.setEnableHeaderTranslationContent(false);
                break;
            case 内容跟随偏移:
                smartLayout.setEnableHeaderTranslationContent(true);
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
        smartLayout.setPrimaryColorsId(colorPrimary, android.R.color.white);
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(ContextCompat.getColor(this, colorPrimaryDark));
        }
    }
}
