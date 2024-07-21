package com.simplelogin.biz.kakao.controller

import com.simplelogin.biz.kakao.dto.ReqSearchProfile
import com.simplelogin.biz.kakao.service.KakaoService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.tags.Tag
import lombok.RequiredArgsConstructor
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/kakao")
@Tag(name = "kakao login")
class KakaoApiController(
    private val kakaoService: KakaoService
) {
    /**
     * kakao 인증 코드
     * */
    @GetMapping("/authorize")
    @Operation(summary = "카카오 로그인 인증 요청", description = "카카오 로그인시 필요한 인증 요청")
    fun authorize(): String {
        return kakaoService.authorize()
    }

    /**
     * kakao 토큰 발급
     * */
    @GetMapping("/token/{token}")
    @Operation(summary = "카카오 토큰 발급", description = "카카오 토큰 발급 요청")
    fun getToken(@PathVariable("token") token: String): ResponseEntity<String> {
        return kakaoService.getToken(token)
    }

    /**
     * kakao 로그아웃
     * */
    @GetMapping("/logout/{token}")
    @Operation(summary = "카카오 로그아웃", description = "카카오 로그아웃 요청")
    fun logout(@PathVariable("token") token: String): ResponseEntity<String> {
        return kakaoService.logout(token)
    }

    /**
     * kakao 로그아웃
     * */
    @PostMapping("/getProfile")
    @Operation(summary = "카카오 프로필 조회", description = "카카오 프로필 조회 요청")
    fun getProfile(@Parameter reqSearchProfile: ReqSearchProfile): ResponseEntity<String> {
        return kakaoService.getProfile(reqSearchProfile)
    }

}