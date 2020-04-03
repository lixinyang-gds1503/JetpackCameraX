// IServiceToViewInterface.aidl
package cn.lxyhome.jetpackcamerax;

// Declare any non-default types here with import statements
import cn.lxyhome.jetpackcamerax.dao.entity.UserInfo;

interface IServiceToViewInterface {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void insertState(boolean state, in List<UserInfo> userinfos);
    void queryState(boolean state, in List<UserInfo> userinfos);
    void deleteState(boolean state, in List<UserInfo> userinfos);
}
