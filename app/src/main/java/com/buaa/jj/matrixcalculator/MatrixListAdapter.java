package com.buaa.jj.matrixcalculator;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.LinkedList;
import java.util.zip.Inflater;

/**
 * Created by jj on 17-7-6.
 */

public class MatrixListAdapter extends BaseAdapter {
    LinkedList<Matrix> mData;
    Context mContext;

    public MatrixListAdapter(){}
    public MatrixListAdapter(LinkedList<Matrix> Data, Context context){
        mData=Data;
        mContext=context;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public Object getItem(int i) {
        return mData.get(i);
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder=null;
        if(view==null){
            view= LayoutInflater.from(mContext).inflate(R.layout.list_item,viewGroup,false);
            viewHolder=new ViewHolder();
            viewHolder.text=(TextView) view.findViewById(R.id.textView2);
            viewHolder.subtext=(TextView) view.findViewById(R.id.textView3);
            view.setTag(viewHolder);
        }
        else {
            viewHolder=(ViewHolder) view.getTag();
        }
        viewHolder.text.setText(""+mData.get(i).getName()+"");
        viewHolder.subtext.setText(mData.get(i).getRow()+"*"+mData.get(i).getColumn());
        return view;
    }

    private class ViewHolder{
        TextView text;
        TextView subtext;
    }
}
