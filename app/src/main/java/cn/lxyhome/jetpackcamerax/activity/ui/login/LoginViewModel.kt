package cn.lxyhome.jetpackcamerax.activity.ui.login

import android.content.Context
import android.content.Intent
import android.os.RemoteException
import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import cn.lxyhome.jetpackcamerax.IServiceToViewInterface
import cn.lxyhome.jetpackcamerax.JetpackApplication
import cn.lxyhome.jetpackcamerax.R
import cn.lxyhome.jetpackcamerax.service.BackService
import cn.lxyhome.jetpackcamerax.service.BackServiceConnection
import cn.lxyhome.jetpackcamerax.util.toast

class LoginViewModel() : ViewModel() {

    private val _loginForm = MutableLiveData<LoginFormState>()
    val loginFormState: LiveData<LoginFormState> = _loginForm

    private val _loginResult = MutableLiveData<LoginResult>()
    val loginResult: LiveData<LoginResult> = _loginResult
    private val mBackServiceConnection = BackServiceConnection()

    fun setLoginValue(lr:LoginResult) {
        _loginResult.value  = lr
    }

    fun bindBackService(context: Context):Boolean {
        //todolxy     启动后台服务  存储 userinfo
        if (context is LoginActivity) {
            val activity = context as LoginActivity
            return  activity.bindService(Intent(context,BackService::class.java),mBackServiceConnection,Context.BIND_AUTO_CREATE)
        }
        return false
    }

    fun unbindBackService(context: Context) {
        context.unbindService(mBackServiceConnection)
    }
    //密码存在share文件里 暗文
    fun loginOrSignIn(username: String, callback: IServiceToViewInterface) {

        val userinfos = JetpackApplication.getUserDao()?.queryWhereForUser(username)
        if (userinfos!=null && userinfos.isNotEmpty()) {
            //todo 跳转用户详情页
            toast("跳转用户详情页")
            setLoginValue(LoginResult(success = userinfos[0]))
        }else {
            //todo 注册后 跳转用户详情页
            try {
                //toast("注册后 跳转用户详情页")
                mBackServiceConnection.mbb?.insertUserInfo(username,callback)
            } catch (e: RemoteException) {
            }
        }

    }

    fun loginDataChanged(username: String, password: String) {
        if (!isUserNameValid(username)) {
            _loginForm.value = LoginFormState(usernameError = R.string.invalid_username)
        } else if (!isPasswordValid(password)) {
            _loginForm.value = LoginFormState(passwordError = R.string.invalid_password)
        } else {
            _loginForm.value = LoginFormState(isDataValid = true)
        }
    }

    // A placeholder username validation check
    private fun isUserNameValid(username: String): Boolean {
        return if (username.contains('@')) {
            Patterns.EMAIL_ADDRESS.matcher(username).matches()
        } else {
            username.isNotBlank()
        }
    }

    // A placeholder password validation check
    private fun isPasswordValid(password: String): Boolean {
        return password.length > 5
    }
}
