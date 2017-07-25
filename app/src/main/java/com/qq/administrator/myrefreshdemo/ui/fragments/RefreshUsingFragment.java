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
import com.qq.administrator.myrefreshdemo.diffutils.DiffActivity;
import com.qq.administrator.myrefreshdemo.ui.activities.using.BasicUseActivity;
import com.qq.administrator.myrefreshdemo.ui.activities.using.BasicUseTwoActivity;
import com.qq.administrator.myrefreshdemo.ui.adapter.BaseRecyclerAdapter;
import com.qq.administrator.myrefreshdemo.ui.adapter.SmartViewHolder;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

import butterknife.Bind;
import butterknife.ButterKnife;

import static android.R.layout.simple_list_item_2;
import static android.support.v7.widget.DividerItemDecoration.VERTICAL;

/**
 * Created by 崔琦 on 2017/7/12 0012.
 * Describe : .....
 */
public class RefreshUsingFragment extends Fragment implements AdapterView.OnItemClickListener {

    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;
    @Bind(R.id.smartLayout)
    SmartRefreshLayout smartLayout;

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    private enum Item {
        Basic("基本的使用", BasicUseActivity.class),
        BasicOne("基本使用第二弹",BasicUseTwoActivity.class),
        DiffOne("DiffUtil的简单使用",DiffActivity.class)

        ;
        public String name;
        public Class<?> clazz;

        Item(String name, Class<?> clazz) {
            this.name = name;
            this.clazz = clazz;
        }

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_refresh_using, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), VERTICAL));
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
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Item item = Item.values()[position];
        if (Activity.class.isAssignableFrom(item.clazz)){
            startActivity(new Intent(getContext(),item.clazz));
        }else if (RefreshHeader.class.isAssignableFrom(item.clazz)){
            try {
                Constructor<?> constructor = item.clazz.getConstructor(Context.class);
                RefreshHeader header = (RefreshHeader)constructor.newInstance();
                smartLayout.setRefreshHeader(header);
                if (!(header instanceof ClassicsHeader)){
                    smartLayout.setPrimaryColorsId(R.color.colorPrimary,android.R.color.white);
                }
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
