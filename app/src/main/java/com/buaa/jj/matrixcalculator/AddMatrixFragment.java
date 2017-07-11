package com.buaa.jj.matrixcalculator;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;

/**
 * Created by jj on 17-7-7.
 */

public class AddMatrixFragment extends Fragment {
    private int row;
    private int column;
    private String name;
    Button btncommit;
    EditText editText;
    MatrixManagerActivity matrixManagerActivity;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        matrixManagerActivity =(MatrixManagerActivity) context;
    }

    public interface FragmentInteraction{
        void process(int r,int c,int[][] n,String name);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        int[] n=getArguments().getIntArray("Row&Column");
        row=n[0];
        column=n[1];
        View view=inflater.inflate(R.layout.fragment_add_matrix,container,false);
        btncommit= view.findViewById(R.id.button);
        editText= view.findViewById(R.id.editText4);
        final GridView gridView=view.findViewById(R.id.grid_view);
        gridView.setNumColumns(column);
        MatrixInputAdapter matrixInputAdapter =new MatrixInputAdapter(getActivity(),row*column);
        gridView.setAdapter(matrixInputAdapter);
        btncommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int[][] m=new int[row][column];
                for(int i=0;i<row;i++){
                    for(int j=0;j<column;j++){
                        m[i][j]=Integer.parseInt(((EditText)gridView.getChildAt(i*column+j).findViewById(R.id.editText3)).getText().toString());
                    }
                }
                name=editText.getText().toString();
                matrixManagerActivity.process(row,column,m,name);
            }
        });
        return view;
    }
}
