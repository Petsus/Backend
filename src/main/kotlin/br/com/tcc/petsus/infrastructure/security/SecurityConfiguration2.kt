package br.com.tcc.petsus.infrastructure.security

import br.com.tcc.petsus.application.filter.AuthenticationFilter
import br.com.tcc.petsus.domain.repository.database.user.UserRepository
import br.com.tcc.petsus.domain.services.security.AuthenticationService
import br.com.tcc.petsus.domain.services.security.TokenService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

//@Configuration
//@EnableWebSecurity
//class SecurityConfiguration2(
//    private val tokenService: TokenService,
//    private val userRepository: UserRepository,
//    private val userDetailsService: AuthenticationService
//) {
//    /**
//     * Configurations of authorization
//     */
//    @Bean
//    @Throws(Exception::class)
//    fun filterChain(http: HttpSecurity): SecurityFilterChain {
//        return http
//            .authorizeRequests()
//            .antMatchers("/test/**").permitAll()
//            .antMatchers("/about/**").permitAll()
//            .antMatchers(HttpMethod.POST, "/auth/**").permitAll()
//            .antMatchers(HttpMethod.POST, "/user/reset-password").permitAll()
//            .antMatchers(HttpMethod.POST, "/user/change-password").permitAll()
//            .antMatchers(HttpMethod.GET, "/address/city").permitAll()
//            .antMatchers(HttpMethod.GET, "/address/state").permitAll()
//            .antMatchers(HttpMethod.GET, "/exam/**").permitAll()
//            .antMatchers(HttpMethod.POST, "/exam/**").hasRole("ADM")
//            .antMatchers(HttpMethod.GET, "/actuator/**").hasRole("ADM")
//            .antMatchers(HttpMethod.POST, "/actuator/state").hasRole("ADM")
//            .antMatchers(HttpMethod.POST, "/actuator/city").hasRole("ADM")
//            .antMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()
//            .anyRequest().authenticated()
//            .and().csrf().disable()
//            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//            .and().addFilterBefore(AuthenticationFilter(tokenService, userRepository), UsernamePasswordAuthenticationFilter::class.java)
//            .httpBasic(Customizer.withDefaults())
//            .build()
//    }
//    /**
//     * Configurations of static resources
//     */
//    @Bean
//    fun webSecurityCustomizer(): WebSecurityCustomizer {
//        return WebSecurityCustomizer { webSecurity ->
//            webSecurity.ignoring()
//                .antMatchers("/**.html", "/v2/api-docs", "/webjars/**", "/configuration/**", "/swagger-resources/**")
//        }
//    }
//    /**
//     * Configurations of authentication
//     */
//    @Bean
//    @Throws(Exception::class)
//    fun authenticationManager(auth: AuthenticationManagerBuilder) {
//        auth.userDetailsService(userDetailsService).passwordEncoder(BCryptPasswordEncoder())
//    }
//
//    @Bean
//    @Throws(Exception::class)
//    fun authenticationManager(http: HttpSecurity): AuthenticationManager {
//        return http.getSharedObject(AuthenticationManagerBuilder::class.java)
//            .build()
//    }
//}