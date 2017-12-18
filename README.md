# recycleLoadMore
介绍
===
列表是android中最重要的数据呈现方式。

绝大多数应用在从服务器上获取长列表类型的数据的时候都需要分页获取，一般来说，分页有三种方式，一种是在列表加载到最后一个子项的时候自动加载（用户体验最好的方式，网速足够的情况下完全感觉不到加载），一种是列表在底部的时候还需要上拉（交互效果最好的方式），还有一种就是列出列表页数，点击跳转页面。

RecycleLoadMore就是实现的滑到底部自动加载的方式。

使用
===
就像demo中看到的那样，继承LoadMoreAdapter实现一个Recyclerview.adapter,平时常实现的onCreateViewHolder，onBindViewHolder，getItemCount三个方法变成了onCreateDataViewHolder，onBindDataViewHolder，getDataItemCount

在实现的adapter被设置到Recyclerview中之后有个changeState(int)方法可改变加载更多的状态，可用参数为STATE_NONE，STATE_LOADING，STATE_FAILED，当列表需要加载更多时需要改变状态为STATE_LOADING，当加载更多加载失败时改变状态为STATE_FAILED，当不再需要加载数据的时候改变状态为STATE_NONE

需要设置LoadMoreAdapter.OnLoadListener来加载数据

adapter.setOnLoadListener(new LoadMoreAdapter.OnLoadListener() {
            @Override
            public void loadMore() {
               //load data
            }
        });
