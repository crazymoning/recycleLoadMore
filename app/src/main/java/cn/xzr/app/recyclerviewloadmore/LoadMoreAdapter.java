package cn.xzr.app.recyclerviewloadmore;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import butterknife.BindView;
import butterknife.ButterKnife;

public abstract class LoadMoreAdapter<VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter {

    private static final int TYPE_LOAD = 46548;

    public static final int STATE_NONE = 101;
    public static final int STATE_LOADING = 102;
    public static final int STATE_FAILED = 103;
    private boolean isLoading;

    private int state = STATE_NONE;

    private OnLoadListener onloadListener;

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (recyclerView.getLayoutManager() instanceof LinearLayoutManager){
                    int lastVisibleItem = ((LinearLayoutManager) recyclerView.
                            getLayoutManager()).findLastCompletelyVisibleItemPosition();
                    if (state == STATE_LOADING && !isLoading && getDataItemCount() <= lastVisibleItem +1){
                        if (onloadListener != null){
                            isLoading = true;
                            onloadListener.loadMore();
                        }
                    }
                }
            }
        });
    }

    @Override
    public int getItemViewType(int position) {
        if (position == getItemCount()-1 && state != STATE_NONE){
            return TYPE_LOAD;
        }else {
            return getDataItemViewType(position);
        }
    }

    public int getDataItemViewType(int position){
        return 0;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_LOAD){
            return new LoadHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.view_list_load,parent,false));
        }else {
            return onCreateDataViewHolder(parent,viewType);
        }
    }

    public abstract VH onCreateDataViewHolder(ViewGroup parent,int viewType);

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof LoadHolder){
            if (state == STATE_LOADING){

                ((LoadHolder) holder).loadFailed.setVisibility(View.GONE);
                ((LoadHolder) holder).loading.setVisibility(View.VISIBLE);
            }else if (state == STATE_FAILED){
                ((LoadHolder) holder).loadFailed.setVisibility(View.VISIBLE);
                ((LoadHolder) holder).loading.setVisibility(View.GONE);
            }
            ((LoadHolder) holder).loadFailed.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    changeState(STATE_LOADING);
                    if (onloadListener != null){
                        onloadListener.loadMore();
                    }
                }
            });
        }else {
            onBindDataViewHolder((VH)holder,position);
        }
    }

    public void changeState(int state){
        this.state = state;
        isLoading = false;
        if (state == STATE_NONE){
            notifyDataSetChanged();
        }else {
            notifyItemChanged(getItemCount() - 1);
        }
    }

    public abstract void onBindDataViewHolder(VH holder, int position);

    @Override
    public int getItemCount() {
        return getDataItemCount() + (state != STATE_NONE ? 1 : 0);
    }

    public abstract int getDataItemCount();

    static class LoadHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.loading_layout)
        LinearLayout loading;

        @BindView(R.id.load_failed)
        LinearLayout loadFailed;

        public LoadHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);

        }
    }

    public interface OnLoadListener{
        void loadMore();
    }

    public void setOnLoadListener(OnLoadListener onloadListener) {
        this.onloadListener = onloadListener;
    }
}
