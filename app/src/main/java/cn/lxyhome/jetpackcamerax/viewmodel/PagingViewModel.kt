package cn.lxyhome.jetpackcamerax.viewmodel


import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import androidx.paging.PositionalDataSource
import cn.lxyhome.jetpackcamerax.JetpackApplication
import cn.lxyhome.jetpackcamerax.dao.entity.UserInfo

/**
 *   <这个类的说明>
 *This Class: lixinyang on 2020/7/20 created.
 *E-mail:  lixinyang.bj@fang.com
 *
 */
class PagingViewModel:ViewModel() {
    private val configBuilder = PagedList.Config.Builder()
    private val config = configBuilder.setPageSize(10)
        .setEnablePlaceholders(false)
        .setInitialLoadSizeHint(10)
        .setPrefetchDistance(2)
        .build()
    //val liveData = LivePagedListBuilder(PagingDataSourceFactory(), config).build()
    val liveData = LivePagedListBuilder(JetpackApplication.getUserDao()?.quereUserById()!!, config).build()
}


class PagingDataSource : PositionalDataSource<UserInfo>() {
    override fun loadRange(params: LoadRangeParams, callback: LoadRangeCallback<UserInfo>) {

        val queryCardAllList = JetpackApplication.getUserDao()?.queryUserAllList()
        Log.e("PagingDataSource","${params.loadSize}|||${params.startPosition}")
        queryCardAllList?.let {
            callback.onResult(it)
        }
    }

    override fun loadInitial(params: LoadInitialParams, callback: LoadInitialCallback<UserInfo>) {
        val queryUserAllList = JetpackApplication.getUserDao()?.queryUserAllList()
        if (queryUserAllList!=null) {
            callback.onResult(queryUserAllList,0,queryUserAllList.size)
        }else{
            callback.onResult(ArrayList<UserInfo>(),0,5)
        }
    }

}

class PagingDataSourceFactory : DataSource.Factory<Int, UserInfo>() {
    override fun create(): DataSource<Int, UserInfo> {
        return PagingDataSource()
    }

}