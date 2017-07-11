package com.buaa.jj.matrixcalculator;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by jj on 17-7-5.
 */

public class MyMatrix implements Serializable {
    private String name;
    private int row;
    private int column;
    private double[][] num;
    private Queue<MyFluctuationMatrix> operation;
    private boolean state;

    private boolean fun(int[][] n){
        return n.length==row&&n[0].length==column;
    }

    public void setElement(int r,int c,double n){
        num[r][c]=n;
    }

    public void setName(String name){
        this.name=name;
    }

    public MyMatrix(){
        row=1;
        column=1;
        num=new double[row][column];
        state=true;
        name=new String();
        name="矩阵";
        operation=new LinkedList<MyFluctuationMatrix>();
    }

    public MyMatrix(int r, int c, int[][] n, int id, String name){
        row=r;
        column=c;
        if(fun(n)){
            num=new double[row][column];
            state=true;
            this.name=id+"-"+name;
            operation=new LinkedList<MyFluctuationMatrix>();
        }
        else{
            row=1;
            column=1;
            num=new double[row][column];
            state=false;
            this.name=id+"-矩阵";
            operation=new LinkedList<MyFluctuationMatrix>();
        }
        if(state){
            for(int i=0;i<row;i++){
                for(int j=0;j<column;j++){
                    setElement(i,j,n[i][j]);
                }
            }
        }
    }

    public MyMatrix(int r, int c, int[][] n, String name){
        row=r;
        column=c;
        num=new double[row][column];
        state=true;
        this.name=name;
        operation=new LinkedList<MyFluctuationMatrix>();
        for(int i=0;i<row;i++){
            for(int j=0;j<column;j++){
                setElement(i,j,n[i][j]);
            }
        }
    }

    public void operate(MyFluctuationMatrix fluctuationMatrix){
        operation.offer(fluctuationMatrix);
    }

    public MyMatrix matrixMultiply(MyMatrix baseMatrix, MyFluctuationMatrix fluctuationMatrix){
        int row=fluctuationMatrix.getRow();
        int column=baseMatrix.getColumn();
        int[][] m=new int[row][column];
        String name=baseMatrix.getName();
        MyMatrix ans=new MyMatrix(row,column,m,name);
        for(int i=0;i<row;i++){
            for(int j=0;j<column;j++){
                for(int k=0;k<baseMatrix.column;k++)
                    ans.setElement(i,j,baseMatrix.getElement(i,k)*fluctuationMatrix.getElement(k,j));
            }
        }
        return ans;
    }

    public void mergeMatrix(){
        MyMatrix tmp=this;
        while(!operation.isEmpty()){
            tmp=matrixMultiply(tmp,operation.poll());
        }
        copyMatrix(tmp);
    }

    public int getRow(){
        return row;
    }

    public int getColumn(){
        return column;
    }

    public double[][] getNum(){
        return num;
    }

    public double getElement(int r,int c){
        return num[r][c];
    }

    public String getName(){
        return name;
    }

    public void copyMatrix(MyMatrix matrix){
        this.name=matrix.getName();
        this.num=matrix.getNum();
        this.row=matrix.getRow();
        this.column=matrix.getColumn();
    }
}