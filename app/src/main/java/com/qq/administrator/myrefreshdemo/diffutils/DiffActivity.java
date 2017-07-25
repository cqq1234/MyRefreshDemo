package com.qq.administrator.myrefreshdemo.diffutils;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.qq.administrator.myrefreshdemo.R;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by 崔琦 on 2017/7/25 0025.
 * Describe : .....
 */
public class DiffActivity extends AppCompatActivity {
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;
    @Bind(R.id.smartLayout)
    SmartRefreshLayout smartLayout;

    private List<TestBean> mDatas;
    private DiffAdapter mAdapter;

    private int count = 1;
    private static final int H_CODE_UPDATE = 1;
    private List<TestBean> mNewDatas;//增加一个变量暂存newList
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case H_CODE_UPDATE:
                    DiffUtil.DiffResult diffResult = (DiffUtil.DiffResult)msg.obj;
                    //利用DiffUtil.DiffResult对象的dispatchUpdatesTo（）方法，传入RecyclerView的Adapter
                    diffResult.dispatchUpdatesTo(mAdapter);
                    mDatas = mNewDatas;
                    mAdapter.setData(mDatas);
                    break;
            }
        }
    };
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diff);
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
        initData();
    }

    private void initData(){
        mDatas = new ArrayList<>();
        mDatas.add(new TestBean("昵称：温良寄她","爱好：篮球 台球 唱歌 嘻哈","描述：哥哥姐姐看看我这张帅脸吧",R.mipmap.image_avatar_1,R.mipmap.image_avatar_2,R.mipmap.image_avatar_3,"关注"));
        mDatas.add(new TestBean("昵称：撒娇女人最好命","爱好：篮球 台球 唱歌 Android","描述：哥哥姐姐看看我这张帅脸吧",R.mipmap.image_avatar_1,R.mipmap.image_avatar_2,R.mipmap.image_avatar_3,"关注"));
        mDatas.add(new TestBean("昵称：一抹透亮外加蛋疼","爱好：篮球 Java 唱歌 嘻哈","描述：哥哥姐姐看看我这张帅脸吧",R.mipmap.image_avatar_1,R.mipmap.image_avatar_2,R.mipmap.image_avatar_3,"关注"));
        mDatas.add(new TestBean("昵称：一抹透亮外加蛋疼","爱好：php 台球 唱歌 嘻哈","描述：哥哥姐姐看看我这张帅脸吧",R.mipmap.image_avatar_1,R.mipmap.image_avatar_2,R.mipmap.image_avatar_3,"关注"));
        mDatas.add(new TestBean("昵称：一抹透亮外加蛋疼","爱好：篮球 台球 AI 嘻哈","描述：哥哥姐姐看看我这张帅脸吧",R.mipmap.image_avatar_1,R.mipmap.image_avatar_2,R.mipmap.image_avatar_3,"关注"));

        mAdapter = new DiffAdapter(this,mDatas);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        smartLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(final RefreshLayout refreshlayout) {
                ((View) refreshlayout).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                       mNewDatas = new ArrayList<TestBean>();
                        try {
                            for (TestBean bean : mDatas){
                                mNewDatas.add(bean.clone());
                            }
                            count++;

                            //模拟修改数据
                            mNewDatas.get(0).setDesc("描述：Android++Java++Php" + count);
                            mNewDatas.get(0).setLove("爱好：街球" + count + " 游戏"+ count + " hipop 喊麦");
                            mNewDatas.get(0).setPic3(R.mipmap.streetball);
                            //模拟移除数据
                            TestBean testBean = mNewDatas.get(1);
                            mNewDatas.remove(testBean);
                            //mNewDatas.add(testBean);
                            //模拟新增数据
                            mNewDatas.add(new TestBean("昵称：就你牛逼就你装","爱好：吹牛逼 玩 抽烟 喝酒","描述：我这么帅的脸你都不看",R.mipmap.image_avatar_5,R.mipmap.streetball,R.mipmap.image_avatar_6,"关注"));
                            //利用DiffUtil.calculateDiff()方法，传入一个规则DiffUtil.Callback对象，和是否检测移动item的 boolean变量，得到DiffUtil.DiffResult 的对象
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new DiffCallBack(mDatas,mNewDatas),true);
                                    Message message = mHandler.obtainMessage(H_CODE_UPDATE);
                                    message.obj = diffResult;
                                    message.sendToTarget();
                                }
                            }).start();
                        }catch (CloneNotSupportedException e) {
                            e.printStackTrace();
                        }
                        refreshlayout.finishRefresh();
                    }
                }, 10);
            }
        });
    }

}
