package cn.lxyhome.jetpackcamerax.net

/**
 *   <这个类的说明>
 *This Class: lixinyang on 2021/3/12 created.
 *E-mail:  lixinyang.bj@fang.com
 *
 */


fun getJsonFromNet(map:Map<String,String?>?,netCallback:(Any?)->Unit) {
    AppHttpApi.executeJsonFromNet(map, netCallback)
}