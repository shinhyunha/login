package com.simplelogin.biz.naver.contorller

import com.simplelogin.biz.naver.service.NaverService
import io.swagger.v3.oas.annotations.Operation
import lombok.RequiredArgsConstructor
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/naver")
class NaverApiController(
    private val naverService: NaverService
) {
    /**
     * 네이버 로그인 인증 요청
     * */
    @GetMapping("/authorize")
    @Operation(summary = "네이버 로그인 인증 요청", description = "네이버 로그인시 필요한 인증 요청")
    fun authorize(): ResponseEntity<String> {
        return naverService.authorize()
    }

    /**
     * 네이버 접근 토큰 활용
     * */
    @GetMapping("/token/{code}")
    @Operation(summary = "네이버 로그인 토큰 발급", description = "네이버 로그인 토큰 발급")
    fun tokenAccess(@PathVariable("code") code: String): ResponseEntity<String> {
        return naverService.tokenAcess(code)
    }

    /**
     * 네이버 갱신 토큰 활용
     * */
    @GetMapping("/token/refresh/{token}")
    @Operation(summary = "네이버 로그인 토큰 갱신", description = "네이버 로그인 갱신 토큰을 활용하여 인증 갱신")
    fun tokenRefresh(@PathVariable("token") token: String): ResponseEntity<String> {
        return naverService.refreshToken(token)
    }

    /**
     * 네이버 접근 토큰 삭제
     * */
    @GetMapping("/token/delete/{token}")
    @Operation(summary = "네이버 로그인 접근 토큰 삭제", description = "네이버 로그인 접근 토큰 삭제")
    fun tokenDelete(@PathVariable("token") token: String): ResponseEntity<String> {
        return naverService.deleteToken(token)
    }
}