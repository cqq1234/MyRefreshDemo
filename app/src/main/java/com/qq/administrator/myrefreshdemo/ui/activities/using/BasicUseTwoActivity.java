package com.qq.administrator.myrefreshdemo.ui.activities.using;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.qq.administrator.myrefreshdemo.R;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by 崔琦 on 2017/7/14 0014.
 * Describe : .....
 */
public class BasicUseTwoActivity extends AppCompatActivity {
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;
    @Bind(R.id.smartLayout)
    SmartRefreshLayout smartLayout;

    private List mData;
    private MyRecycleAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basictwo_use);
        ButterKnife.bind(this);
        initView();
    }

    private void initView(){
        mData = new ArrayList();

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        adapter = new MyRecycleAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        smartLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(final RefreshLayout refreshlayout) {
                ((View) refreshlayout).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mData.clear();
                        //创建10条初始数据
                        for (int i = 0; i < 10; i++){
                            String str = i + "号";
                            mData.add(str);
                        }
                        adapter.notifyDataSetChanged();
                        refreshlayout.finishRefresh();
                        refreshlayout.setLoadmoreFinished(false);
                    }
                }, 2000);
            }
        });

        smartLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(final RefreshLayout refreshlayout) {
                ((View) refreshlayout).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        for (int i = 0;i < 10;i++){
                            mData.add(i + "嘎嘎");
                        }
                        adapter.notifyDataSetChanged();
                        refreshlayout.finishLoadmore();
                        if (adapter.getItemCount() > 59){
                            Toast.makeText(getBaseContext(), "数据全部加载完毕", Toast.LENGTH_SHORT).show();
                            refreshlayout.setLoadmoreFinished(true);//将不会再次触发加载更多事件
                        }
                    }
                },1000);
            }
        });

        //自动刷新
        smartLayout.autoRefresh();
    }


    private class MyRecycleAdapter extends RecyclerView.Adapter<MyRecycleAdapter.ViewHolder>{
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View itemView = inflater.inflate(R.layout.item_two_recycle_basic,parent,false);
            return new ViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {

        }

        @Override
        public int getItemCount() {
            return mData.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder{
            public ViewHolder(View itemView) {
                super(itemView);
            }
        }
    }
}
