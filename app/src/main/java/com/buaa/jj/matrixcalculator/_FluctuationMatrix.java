package com.buaa.jj.matrixcalculator;


/**
 * Created by jj on 17-7-6.
 */

public class _FluctuationMatrix {
    private int row;
    private int column;
    private int[][] num;
    private boolean state;
    private boolean fun(int[][] n){
        return n.length==row&&n[0].length==column;
    }
    public void setElement(int r,int c,int n){
        num[r][c]=n;
    }
    public _FluctuationMatrix(){
        row=1;
        column=1;
        num=new int[row][column];
        state=true;
    }
    public _FluctuationMatrix(int r, int c, int[][] n, int id, String name){
        if(fun(n)){
            row=r;
            column=c;
            num=new int[row][column];
            state=true;
        }
        else{
            row=1;
            column=1;
            num=new int[row][column];
            state=false;
        }
        if(state){
            for(int i=0;i<row;i++){
                for(int j=0;j<column;j++){
                    setElement(i,j,n[i][j]);
                }
            }
        }
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
}
