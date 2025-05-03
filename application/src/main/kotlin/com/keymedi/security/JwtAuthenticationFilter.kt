package com.keymedi.security

import com.keymedi.utils.TimeUnit
import jakarta.servlet.FilterChain
import jakarta.servlet.http.Cookie
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpHeaders
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class JwtAuthenticationFilter(
    private val jwtService: JwtService
) : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val token = extractTokenFromHeaderOrCookie(request)

        if (token != null && jwtService.validateToken(token)) {
            val userId = jwtService.getUserId(token)

            val authentication = UsernamePasswordAuthenticationToken(userId, null, emptyList())
            SecurityContextHolder.getContext().authentication = authentication

            if (jwtService.isTokenExpiringSoon(token)) {
                val newToken = jwtService.generateAccessToken(userId)
                response.setHeader(KEYMEDI_CUSTOM_HEADER, newToken)

                val cookie = genAuthCookie(newToken)
                response.addCookie(cookie)
            }
        }

        filterChain.doFilter(request, response)
    }

    private fun extractTokenFromHeaderOrCookie(request: HttpServletRequest): String? {
        val header = request.getHeader(HttpHeaders.AUTHORIZATION)
        if (header != null && header.startsWith(BEARER_PREFIX)) {
            return header.substring(7)
        }

        return request.cookies?.firstOrNull { it.name == "AUTH_TOKEN" }?.value
    }

    companion object {
        const val BEARER_PREFIX = "Bearer "
        const val KEYMEDI_CUSTOM_HEADER = "Keymedi-Token"

        fun genAuthCookie(authToken: String): Cookie {
            return Cookie("AUTH_TOKEN", authToken).apply {
                path = "/"
                isHttpOnly = true
                secure = true
                maxAge = TimeUnit.MINUTE.toSeconds(30).toInt()
            }
        }
    }
}