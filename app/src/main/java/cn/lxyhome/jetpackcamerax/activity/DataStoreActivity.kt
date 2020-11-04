package cn.lxyhome.jetpackcamerax.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.datastore.CorruptionException
import androidx.datastore.DataStore
import androidx.datastore.Serializer
import androidx.datastore.createDataStore
import androidx.datastore.preferences.Preferences
import androidx.datastore.preferences.createDataStore
import androidx.datastore.preferences.edit
import androidx.datastore.preferences.preferencesKey
import androidx.datastore.preferences.protobuf.InvalidProtocolBufferException
import androidx.lifecycle.lifecycleScope
import cn.lxyhome.jetpackcamerax.R
import cn.lxyhome.jetpackcamerax.UserPreferences
import cn.lxyhome.jetpackcamerax.util.toast
import kotlinx.android.synthetic.main.activity_data_store.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.io.InputStream
import java.io.OutputStream

/**
 * DataStore  处于实验阶段
 */
class DataStoreActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_data_store)
        val stringKey = preferencesKey<String>("string")
        val createDataStore = createDataStores("test")// Preferences DataStore
        val userPrefs:DataStore<UserPreferences> = createDataStore("user_prefs.pb",UserPrefsSerializer)//proto DataStore
        lifecycleScope.launch {
            edit(createDataStore, stringKey)
            userPrefs.updateData {
                it.toBuilder().setId(1).setName("User")
                    .setAge(99).setPhone("123345435634").build()
            }
        }
        get_btn.setOnClickListener {
            val map = createDataStore.data.map {
                it[stringKey] ?: ""
            }
            val map1: Flow<String> = userPrefs.data.map {
               "${it.id}  ${it.name}  ${it.age}  ${it.phone}"
            }
            lifecycleScope.launch {
                toast(map.first() +"\n" +map1.first())
            }
        }
    }


    private suspend fun edit(
        createDataStore: DataStore<Preferences>,
        stringKey: Preferences.Key<String>
    ) {
        createDataStore.edit {
            //val s = it[stringKey] ?: ""
            it[stringKey] =  "KotLin"
        }
    }

    fun createDataStores(name:String):DataStore<Preferences> {
       return this.createDataStore(name)
    }

}


object UserPrefsSerializer:Serializer<UserPreferences>{
    override fun readFrom(input: InputStream): UserPreferences {
        try {
            return UserPreferences.parseFrom(input)
        } catch (exception: InvalidProtocolBufferException) {
            throw CorruptionException("Cannot read proto.", exception)
        }
    }

    override fun writeTo(t: UserPreferences, output: OutputStream) {
       t.writeTo(output)
    }

}