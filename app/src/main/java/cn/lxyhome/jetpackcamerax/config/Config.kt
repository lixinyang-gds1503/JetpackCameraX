package cn.lxyhome.jetpackcamerax.config

import android.content.Context

interface Config {
    private fun setConfigTag(configtag: Int):Config{
        return this
    }
    fun getConfigTag():Int
    companion object{
       const val APP_DATABASE_CONFIG_TAG =100
    }
}