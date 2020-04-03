// IUserAidlInterface.aidl
package cn.lxyhome.jetpackcamerax;

// Declare any non-default types here with import statements
import cn.lxyhome.jetpackcamerax.dao.entity.UserInfo;
import cn.lxyhome.jetpackcamerax.IServiceToViewInterface;

interface IUserAidlInterface {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void insertUserInfo(String username,IServiceToViewInterface callback);
    void queryWhereUserName(String username,IServiceToViewInterface callback);
    void deleteUserInfo(String username,IServiceToViewInterface callback);
}
