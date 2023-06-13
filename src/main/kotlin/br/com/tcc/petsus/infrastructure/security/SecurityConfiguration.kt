package br.com.tcc.petsus.infrastructure.security

import br.com.tcc.petsus.application.service.security.AuthenticationServiceImpl
import br.com.tcc.petsus.application.service.security.TokenServiceImpl
import br.com.tcc.petsus.application.filter.AuthenticationFilter
import br.com.tcc.petsus.domain.repository.user.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter


@Configuration
@EnableWebSecurity
class SecurityConfiguration : WebSecurityConfigurerAdapter() {

    @Autowired
    private lateinit var userDetailsService: AuthenticationServiceImpl

    @Autowired
    private lateinit var tokenService: TokenServiceImpl

    @Autowired
    private lateinit var repository: UserRepository

    /**
     * Configurations of authorization
     */
    override fun configure(http: HttpSecurity) {
        http.authorizeRequests()
            .antMatchers("/test/**").permitAll()
            .antMatchers("/about/**").permitAll()
            .antMatchers(HttpMethod.POST, "/auth/**").permitAll()
            .antMatchers(HttpMethod.POST, "/user/reset-password").permitAll()
            .antMatchers(HttpMethod.POST, "/user/change-password").permitAll()
            .antMatchers(HttpMethod.GET, "/address/city").permitAll()
            .antMatchers(HttpMethod.GET, "/address/state").permitAll()
            .antMatchers(HttpMethod.GET, "/exam/**").permitAll()
            .antMatchers(HttpMethod.POST, "/exam/**").hasRole("ADM")
            .antMatchers(HttpMethod.GET, "/actuator/**").hasRole("ADM")
            .antMatchers(HttpMethod.POST, "/actuator/state").hasRole("ADM")
            .antMatchers(HttpMethod.POST, "/actuator/city").hasRole("ADM")
            .antMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()
            .anyRequest().authenticated()
            .and().csrf().disable()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and().addFilterBefore(AuthenticationFilter(tokenService, repository), UsernamePasswordAuthenticationFilter::class.java)
    }

    /**
     * Configurations of authentication
     */
    override fun configure(auth: AuthenticationManagerBuilder) {
        auth.userDetailsService(userDetailsService).passwordEncoder(BCryptPasswordEncoder())
            .and()
    }

    /**
     * Configurations of static resources
     */
    override fun configure(web: WebSecurity) {
        web.ignoring().antMatchers("/**.html", "/v2/api-docs", "/webjars/**", "/configuration/**", "/swagger-resources/**")
    }

    @Bean
    override fun authenticationManager(): AuthenticationManager {
        return super.authenticationManager()
    }

}