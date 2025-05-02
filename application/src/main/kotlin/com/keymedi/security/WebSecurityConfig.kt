package com.keymedi.security

import com.keymedi.config.app.ApiAuthenticationProperties
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter


@Configuration
@EnableWebSecurity
class WebSecurityConfig(
    private val apiAuthenticationProperties: ApiAuthenticationProperties,
) {
    @Bean
    fun webSecurityCustomizer(): WebSecurityCustomizer = WebSecurityCustomizer {
        it.ignoring().requestMatchers("/actuator/**")
        it.ignoring().requestMatchers("/")
    }

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain = http
        .csrf { it.disable() }
        .authorizeHttpRequests {
            it.requestMatchers(
                "/**"
            ).permitAll()
            it.anyRequest().authenticated()
        }
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