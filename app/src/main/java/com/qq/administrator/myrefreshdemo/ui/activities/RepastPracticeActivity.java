package com.qq.administrator.myrefreshdemo.ui.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.github.mmin18.widget.RealtimeBlurView;
import com.qq.administrator.myrefreshdemo.R;
import com.qq.administrator.myrefreshdemo.ui.adapter.BaseRecyclerAdapter;
import com.qq.administrator.myrefreshdemo.ui.adapter.SmartViewHolder;
import com.qq.administrator.myrefreshdemo.utils.StatusBarUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;

import java.util.Arrays;
import java.util.Collection;

import butterknife.Bind;
import butterknife.ButterKnife;
import pl.droidsonroids.gif.GifImageView;

/**
 * Created by 崔琦 on 2017/7/12 0012.
 * Describe : .....
 */
public class RepastPracticeActivity extends AppCompatActivity {

    @Bind(R.id.gifView)
    GifImageView gifView;
    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;
    @Bind(R.id.smartLayout)
    SmartRefreshLayout smartLayout;
    @Bind(R.id.blurView)
    RealtimeBlurView blurView;
    @Bind(R.id.toolbar)
    Toolbar toolbar;

    private class Model {
        int imageId;
        int avatarId;
        String name;
        String nickname;
    }

    //设置第一次进入自动刷新
    private static boolean isFirstEnter = true;
    private BaseRecyclerAdapter<Model> mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practice_repast);
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
        //第一次进入自动刷新
        if (isFirstEnter){
            isFirstEnter = false;
            smartLayout.autoRefresh();
        }

        //初始化列表和监听
        View view = findViewById(R.id.recyclerView);
        if (view instanceof RecyclerView){
            RecyclerView recyclerView = (RecyclerView)view;
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(mAdapter = new BaseRecyclerAdapter<Model>(loadModels(),R.layout.listitem_practive_repast) {
                @Override
                protected void onBindViewHolder(SmartViewHolder holder, Model model, int position) {
                    holder.text(R.id.foodName,model.name);
                    holder.text(R.id.nickName,model.nickname);
                    holder.image(R.id.image, model.imageId);
                    holder.image(R.id.avatar,model.avatarId);
                }

                @Override
                public boolean areAllItemsEnabled() {
                    return false;
                }
            });

            smartLayout.setOnRefreshLoadmoreListener(new OnRefreshLoadmoreListener() {
                @Override
                public void onLoadmore(final RefreshLayout refreshlayout) {
                    smartLayout.getLayout().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mAdapter.loadmore(loadModels());
                            refreshlayout.finishLoadmore();
                            if (mAdapter.getCount() > 12){
                                Toast.makeText(getBaseContext(), "数据全部加载完毕", Toast.LENGTH_SHORT).show();
                                refreshlayout.setLoadmoreFinished(true);
                            }
                        }
                    },1000);
                }

                @Override
                public void onRefresh(final RefreshLayout refreshlayout) {
                    smartLayout.getLayout().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            refreshlayout.finishRefresh();
                        }
                    },2000);
                }
            });
        }

        //透明状态栏和间距处理
        StatusBarUtil.darkMode(this);
        StatusBarUtil.setPaddingSmart(this,view);
        StatusBarUtil.setPaddingSmart(this,toolbar);
        StatusBarUtil.setPaddingSmart(this,findViewById(R.id.blurView));
        StatusBarUtil.setMargin(this,findViewById(R.id.gifView));
    }

    /**
     * 模拟测试数据
     * */
    private Collection<Model> loadModels(){
        return Arrays.asList(
                new Model(){{
                    this.name = "弹夹香酥鸭";
                    this.nickname = "爱过那张大圆脸";
                    this.imageId = R.mipmap.image_practice_repast_1;
                    this.avatarId = R.mipmap.image_avatar_1;
                }},new Model(){{
                    this.name = "香菇蒸鸟蛋";
                    this.nickname = "淑女算个鸟屎";
                    this.imageId = R.mipmap.image_practice_repast_2;
                    this.avatarId = R.mipmap.image_avatar_2;
                }},new Model(){{
                    this.name = "花溪牛肉粉";
                    this.nickname = "性感妩媚嘴";
                    this.imageId = R.mipmap.image_practice_repast_3;
                    this.avatarId = R.mipmap.image_avatar_3;
                }},new Model(){{
                    this.name = "破酥包";
                    this.nickname = "一丝丝纯真牛奶";
                    this.imageId = R.mipmap.image_practice_repast_4;
                    this.avatarId = R.mipmap.image_avatar_4;
                }},new Model() {{
                    this.name = "盐菜饭";
                    this.nickname = "等着你回来干活";
                    this.imageId = R.mipmap.image_practice_repast_5;
                    this.avatarId = R.mipmap.image_avatar_5;
                }},new Model() {{
                    this.name = "米豆腐";
                    this.nickname = "宝宝树人陪";
                    this.imageId = R.mipmap.image_practice_repast_6;
                    this.avatarId = R.mipmap.image_avatar_6;
                }});
    }
}
