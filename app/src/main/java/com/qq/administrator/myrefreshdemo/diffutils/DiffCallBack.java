package com.qq.administrator.myrefreshdemo.diffutils;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;

import java.util.List;

/**
 * Created by 崔琦 on 2017/7/25 0025.
 * Describe : 核心类 用来判断新旧Item是否相等
 */
public class DiffCallBack extends DiffUtil.Callback{

    private List<TestBean> mOldDatas,mNewDatas;//看名字

    public DiffCallBack(List<TestBean> mOldDatas, List<TestBean> mNewDatas) {
        this.mOldDatas = mOldDatas;
        this.mNewDatas = mNewDatas;
    }

    @Override
    public int getOldListSize() {
        return mOldDatas == null ? 0 : mOldDatas.size();
    }

    @Override
    public int getNewListSize() {
        return mNewDatas != null ? mNewDatas.size() : 0;
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return mOldDatas.get(oldItemPosition).getName().equals(mNewDatas.get(newItemPosition).getName());
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        TestBean beanOld = mOldDatas.get(oldItemPosition);
        TestBean beanNew = mNewDatas.get(newItemPosition);
        //如果有内容不同，就返回false
        if (!beanOld.getDesc().equals(beanNew.getDesc())){
            return false;
        }
        if (!beanOld.getLove().equals(beanNew.getLove())){
            return false;
        }
        if (beanOld.getPic1() != beanNew.getPic1()){
            return false;
        }
        if (beanOld.getPic2() != beanNew.getPic2()){
            return false;
        }
        if (beanOld.getPic3() != beanNew.getPic3()){
            return false;
        }
        return true;//默认两个data内容是相同的
    }

    @Nullable
    @Override
    public Object getChangePayload(int oldItemPosition, int newItemPosition) {
        // 定向刷新中的部分更新
        // 效率最高
        //只是没有了ItemChange的白光一闪动画，（反正我也觉得不太重要）
        TestBean oldBean = mOldDatas.get(oldItemPosition);
        TestBean newBean = mNewDatas.get(newItemPosition);
        //核心字段不用比较，一定相等
        Bundle payload = new Bundle();
        if (!oldBean.getDesc().equals(newBean.getDesc())){
            payload.putString("KEY_DESC",newBean.getDesc());
        }
        if (!oldBean.getLove().equals(newBean.getLove())){
            payload.putString("KEY_LOVE",newBean.getLove());
        }
        if (oldBean.getPic1() != newBean.getPic1()){
            payload.putInt("KEY_PIC1",newBean.getPic1());
        }
        if (oldBean.getPic2() != newBean.getPic2()){
            payload.putInt("KEY_PIC2",newBean.getPic1());
        }
        if (oldBean.getPic3() != newBean.getPic3()){
            payload.putInt("KEY_PIC3",newBean.getPic1());
        }
        if (payload.size() == 0){
            return null;//如果没有变化就传空
        }
        return payload;
    }
}
