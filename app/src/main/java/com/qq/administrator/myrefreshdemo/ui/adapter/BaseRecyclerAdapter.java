package com.qq.administrator.myrefreshdemo.ui.adapter;

import android.database.DataSetObservable;
import android.database.DataSetObserver;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by 崔琦 on 2017/7/12 0012.
 * Describe : recycle基础适配器
 */
public abstract class BaseRecyclerAdapter<T> extends RecyclerView.Adapter<SmartViewHolder> implements ListAdapter{

    private final int mLayoutId;
    private final List<T> mList;
    private AdapterView.OnItemClickListener mListener;

    public BaseRecyclerAdapter(@LayoutRes int mLayoutId) {
        setHasStableIds(false);
        this.mList = new ArrayList<>();
        this.mLayoutId = mLayoutId;
    }

    public BaseRecyclerAdapter(Collection<T> collection, @LayoutRes int mLayoutId) {
        setHasStableIds(false);
        this.mList = new ArrayList<>(collection);
        this.mLayoutId = mLayoutId;
    }

    public BaseRecyclerAdapter(Collection<T> collection, @LayoutRes int mLayoutId, AdapterView.OnItemClickListener mListener) {
        setHasStableIds(false);
        setOnItemClickListener(mListener);
        this.mLayoutId = mLayoutId;
        this.mList = new ArrayList<>(collection);
    }

    @Override
    public SmartViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new SmartViewHolder(LayoutInflater.from(parent.getContext()).inflate(mLayoutId,parent,false),mListener);
    }

    @Override
    public void onBindViewHolder(SmartViewHolder holder, int position) {
        onBindViewHolder(holder,position < mList.size() ? mList.get(position) : null,position);
    }

    protected abstract void onBindViewHolder(SmartViewHolder holder, T model, int position);

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public BaseRecyclerAdapter<T> setOnItemClickListener(AdapterView.OnItemClickListener listener){
        mListener = listener;
        return this;
    }

    public BaseRecyclerAdapter<T> refresh(Collection<T> collection){
        mList.clear();
        mList.addAll(collection);
        notifyDataSetChanged();
        notifyListDataSetChanged();
        return this;
    }

    public BaseRecyclerAdapter<T> loadmore(Collection<T> collection){
        mList.addAll(collection);
        notifyDataSetChanged();
        notifyListDataSetChanged();
        return this;
    }

    private final DataSetObservable mDataSetObservable = new DataSetObservable();

    public void registerDataSetObserver(DataSetObserver observer){
        mDataSetObservable.registerObserver(observer);
    }

    public void unregisterDataSetObserver(DataSetObserver observer){
        mDataSetObservable.unregisterObserver(observer);
    }

    public void notifyListDataSetChanged(){
        mDataSetObservable.notifyChanged();
    }

    public void notifyDataSetInvalidated() {
        mDataSetObservable.notifyInvalidated();
    }

    public boolean areAllItemsEnable(){
        return true;
    }

    public boolean isEnabled(int position){
        return true;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        SmartViewHolder holder;
        if (convertView != null){
            holder = (SmartViewHolder)convertView.getTag();
        }else {
            holder = onCreateViewHolder(parent,getItemViewType(position));
            convertView = holder.itemView;
            convertView.setTag(holder);
        }

        onBindViewHolder(holder,position);
        return convertView;
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public boolean isEmpty() {
        return getCount() == 0;
    }

    @Override
    public Object getItem(int i) {
        return mList.get(i);
    }

    @Override
    public int getCount() {
        return mList.size();
    }
}
