package com.simplelogin.biz.naver.dto

data class Response(
    private val resultCode: String?,

    private val message: String?,

    private val response: Any?,
)
