package com.buaa.jj.matrixcalculator;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/**
 * Created by jj on 17-7-5.
 */

public class Matrix {
    private String name;
    private int row;
    private int column;
    private int[][] num;
    private Queue<FluctuationMatrix> operation;
    private boolean state;
    private boolean fun(int[][] n){
        return n.length==row&&n[0].length==column;
    }
    public void setElement(int r,int c,int n){
        num[r][c]=n;
    }
    public void setName(String name){
        this.name=name;
    }
    public Matrix(){
        row=1;
        column=1;
        num=new int[row][column];
        state=true;
        name=new String();
        name="矩阵";
        operation=new LinkedList<FluctuationMatrix>();
    }
    public Matrix(int r,int c,int[][] n,int id,String name){
        if(fun(n)){
            row=r;
            column=c;
            num=new int[row][column];
            state=true;
            this.name=id+"-"+name;
            operation=new LinkedList<FluctuationMatrix>();
        }
        else{
            row=1;
            column=1;
            num=new int[row][column];
            state=false;
            this.name=id+"-矩阵";
            operation=new LinkedList<FluctuationMatrix>();
        }
        if(state){
            for(int i=0;i<row;i++){
                for(int j=0;j<column;j++){
                    setElement(i,j,n[i][j]);
                }
            }
        }
    }
    public Matrix(int r,int c,int[][] n,String name){
        row=r;
        column=c;
        num=new int[row][column];
        state=true;
        this.name=name;
        operation=new LinkedList<FluctuationMatrix>();
        for(int i=0;i<row;i++){
            for(int j=0;j<column;j++){
                setElement(i,j,n[i][j]);
            }
        }
    }
    public void operate(FluctuationMatrix fluctuationMatrix){
        operation.offer(fluctuationMatrix);
    }
    public Matrix matrixMultiply(Matrix baseMatrix,FluctuationMatrix fluctuationMatrix){
        int row=fluctuationMatrix.getRow();
        int column=baseMatrix.getColumn();
        int[][] m=new int[row][column];
        String name=baseMatrix.getName();
        Matrix ans=new Matrix(row,column,m,name);
        for(int i=0;i<row;i++){
            for(int j=0;j<column;j++){
                for(int k=0;k<baseMatrix.column;k++)
                    ans.setElement(i,j,baseMatrix.getElement(i,k)*fluctuationMatrix.getElement(k,j));
            }
        }
        return ans;
    }
    public void mergeMatrix(){
        Matrix tmp=this;
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
    public int[][] getNum(){
        return num;
    }
    public int getElement(int r,int c){
        return num[r][c];
    }
    public String getName(){
        return name;
    }
    public void copyMatrix(Matrix matrix){
        this.name=matrix.getName();
        this.num=matrix.getNum();
        this.row=matrix.getRow();
        this.column=matrix.getColumn();
    }
}