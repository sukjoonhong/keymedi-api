package kr.co.medipanda.portal.security

import kr.co.medipanda.portal.config.app.ApiAuthenticationProperties
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter


@Configuration
@EnableWebSecurity
class WebSecurityConfig(
    private val apiAuthenticationProperties: ApiAuthenticationProperties,
    private val jwtAuthenticationFilter: JwtAuthenticationFilter,
) {
    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain = http
        .cors { }
        .csrf { it.disable() }
        .authorizeHttpRequests {
            it.requestMatchers(
                "/swagger-ui/**", //TODO: 운영 배포시 제거
                "/api-docs/**", //TODO: 운영 배포시 제거
                "/v1/auth/login",
                "/v1/auth/token/refresh",
                "/v1/auth/public-key"
            ).permitAll()
            it.anyRequest().authenticated()
        }
        .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter::class.java)
        .build()

    @Autowired
    @Throws(Exception::class)
    fun configureGlobal(auth: AuthenticationManagerBuilder) {
        auth.inMemoryAuthentication()
            .withUser(apiAuthenticationProperties.username)
            .password("{noop}${apiAuthenticationProperties.password}")
            .roles("ADMIN")
    }
}