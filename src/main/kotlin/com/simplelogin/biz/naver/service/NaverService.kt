package com.simplelogin.biz.naver.service

import com.fasterxml.jackson.databind.ObjectMapper
import com.simplelogin.biz.naver.dto.ProfileDto
import com.simplelogin.biz.naver.dto.Response
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.web.client.RestClient
import org.springframework.web.client.body
import org.springframework.web.client.toEntity

@Service
class NaverService(
    @Value("\${naver.client-id}")
    private val clientId: String,
    @Value("\${naver.client-secret}")
    private val clientSecret: String
) {
    private val CALLBACK_URL = "http://localhost:8080/naver/callback"
    private var tokenUrl = "https://nid.naver.com/oauth2.0/token"

    fun authorize(): ResponseEntity<String> {
        var authorizeUrl = "https://nid.naver.com/oauth2.0/authorize"

        authorizeUrl += "?response_type=code&redirect_uri=$CALLBACK_URL&client_id=$clientId&state=개발중"
        return restClientGet(authorizeUrl)
    }

    fun tokenAcess(code: String): ResponseEntity<String> {
        tokenUrl += "?grant_type=authorization_code&client_id=$clientId&client_secret=$clientSecret&code=$code&state=개발중"
        return restClientGet(tokenUrl)
    }

    fun refreshToken(token: String): ResponseEntity<String> {
        tokenUrl += "?grant_type=refresh_token&client_id=$clientId&client_secret=$clientSecret&refresh_token=$token"
        return restClientGet(tokenUrl)
    }

    fun deleteToken(token: String): ResponseEntity<String> {
        tokenUrl += "?grant_type=delete&client_id=$clientId&client_secret=$clientSecret&access_token=$token&service_provider=NAVER"
        return restClientGet(tokenUrl)
    }

    /**
     * 외부 api 요청
     * get type
     * */
    fun restClientGet(uri: String): ResponseEntity<String> {
        try {
            val restClient = RestClient.create()
            val result = restClient.get()
                .uri(uri)
                .accept(APPLICATION_JSON)
                .retrieve()
                .toEntity<String>()
            return result
        } catch (e: RuntimeException) {
            println("통신 오류 발생")
            e.printStackTrace()
            throw e
        }
    }

    fun searchProfile(token: String): ProfileDto {
        var uri = "https://openapi.naver.com/v1/nid/me"

        try {
            var restClient = RestClient.create()
            var result = restClient.get()
                .uri(uri)
                .header(HttpHeaders.AUTHORIZATION, "Bearer $token")
                .accept(APPLICATION_JSON)
                .retrieve()
                .toEntity<String>()

            val data = ObjectMapper().readTree(result.body)["response"]

            val profileDto = ProfileDto(
                id = data["id"].toString().replace("\"", ""),
                nickname = data["nickname"].toString().replace("\"", ""),
                name = data["name"].toString().replace("\"", "")
            )
            println("profileDto = ${profileDto}")
            return profileDto
        }catch (e: RuntimeException) {
            println("통신 오류 발생")
            e.printStackTrace()
            throw e
        }
    }
}