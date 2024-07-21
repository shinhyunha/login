package com.simplelogin.biz.kakao.service

import com.simplelogin.biz.kakao.dto.ReqSearchProfile
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap
import org.springframework.web.client.RestClient
import org.springframework.web.client.toEntity

@Service
class KakaoService(
    @Value("\${kakao.rest-api-key}")
    private val restApiKey: String,
    @Value("\${kakao.client-secret-key}")
    private val clientSecretkey: String
) {
    private val CALLBACK_URL = "http://localhost:8080/success"

    fun authorize(): String {
        var authorizeUrl = "https://kauth.kakao.com/oauth/authorize"
        authorizeUrl += "?client_id=$restApiKey&redirect_uri=$CALLBACK_URL&response_type=code"
        return restclientGet(authorizeUrl).headers.location.toString()
    }

    private fun restclientGet(uri: String): ResponseEntity<String> {
        try {
            val restClient = RestClient.create()
            return restClient.get()
                .uri(uri)
                .accept(APPLICATION_JSON)
                .retrieve()
                .toEntity<String>()
        } catch (e: Exception) {
            println("통신 오류 발생")
            e.printStackTrace()
            throw e
        }
    }

    fun getToken(token: String): ResponseEntity<String> {
        val tokenUrl = "https://kauth.kakao.com/oauth/token"
        // Content-type: application/x-www-form-urlencoded;charset=utf-8으로 요청할경우 MultiValueMap을 사용한다.
        val bodyMap: MultiValueMap<String, String> = LinkedMultiValueMap()
        bodyMap["grant_type"] = "authorization_code"
        bodyMap["client_id"] = restApiKey
        bodyMap["redirect_uri"] = CALLBACK_URL
        bodyMap["code"] = token
        bodyMap["client_secret"] = clientSecretkey

        try {
            val restClient = RestClient.create()
            return restClient.post()
                .uri(tokenUrl)
                .accept(APPLICATION_JSON)
                .body(bodyMap)
                .retrieve()
                .toEntity<String>()
        } catch (e: Exception) {
            println("통신 오류 발생")
            e.printStackTrace()
            throw e
        }
    }

    fun logout(token: String): ResponseEntity<String> {
        val logoutUrl = "https://kapi.kakao.com/v1/user/logout"

        try {
            val restClient = RestClient.create()
            return restClient.post()
                .uri(logoutUrl)
                .accept(APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, "Bearer $token")
                .retrieve()
                .toEntity<String>()
        } catch (e: Exception) {
            println("통신 오류 발생")
            e.printStackTrace()
            throw e
        }
    }

    fun getProfile(reqSearchProfile: ReqSearchProfile): ResponseEntity<String> {
        val getProfileUrl = "https://kapi.kakao.com/v2/user/me"

        try {
            val restClient = RestClient.create()
            return restClient.post()
                .uri(getProfileUrl)
                .accept(APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, "Bearer ${reqSearchProfile.accessToken}")
                .retrieve()
                .toEntity<String>()
        } catch (e: Exception) {
            println("통신 오류 발생")
            e.printStackTrace()
            throw e
        }
    }
}