package com.qq.administrator.myrefreshdemo.diffutils;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.qq.administrator.myrefreshdemo.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by 崔琦 on 2017/7/25 0025.
 * Describe : .....
 */
public class DiffAdapter extends RecyclerView.Adapter<DiffAdapter.DiffViewHolder>{

    private LayoutInflater mInflater;
    private Context mContext;
    private List<TestBean> mData;
    private final static String TAG = "Diff";

    public DiffAdapter(Context mContext, List<TestBean> mData) {
        this.mContext = mContext;
        this.mData = mData;
        mInflater = LayoutInflater.from(mContext);
    }

    public void setData(List<TestBean> mData){
        this.mData = mData;
    }

    @Override
    public DiffViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_diff, parent, false);
        return new DiffViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DiffViewHolder holder, int position) {
        TestBean bean = mData.get(position);
        holder.itemName.setText(bean.getName());
        holder.itemDesc.setText(bean.getDesc());
        holder.itemLove.setText(bean.getLove());
        holder.itemAttention.setText(bean.getAttention());
        holder.itemPic1.setImageResource(bean.getPic1());
        holder.itemPic2.setImageResource(bean.getPic2());
        holder.itemPic3.setImageResource(bean.getPic3());
    }

    @Override
    public void onBindViewHolder(DiffViewHolder holder, int position, List<Object> payloads) {
        if (payloads.isEmpty()){
            onBindViewHolder(holder,position);
        }else {
            Bundle payload = (Bundle)payloads.get(0);//取出我们在getChangePayload（）方法返回的bundle
            TestBean bean = mData.get(position);//取出新数据源
            for (String key : payload.keySet()){
                switch (key){
                    case "KEY_DESC":
                        //这里可以用payload里的数据，不过data也是新的 也可以用
                        holder.itemDesc.setText(bean.getDesc());
                        break;
                    case "KEY_LOVE":
                        holder.itemLove.setText(bean.getLove());
                        break;
                    case "KEY_PIC1":
                        holder.itemPic1.setImageResource(payload.getInt(key));
                        break;
                    case "KEY_PIC2":
                        holder.itemPic2.setImageResource(bean.getPic1());
                        break;
                    case "KEY_PIC3":
                        holder.itemPic3.setImageResource(bean.getPic3());
                        break;
                }
            }
        }

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class DiffViewHolder extends RecyclerView.ViewHolder{

        public TextView itemName,itemLove,itemDesc;
        public CircleImageView itemPic1,itemPic2,itemPic3;
        public TextView itemAttention;

        public DiffViewHolder(View itemView) {
            super(itemView);
            itemName = (TextView)itemView.findViewById(R.id.item_name);
            itemLove = (TextView)itemView.findViewById(R.id.item_love);
            itemDesc = (TextView)itemView.findViewById(R.id.item_desc);
            itemPic1 = (CircleImageView)itemView.findViewById(R.id.avatar);
            itemPic2 = (CircleImageView)itemView.findViewById(R.id.avatar2);
            itemPic3 = (CircleImageView)itemView.findViewById(R.id.avatar3);
            itemAttention = (TextView)itemView.findViewById(R.id.attention);
        }
    }
}
