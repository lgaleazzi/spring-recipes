package com.myrecipes.core.config;

import com.myrecipes.core.web.FlashMessage;
import com.myrecipes.service.DetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.data.repository.query.spi.EvaluationContextExtension;
import org.springframework.data.repository.query.spi.EvaluationContextExtensionSupport;
import org.springframework.security.access.expression.SecurityExpressionRoot;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@EnableWebSecurity
public class SecurityConfig
{

    @Configuration
    @Order(1)
    public static class ApiWebSecurityConfigurationAdapter extends WebSecurityConfigurerAdapter {
        protected void configure(HttpSecurity http) throws Exception {
            // TODO: enable security and csrf
            http
                    .antMatcher("/api/**")
                    .authorizeRequests()
                    .anyRequest().authenticated()
                    .and()
                    .httpBasic()
                    .and()
                    .csrf().disable();
        }
    }

    @Configuration
    public static class FormLoginWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter
    {

        @Autowired
        private DetailsService detailsService;

        @Autowired
        public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
            auth.userDetailsService(detailsService).passwordEncoder(passwordEncoder());
        }

        @Bean
        public PasswordEncoder passwordEncoder() {
            return new BCryptPasswordEncoder(10);
        }

        @Override
        public void configure(WebSecurity web) throws Exception {
            web.ignoring().antMatchers("/assets/**");
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            //TODO: configure security
            http
                    .authorizeRequests()
                    .anyRequest()
                    .permitAll()
                    .and().formLogin()
                        .loginPage("/login")
                        .permitAll()
                        .successHandler(loginSuccessHandler())
                        .failureHandler(loginFailureHandler())
                    .and().logout()
                        .permitAll()
                    .logoutSuccessUrl("/")
                    .and().csrf();
        }

        public AuthenticationSuccessHandler loginSuccessHandler() {
            return (request, response, authentication) -> response.sendRedirect("/");
        }

        public AuthenticationFailureHandler loginFailureHandler() {
            return (request, response, authentication) -> {
                request.getSession().setAttribute("flash", new FlashMessage("Incorrect username and/or password. Please try again.", FlashMessage.Status.FAILURE));
                response.sendRedirect("/login");
            };
        }

        @Bean
        public EvaluationContextExtension securityExtension() {
            return new EvaluationContextExtensionSupport() {
                @Override
                public String getExtensionId() {
                    return "security";
                }

                @Override
                public Object getRootObject() {
                    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                    return new SecurityExpressionRoot(authentication) {};
                }
            };
        }
    }
}
