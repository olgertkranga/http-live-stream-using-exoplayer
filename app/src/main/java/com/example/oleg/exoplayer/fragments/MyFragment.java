package com.example.oleg.exoplayer.fragments;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.oleg.exoplayer.R;
import com.example.oleg.exoplayer.activities.CustomeAdapter;
import com.example.oleg.exoplayer.activities.PlayersModel;

import java.util.ArrayList;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static java.security.AccessController.getContext;

public class MyFragment {

    @BindView(R.id.lv)
    ListView listView;

    public static final String FRAGMENT_ID = "MyFragment";
    private Context context;
    private Unbinder unbinder;
    private ArrayList<PlayersModel> playersModelArrayList;
    private CustomeAdapter myFragmentAdapter;

    public MyFragment(Context context, ArrayList<PlayersModel> playersModelArrayList ) {
        this.context = context;
        this.playersModelArrayList = playersModelArrayList;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
   // @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.lv_item, container, false);
        unbinder = ButterKnife.bind(this, view);
        init();
        return view;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void init() {
        myFragmentAdapter = new CustomeAdapter(Objects.requireNonNull(getContext()), playersModelArrayList);
        listView.setAdapter(myFragmentAdapter);
    }
}