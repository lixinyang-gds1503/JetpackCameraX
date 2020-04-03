package cn.lxyhome.jetpackcamerax.activity.ui.login

import cn.lxyhome.jetpackcamerax.dao.entity.UserInfo

/**
 * Authentication result : success (user details) or error message.
 */
data class LoginResult(
    val success: UserInfo? = null,
    val error: Int? = null
)
