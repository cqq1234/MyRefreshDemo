package com.qq.administrator.myrefreshdemo.ui.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
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
import com.qq.administrator.myrefreshdemo.ui.activities.ExperimentActivity;
import com.qq.administrator.myrefreshdemo.ui.activities.style.BezierStyleActivity;
import com.qq.administrator.myrefreshdemo.ui.activities.style.CircleStyleActivity;
import com.qq.administrator.myrefreshdemo.ui.activities.style.ClassicsStyleActivity;
import com.qq.administrator.myrefreshdemo.ui.activities.style.DeliveryStyleActivity;
import com.qq.administrator.myrefreshdemo.ui.activities.style.DropboxStyleActivity;
import com.qq.administrator.myrefreshdemo.ui.activities.style.FlyRefreshStyleActivity;
import com.qq.administrator.myrefreshdemo.ui.activities.style.FunGameBattleCityStyleActivity;
import com.qq.administrator.myrefreshdemo.ui.activities.style.FunGameHitBlockStyleActivity;
import com.qq.administrator.myrefreshdemo.ui.activities.style.MaterialStyleActivity;
import com.qq.administrator.myrefreshdemo.ui.activities.style.PhoenixStyleActivity;
import com.qq.administrator.myrefreshdemo.ui.activities.style.StoreHouseStyleActivity;
import com.qq.administrator.myrefreshdemo.ui.activities.style.TaurusStyleActivity;
import com.qq.administrator.myrefreshdemo.ui.activities.style.WaterDropStyleActivity;
import com.qq.administrator.myrefreshdemo.ui.activities.style.WaveSwipStyleActivity;
import com.qq.administrator.myrefreshdemo.ui.adapter.BaseRecyclerAdapter;
import com.qq.administrator.myrefreshdemo.ui.adapter.SmartViewHolder;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;

import java.lang.reflect.Constructor;
import java.util.Arrays;

import static android.R.layout.simple_list_item_2;
import static android.support.v7.widget.DividerItemDecoration.VERTICAL;

/**
 * Created by 崔琦 on 2017/7/12 0012.
 * Describe :
 */
public class RefreshStylesFragment extends Fragment implements AdapterView.OnItemClickListener{

    private enum Item{
        Delivery(R.string.title_activity_style_delivery,DeliveryStyleActivity.class),
        Dropbox(R.string.title_activity_style_dropbox, DropboxStyleActivity.class),
        FlyRefresh(R.string.title_activity_style_flyrefresh, FlyRefreshStyleActivity.class),
        WaveSwipe(R.string.title_activity_style_wave_swip, WaveSwipStyleActivity.class),
        WaterDrop(R.string.title_activity_style_water_drop, WaterDropStyleActivity.class),
        Material(R.string.title_activity_style_material, MaterialStyleActivity.class),
        Phoenix(R.string.title_activity_style_phoenix, PhoenixStyleActivity.class),
        Taurus(R.string.title_activity_style_taurus, TaurusStyleActivity.class),
        Bezier(R.string.title_activity_style_bezier, BezierStyleActivity.class),
        Circle(R.string.title_activity_style_circle, CircleStyleActivity.class),
        FunGameHitBlock(R.string.title_activity_style_fungame_hitblock, FunGameHitBlockStyleActivity.class),
        FunGameBattleCity(R.string.title_activity_style_fungame_battlecity, FunGameBattleCityStyleActivity.class),
        StoreHouse(R.string.title_activity_style_storehouse, StoreHouseStyleActivity.class),
        Classics(R.string.title_activity_style_classics, ClassicsStyleActivity.class),
        ;
        public int nameId;
        public Class<?> clazz;
        Item(@StringRes int nameId, Class<?> clazz) {
            this.nameId = nameId;
            this.clazz = clazz;
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_refresh_styles,container,false);
    }

    @Override
    public void onViewCreated(View root, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(root, savedInstanceState);
        View view = root.findViewById(R.id.recyclerView);
        if (view instanceof RecyclerView){
            RecyclerView recyclerView = (RecyclerView)view;
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), VERTICAL));
            recyclerView.setAdapter(new BaseRecyclerAdapter<Item>(Arrays.asList(Item.values()),simple_list_item_2,this) {

                @Override
                public boolean areAllItemsEnabled() {
                    return false;
                }

                @Override
                protected void onBindViewHolder(SmartViewHolder holder, Item model, int position) {
                    holder.text(android.R.id.text1, model.name());
                    holder.text(android.R.id.text2, model.nameId);
                    holder.textColorId(android.R.id.text2, R.color.colorTextAssistant);
                }
            });
        }
        root.findViewById(R.id.toolbar).setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                startActivity(new Intent(getContext(), ExperimentActivity.class));
                return false;
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
                RefreshHeader header = (RefreshHeader) constructor.newInstance(getContext());
                RefreshLayout layout = (RefreshLayout) getView().findViewById(R.id.smartLayout);
                layout.setRefreshHeader(header);
                if (!(header instanceof ClassicsHeader)){
                    layout.setPrimaryColorsId(R.color.colorPrimary, android.R.color.white);
                }
                layout.autoRefresh();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

}
