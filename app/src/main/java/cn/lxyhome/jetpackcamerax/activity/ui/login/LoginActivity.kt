package cn.lxyhome.jetpackcamerax.activity.ui.login

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import cn.lxyhome.jetpackcamerax.IServiceToViewInterface
import cn.lxyhome.jetpackcamerax.R
import cn.lxyhome.jetpackcamerax.activity.UserInfoActivity
import cn.lxyhome.jetpackcamerax.base.BaseActivity
import cn.lxyhome.jetpackcamerax.dao.entity.UserInfo
import cn.lxyhome.jetpackcamerax.util.startActivity
import cn.lxyhome.jetpackcamerax.util.toast


class LoginActivity : BaseActivity() {

    private lateinit var loginViewModel: LoginViewModel
    private lateinit var username:EditText
    private lateinit var password:EditText
    private lateinit var login:Button
    private lateinit var loading:ProgressBar
    private  val AIDLCallback: InnerAIDLCallback = InnerAIDLCallback()
    private val mHandler= Handler(Handler.Callback {
        if (it.what==1) {
            loading.visibility = View.GONE
            toast("insert success")
            if (it.obj!= null) {
                val infos:List<UserInfo> = it.obj as List<UserInfo>
                startActivity<UserInfoActivity> {
                    Intent().putExtra("username",infos[0])
                }
                finish()
            }
        }
        false
    })

    override fun onDestroy() {
        super.onDestroy()
        loginViewModel.unbindBackService(this)
    }

    override fun onStart() {
        super.onStart()
        if (!loginViewModel.bindBackService(this)) {
            toast("抱歉，后台服务没有启动")
            finish()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setview(R.layout.activity_login)

        username = findViewById<EditText>(R.id.username)
        password = findViewById<EditText>(R.id.password)
        login = findViewById<Button>(R.id.login)
        loading = findViewById<ProgressBar>(R.id.loading)

        loginViewModel = ViewModelProvider(this, LoginViewModelFactory())
            .get(LoginViewModel::class.java)


        loginViewModel.loginFormState.observe(this@LoginActivity, Observer {
            val loginState = it ?: return@Observer

            // disable login button unless both username / password is valid
            login.isEnabled = loginState.isDataValid

            if (loginState.usernameError != null) {
                username.error = getString(loginState.usernameError)
            }
            if (loginState.passwordError != null) {
                password.error = getString(loginState.passwordError)
            }
        })

        loginViewModel.loginResult.observe(this@LoginActivity, Observer {
            val loginResult = it ?: return@Observer

            loading.visibility = View.GONE
            if (loginResult.error != null) {
                showLoginFailed(loginResult.error)
            }

            if (loginResult.success != null) {
                setResult(Activity.RESULT_OK)
               startActivity<UserInfoActivity> {intient->
                   Intent().putExtra("username",it.success)
               }
                finish()
            }

        })

        username.afterTextChanged {
            loginViewModel.loginDataChanged(
                username.text.toString(),
                password.text.toString()
            )
        }

        password.apply {
            afterTextChanged {
                loginViewModel.loginDataChanged(
                    username.text.toString(),
                    password.text.toString()
                )
            }

            setOnEditorActionListener { _, actionId, _ ->
                when (actionId) {
                    EditorInfo.IME_ACTION_DONE ->
                    {  loading.visibility = View.VISIBLE
                        loginViewModel.loginOrSignIn(username.text.toString(),AIDLCallback)}
                }
                false
            }

            login.setOnClickListener {
                loading.visibility = View.VISIBLE
                loginViewModel.loginOrSignIn(username.text.toString(),AIDLCallback)
            }
        }
    }

    private fun showLoginFailed(@StringRes errorString: Int) {
        Toast.makeText(applicationContext, errorString, Toast.LENGTH_SHORT).show()
    }

    /**
     * aidl backservice回调
     */
    private inner class InnerAIDLCallback:IServiceToViewInterface.Stub(){
        override fun insertState(state: Boolean, userinfos: MutableList<UserInfo>?) {
            if (state) {
                val obtainMessage = mHandler.obtainMessage()
                obtainMessage.run {
                    obj = userinfos
                    what = 1
                }
                mHandler.sendMessage(obtainMessage)
            }
        }

        override fun deleteState(state: Boolean, userinfos: MutableList<UserInfo>?) {

        }

        override fun queryState(state: Boolean, userinfos: MutableList<UserInfo>?) {

        }
    }
}

/**
 * Extension function to simplify setting an afterTextChanged action to EditText components.
 */
fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(editable: Editable?) {
            afterTextChanged.invoke(editable.toString())
        }

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
    })
}
