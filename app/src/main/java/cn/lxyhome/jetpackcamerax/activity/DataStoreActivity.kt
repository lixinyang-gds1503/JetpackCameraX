package cn.lxyhome.jetpackcamerax.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.datastore.core.*
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.dataStore
import androidx.datastore.dataStoreFile
import androidx.datastore.preferences.*
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.protobuf.InvalidProtocolBufferException
import androidx.lifecycle.lifecycleScope
import cn.lxyhome.jetpackcamerax.R
import cn.lxyhome.jetpackcamerax.UserPreferences
import cn.lxyhome.jetpackcamerax.util.toast
import kotlinx.android.synthetic.main.activity_data_store.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.io.InputStream
import java.io.OutputStream

/**
 * DataStore  处于实验阶段
 * 数据存储
 */
class DataStoreActivity : AppCompatActivity() {
    val userPrefs:DataStore<UserPreferences> by dataStore("user_prefs.pb",UserPrefsSerializer)//proto DataStore
    val anyPrefs:DataStore<Preferences> by preferencesDataStore("anyPrefs")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_data_store)
       // val stringKey = preferencesKey<String>("string")
       // val createDataStore = createDataStores("test")// Preferences DataStore
        val nameKey = stringPreferencesKey("name")
        val agentIdKey = intPreferencesKey("agentId")
        lifecycleScope.launch(Dispatchers.IO) {
            anyPrefs.data.map {
                it[nameKey]?:"Not empy"
                it[agentIdKey]?:0
            }
            anyPrefs.edit {
                it[nameKey] = "Kotlin Preferences DataStore Type Created."
                it[agentIdKey]= 1
            }
            userPrefs.updateData {
                it.toBuilder().setId(1).setName("User")
                    .setAge(99).setPhone("123345435634").build()
            }
        }
        get_btn.setOnClickListener {

            val map1: Flow<String> = userPrefs.data.map {
               "${it.id}  ${it.name}  ${it.age}  ${it.phone}"
            }
            val map = anyPrefs.data.map {
                "$it"
            }
            lifecycleScope.launch {
                toast(map.first() +"\n" +map1.first())
            }
        }
    }

}



object UserPrefsSerializer: Serializer<UserPreferences> {
    override suspend fun readFrom(input: InputStream): UserPreferences {
        try {
            return UserPreferences.parseFrom(input)
        } catch (exception: InvalidProtocolBufferException) {
            throw CorruptionException("Cannot read proto.", exception)
        }
    }

    override suspend fun writeTo(t: UserPreferences, output: OutputStream) {
       t.writeTo(output)
    }

    override val defaultValue: UserPreferences
        get() = UserPreferences.getDefaultInstance()

}