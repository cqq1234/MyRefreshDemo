package com.qq.administrator.myrefreshdemo.ui.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.qq.administrator.myrefreshdemo.R;
import com.qq.administrator.myrefreshdemo.ui.activities.FeedlistPracticeActivity;
import com.qq.administrator.myrefreshdemo.ui.activities.ProfilePracticeActivity;
import com.qq.administrator.myrefreshdemo.ui.activities.RepastPracticeActivity;
import com.qq.administrator.myrefreshdemo.ui.activities.WebviewPracticeActivity;
import com.qq.administrator.myrefreshdemo.ui.activities.WeiboPracticeActivity;
import com.qq.administrator.myrefreshdemo.ui.adapter.BaseRecyclerAdapter;
import com.qq.administrator.myrefreshdemo.ui.adapter.SmartViewHolder;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

import static android.R.layout.simple_list_item_2;
import static android.support.v7.widget.DividerItemDecoration.VERTICAL;

/**
 * Created by 崔琦 on 2017/7/12 0012.
 * Describe : 实战演示
 */
public class RefreshPracticeFragment extends Fragment implements AdapterView.OnItemClickListener{

    private enum Item{
        Repast("餐饮美食-简单自定义Header-外边距magin",RepastPracticeActivity.class),
        Profile("个人中心-OverScroll",ProfilePracticeActivity.class),
        Webview("网页引用-WebView",WebviewPracticeActivity.class),
        FeedList("微博列表-智能识别",FeedlistPracticeActivity.class),
        Weibo("微博主页-CoordinatorLayout",WeiboPracticeActivity.class)
        ;
        public String name;
        public Class<?> clazz;

        Item(String name ,Class<?> clazz) {
            this.name = name;
            this.clazz = clazz;
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_refresh_practive,container,false);
    }

    @Override
    public void onViewCreated(View root, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(root, savedInstanceState);
        View view = root.findViewById(R.id.recyclerView);
        if (view instanceof RecyclerView){
            RecyclerView recyclerView = (RecyclerView)view;
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            recyclerView.addItemDecoration(new DividerItemDecoration(getContext(),VERTICAL));
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
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        Item item = Item.values()[position];
        if (Activity.class.isAssignableFrom(item.clazz)){
            startActivity(new Intent(getContext(),item.clazz));
        }else if (RefreshHeader.class.isAssignableFrom(item.clazz)){
            try {
                Constructor<?> constructor = item.clazz.getConstructor(Context.class);
                RefreshHeader header = (RefreshHeader) constructor.newInstance(getContext());
                RefreshLayout layout = (RefreshLayout)getView().findViewById(R.id.smartLayout);
                layout.setRefreshHeader(header);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (java.lang.InstantiationException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }

    }
}
