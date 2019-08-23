package com.capfront.assignment.view;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.Observable;
import java.util.Observer;

import com.capfront.assignment.R;
import com.capfront.assignment.adapters.MainAdapter;
import com.capfront.assignment.viewmodel.ProductViewModel;


/**
 * Created by neeraj on 10/01/2019.
 */

public class MainActivity extends AppCompatActivity implements Observer {


    private MainAdapter dataAdapter;
    private ProductViewModel productViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        productViewModel = new ProductViewModel();
        init();
    }


    private void init() {
        final SwipeRefreshLayout swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.pullToRefresh);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        dataAdapter = new MainAdapter(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(dataAdapter);
        productViewModel.addObserver(this);
        productViewModel.bindData();
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                productViewModel.bindData();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof ProductViewModel) {
            dataAdapter.setData(((ProductViewModel) o).getRowData());
        }
    }
}
