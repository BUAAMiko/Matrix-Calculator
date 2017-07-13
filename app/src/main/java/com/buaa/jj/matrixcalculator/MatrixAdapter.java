package com.buaa.jj.matrixcalculator;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * Created by jj on 17-7-10.
 */

public class MatrixAdapter extends BaseAdapter {
    private Context context;
    private int count;
    private double[][] n;
    private int column;
    public MatrixAdapter(Context context,int count,MyMatrix matrix){
        this.context=context;
        this.count=count;
        n=matrix.getNum();
        column=matrix.getColumn();
    }
    @Override
    public int getCount() {
        return count;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view= LayoutInflater.from(context).inflate(R.layout.adapter_grid_item_output,viewGroup,false);
        TextView textView=view.findViewById(R.id.matrix_output);
        String num=String.format("%.2f",n[i/column][i%column]);
        textView.setText(num);
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
