package cn.lxyhome.jetpackcamerax.config

interface Config {
    private fun setConfigTag(configtag: Int):Config{
        return this
    }
    fun getConfigTag():Int
    companion object{
       const val APP_DATABASE_CONFIG_TAG =100
       const val APP_BUGLY_CONFIG_TAG =101

        const val buglyid = "c438bf8ece"
    }
}