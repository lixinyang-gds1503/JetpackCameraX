package cn.lxyhome.jetpackcamerax.view

/**
 *   <密封类测试,用处真是不大啊
 * >
 *This Class: lixinyang on 2021/7/27 created.
 *E-mail:  lixinyang.bj@fang.com
 *
 */
sealed class BaseSealed


class MyFirstSealedClass : BaseSealed() {
    override fun equals(other: Any?): Boolean {
        return this === other
    }

    override fun hashCode(): Int {
        return System.identityHashCode(this)
    }




}