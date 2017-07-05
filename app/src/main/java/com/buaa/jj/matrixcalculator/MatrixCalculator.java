package com.buaa.jj.matrixcalculator;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import java.util.LinkedList;
import java.util.Queue;
public class MatrixCalculator extends Service {
    MyBinder binder=new MyBinder();
    private LinkedList<Queue<Matrix>> MList;
    private Queue<Matrix> deletedMatrix;
    private int deletedId;
    public class MyBinder extends Binder{
        MatrixCalculator getService(){
            return MatrixCalculator.this;
        }
    }
    public MatrixCalculator() {
        MList=new LinkedList<Queue<Matrix>>();
        deletedMatrix=new LinkedList<Matrix>();
    }
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }
    public void createMatrix(int row,int column,int n[][]){
        Matrix tmp=new Matrix(row,column,n);
        Queue<Matrix> tmpQueue=new LinkedList<Matrix>();
        tmpQueue.offer(tmp);
        MList.add(tmpQueue);
    }
    public void removeMatrix(int id){
        if(id<MList.size()){
            Queue<Matrix> tmp= MList.get(id);
            while (!tmp.isEmpty()) {
                deletedMatrix.offer(tmp.poll());
            }
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
}
