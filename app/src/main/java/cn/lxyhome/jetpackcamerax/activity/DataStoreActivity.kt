package cn.lxyhome.jetpackcamerax.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.datastore.DataStore
import androidx.datastore.preferences.Preferences
import androidx.datastore.preferences.createDataStore
import androidx.datastore.preferences.edit
import androidx.datastore.preferences.preferencesKey
import androidx.lifecycle.lifecycleScope
import cn.lxyhome.jetpackcamerax.R
import cn.lxyhome.jetpackcamerax.util.toast
import kotlinx.android.synthetic.main.activity_data_store.*
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

/**
 * DataStore  处于实验阶段
 */
class DataStoreActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_data_store)
        val stringKey = preferencesKey<String>("string")
        val createDataStore = createDataStores("test")
        lifecycleScope.launch {
            edit(createDataStore, stringKey)
        }
        get_btn.setOnClickListener {
            val map = createDataStore.data.map {
                it[stringKey] ?: ""
            }
            lifecycleScope.launch {
                toast(map.first())
            }
        }
    }


    private suspend fun edit(
        createDataStore: DataStore<Preferences>,
        stringKey: Preferences.Key<String>
    ) {
        createDataStore.edit {
            val s = it[stringKey] ?: ""
            it[stringKey] = s + "KotLin"
        }
    }

    fun createDataStores(name:String):DataStore<Preferences> {
       return this.createDataStore(name)
    }

}