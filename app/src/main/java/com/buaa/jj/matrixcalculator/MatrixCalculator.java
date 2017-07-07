package com.buaa.jj.matrixcalculator;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import java.util.LinkedList;
import java.util.Queue;
public class MatrixCalculator extends Service {
    MyBinder binder=new MyBinder();
    private LinkedList<Matrix> MList;
    private Matrix deletedMatrix;
    private int deletedId;
    public class MyBinder extends Binder{
        MatrixCalculator getService(){
            return MatrixCalculator.this;
        }
    }
    public MatrixCalculator() {
        MList=new LinkedList<Matrix>();
        deletedMatrix=new Matrix();
        deletedId=-1;
    }
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    public void createMatrix(int row,int column,int n[][],String name){
        Matrix tmp;
        if(name=="")
            tmp=new Matrix(row,column,n,MList.size(),"矩阵");
        else
            tmp=new Matrix(row,column,n,MList.size(),name);
        MList.add(tmp);
    }
    public void removeMatrix(int id){
        if(id<MList.size()){
            Matrix tmp= MList.get(id);
            deletedMatrix=new Matrix();
            deletedMatrix.copyMatrix(tmp);
            MList.remove(id);
            deletedId=id;
        }
    }
    public void recoveryMatrix(){
        if(deletedId!=-1){
            MList.add(deletedId,deletedMatrix);
            deletedId=-1;
        }
    }
    public int fun(){
        return MList.size();
    }
    public LinkedList<Matrix> getMList(){
        return MList;
    }
}
