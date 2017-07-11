package com.buaa.jj.matrixcalculator;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import java.util.LinkedList;

import Jama.Matrix;

public class ApplicationMainService extends Service {
    MyBinder binder=new MyBinder();
    private LinkedList<MyMatrix> MList;
    private MyMatrix deletedMatrix;
    private int deletedId;

    public class MyBinder extends Binder{
        ApplicationMainService getService(){
            return ApplicationMainService.this;
        }
    }

    public ApplicationMainService() {
        MList=new LinkedList<MyMatrix>();
        deletedMatrix=new MyMatrix();
        deletedId=-1;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    public void createMatrix(int row,int column,int n[][],String name){
        MyMatrix tmp;
        if(name=="")
            tmp=new MyMatrix(row,column,n,MList.size(),"MyMatrix");
        else
            tmp=new MyMatrix(row,column,n,MList.size(),name);
        MList.add(tmp);
    }

    public void removeMatrix(int id){
        if(id<MList.size()){
            MyMatrix tmp= MList.get(id);
            deletedMatrix=new MyMatrix();
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

    public LinkedList<MyMatrix> getMList(){
        return MList;
    }

    public void matrixAnalysed(int id){
        Matrix matrix=new Matrix(MList.get(id).getNum());
        
    }
}
