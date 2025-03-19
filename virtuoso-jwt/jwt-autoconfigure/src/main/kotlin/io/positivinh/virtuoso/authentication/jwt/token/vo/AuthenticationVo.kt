package io.positivinh.virtuoso.authentication.jwt.token.vo

data class AuthenticationVo(
    val username: String,
    val authorities: MutableList<String> = mutableListOf()
)
