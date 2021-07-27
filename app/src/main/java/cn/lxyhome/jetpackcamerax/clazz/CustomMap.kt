package cn.lxyhome.jetpackcamerax.clazz

/**
 *   <泛型>
 *This Class: lixinyang on 2021/6/4 created.
 *E-mail:  lixinyang.bj@fang.com
 *
 */
interface CustomMap<out K, V>{
    fun OUT():K
    companion object{
        public const val APP_CONFIG_INT_ONE = 0x0010
        public const val APP_CONFIG_INT_TWO = 0x0020
        public const val APP_CONFIG_INT_THREE = 0x0040
        public const val APP_CONFIG_INT_FOUR = 0x0080
    }
}