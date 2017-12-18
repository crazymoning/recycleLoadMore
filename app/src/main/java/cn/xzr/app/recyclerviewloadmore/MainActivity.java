package cn.xzr.app.recyclerviewloadmore;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.recycler)
    RecyclerView recyclerView;

    private TestAdapter adapter;
    private Handler handler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new TestAdapter();
        recyclerView.setAdapter(adapter);
        handler = new Handler();

        adapter.addCount(20);
        adapter.changeState(LoadMoreAdapter.STATE_LOADING);

        adapter.setOnLoadListener(new LoadMoreAdapter.OnLoadListener() {
            @Override
            public void loadMore() {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (adapter.getCount() >= 50){
                            adapter.changeState(LoadMoreAdapter.STATE_NONE);
                        }else {
                            if (Math.random()>0.5){
                                adapter.addCount(10);
                                adapter.changeState(LoadMoreAdapter.STATE_LOADING);
                            }else {
                                adapter.changeState(LoadMoreAdapter.STATE_FAILED);
                            }

                        }

                    }
                },2000);
            }
        });

    }
}
