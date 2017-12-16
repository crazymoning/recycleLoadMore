package cn.xzr.library.recyclerviewloadmore;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by xzr on 2017/12/15.
 */

public class TestAdapter extends LoadMoreAdapter<TestAdapter.TestViewHolder> {

    private int count;

    public int getCount() {
        return count;
    }

    public void addCount(int count) {
        if (count <= 0){
            return;
        }
        this.count += count;
        notifyDataSetChanged();
    }

    @Override
    public TestViewHolder onCreateDataViewHolder(ViewGroup parent, int viewType) {
        return new TestViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_test,parent,false));
    }

    @Override
    public void onBindDataViewHolder(TestViewHolder holder, int position) {
        holder.position.setText(String.valueOf(position));
    }

    @Override
    public int getDataItemCount() {
        return count < 0 ? 0 : count;
    }

    static class TestViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.position)
        TextView position;

        public TestViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
