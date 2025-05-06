package com.keymedi.security

import com.keymedi.utils.TimeUnit
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import mu.KLogging
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.util.Date
import javax.crypto.SecretKey

@Component
class JwtService(
    @Value("\${jwt.secret}") private val secretKey: String,
) {
    private val key: SecretKey = Keys.hmacShaKeyFor(secretKey.toByteArray())

    fun generateAccessToken(userId: String): String {
        val claims = Jwts.claims().subject(userId.toString()).build()
        return Jwts.builder()
            .claims(claims)
            .subject(userId)
            .issuedAt(Date())
            .expiration(Date(System.currentTimeMillis() + ACCESS_TOKEN_EXPIRATION))
            .signWith(key)
            .compact()
    }

    fun getUserId(token: String): String {
        return Jwts
            .parser()
            .verifyWith(key)
            .build()
            .parseSignedClaims(token)
            .payload.subject
    }

    // Refresh Token 생성
    fun generateRefreshToken(userId: String): String {
        return Jwts.builder()
            .subject(userId)
            .issuedAt(Date())
            .expiration(Date(System.currentTimeMillis() + REFRESH_TOKEN_EXPIRATION))
            .signWith(key)
            .compact()
    }

    fun validateToken(token: String): Boolean {
        return try {
            Jwts.parser().verifyWith(key).build().parseSignedClaims(token)
            true
        } catch (e: Exception) {
            false
        }
    }

    fun isTokenExpiringSoon(token: String): Boolean {
        val claims = Jwts.parser().verifyWith(key).build().parseSignedClaims(token).payload
        val expiration = claims.expiration.time
        val now = System.currentTimeMillis()
        return (expiration - now) <= (TimeUnit.MINUTE.toMillis(3))
    }

    companion object : KLogging() {
        val ACCESS_TOKEN_EXPIRATION = TimeUnit.MINUTE.toMillis(30)
        private val REFRESH_TOKEN_EXPIRATION = TimeUnit.DAY.toMillis(14)
    }
}