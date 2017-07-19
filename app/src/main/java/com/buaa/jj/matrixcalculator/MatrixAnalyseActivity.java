package com.buaa.jj.matrixcalculator;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import Jama.Matrix;

/**
 * Created by jj on 17-7-11.
 */

public class MatrixAnalyseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.matrix_analyse_context);

        MyMatrix myMatrix=(MyMatrix)getIntent().getSerializableExtra("matrix");
        Matrix matrix=new Matrix(myMatrix.getNum());
        double[][] tmp;
        MyMatrix inverseMatrix;
        MyMatrix cofactorMatrix;
        MyGridView gridView=(MyGridView) findViewById(R.id.matrix_grid);
        gridView.setNumColumns(myMatrix.getColumn());
        gridView.setAdapter(new MatrixAdapter(this,myMatrix.getRow()*myMatrix.getColumn(),myMatrix));
        if(matrix.rank()==myMatrix.getColumn()&&matrix.rank()==myMatrix.getRow()){
            tmp=matrix.inverse().getArray();
            inverseMatrix=new MyMatrix(tmp.length,tmp[0].length,tmp,"");
            tmp=matrix.inverse().times(matrix.det()).getArray();
            cofactorMatrix=new MyMatrix(tmp.length,tmp[0].length,tmp,"");

            gridView=(MyGridView) findViewById(R.id.inverse_matrix);
            gridView.setNumColumns(inverseMatrix.getColumn());
            gridView.setAdapter(new MatrixAdapter(this,inverseMatrix.getRow()*inverseMatrix.getColumn(),inverseMatrix));

            gridView=(MyGridView) findViewById(R.id.cafactor_matrix_grid);
            gridView.setNumColumns(cofactorMatrix.getColumn());
            gridView.setAdapter(new MatrixAdapter(this,cofactorMatrix.getRow()*cofactorMatrix.getColumn(),cofactorMatrix));
        }

        TextView textView=(TextView) findViewById(R.id.rank);
        textView.setText("rank = "+matrix.rank()+"");

        textView=(TextView) findViewById(R.id.det);
        String det=String.format("%.2f",matrix.det());
        textView.setText("det = "+det+"");

        textView=(TextView) findViewById(R.id.dim);
        textView.setText("rowDim = "+matrix.getRowDimension()+"");

        textView=(TextView) findViewById(R.id.dim2);
        textView.setText("columnDim = "+matrix.getColumnDimension()+"");
    }
}
