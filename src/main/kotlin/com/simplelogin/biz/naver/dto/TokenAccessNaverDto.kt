package com.simplelogin.biz.naver.dto

data class TokenAccessNaverDto(
    val grant_type: String = "authorization_code",
    val client_id: String,
    val client_secret: String,
    val code: String,
    val state: String
)
