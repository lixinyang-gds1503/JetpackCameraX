package cn.lxyhome.jetpackcamerax.config

interface Config {
    private fun setConfigTag(configtag: Int):Config{
        return this
    }
    fun getConfigTag():Int
    companion object{
        const val APP_DATABASE_CONFIG_TAG = 100
        const val APP_BUGLY_CONFIG_TAG = 101
        const val APP_WORK_MANAGER_CONFIG_TAG = 102

        const val buglyid = "c438bf8ece"
        const val APP_PROCESS_NAME_1 = "cn.lxyhome.jetpackcamerax:back"
        const val APP_PROCESS_NAME_2 = "cn.lxyhome.jetpackcamerax"
    }
}