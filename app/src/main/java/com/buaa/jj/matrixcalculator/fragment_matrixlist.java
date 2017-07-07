package com.buaa.jj.matrixcalculator;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ListView;

/**
 * Created by jj on 17-7-5.
 */

public class fragment_matrixlist extends Fragment {
    MainActivity activity;
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity=(MainActivity) activity;
        System.out.println("on attach");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        Myadapter myadapter=new Myadapter(activity.getService().getMList(),getActivity());
        FrameLayout view= (FrameLayout)inflater.inflate(R.layout.fragment_elementmanager,container,false);
        ListView list=(ListView) view.findViewById(R.id.list_view);
        list.setAdapter(myadapter);
        System.out.println("on create view");
        System.out.println(activity.getService().fun());
        return view;
    }
}
