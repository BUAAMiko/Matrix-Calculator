package com.buaa.jj.matrixcalculator;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

/**
 * Created by jj on 17-7-10.
 */

public class MatrixFragment extends Fragment {
    private int row;
    private int column;
    private String name;
    MatrixManagerActivity matrixManagerActivity;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        _Matrix tmp=(_Matrix) getArguments().getSerializable("_Matrix");
        row=tmp.getRow();
        column=tmp.getColumn();
        View view=inflater.inflate(R.layout.fragment_matrix,container,false);
        final GridView gridView=view.findViewById(R.id.grid_view);
        gridView.setNumColumns(column);
        MatrixAdapter matrixAdapter=new MatrixAdapter(getActivity(),row*column,tmp);
        gridView.setAdapter(matrixAdapter);
        return view;
    }
}
