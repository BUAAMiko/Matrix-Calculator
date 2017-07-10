package com.buaa.jj.matrixcalculator;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

/**
 * Created by jj on 17-7-7.
 */

public class AddMatrixAdapter extends BaseAdapter {
    private Context context;
    private int count;
    public AddMatrixAdapter(Context mcontext,int count){
        context=mcontext;
        this.count=count;
    }
    @Override
    public int getCount() {
        return count;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view= LayoutInflater.from(context).inflate(R.layout.grid_item_input,viewGroup,false);
        return view;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }
}
