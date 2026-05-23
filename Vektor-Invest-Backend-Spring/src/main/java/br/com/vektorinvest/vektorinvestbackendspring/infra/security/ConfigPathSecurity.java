package br.com.vektorinvest.vektorinvestbackendspring.infra.security;

import br.com.vektorinvest.vektorinvestbackendspring.data.Implementations.UsersDataImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class ConfigPathSecurity {

    @Autowired
    private CustomUserDetailsService detailsService;

    @Value("${session.secret}")
    private String sessionSecret;

    @Value("${time.rememberMe}")
    private Integer timeSessionToRememberMe;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, PersistentTokenRepository tokenRepository, UsersDataImpl usersData) throws Exception {
        return http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/",
                                "/login",
                                "/signUp",
                                "/stock",
                                "/signIn",
                                "/oauth2/**",
                                "/css/**",
                                "/js/**",
                                "/img/**"
                        ).permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .loginProcessingUrl("/signIn")
                        .usernameParameter("email")
                        .passwordParameter("password")
                        .defaultSuccessUrl("/profile", true)
                        .permitAll()
                )
                .oauth2Login(oauth -> oauth
                        .loginPage("/login")
                        .userInfoEndpoint(user -> user
                                .userService(usersData)
                        )
                        .defaultSuccessUrl("/profile", true)
                )

                .securityContext(context -> context
                        .requireExplicitSave(false)
                )
                .exceptionHandling(exception -> exception
                        .accessDeniedHandler((request, response, accessDeniedException) -> {
                            response.sendRedirect("/");
                        })
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/")
                        .deleteCookies("JSESSIONID", "remember-me")
                )
                .rememberMe(remember -> remember
                        .key(sessionSecret)
                        .alwaysRemember(true)
                        .tokenValiditySeconds(timeSessionToRememberMe)
                        .tokenRepository(tokenRepository)
                        .userDetailsService(detailsService)
                )
                .build();
    }

    @Bean
    public PersistentTokenRepository persistentTokenRepository(DataSource dataSource) {
        JdbcTokenRepositoryImpl repo = new JdbcTokenRepositoryImpl();
        repo.setDataSource(dataSource);
        return repo;
    }

    @Bean
    public OAuth2UserService<OAuth2UserRequest, OAuth2User> oauth2UserService() {
        return request -> {
            OAuth2User oauthUser = new DefaultOAuth2UserService().loadUser(request);
            return oauthUser;
        };
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(9);
    }

}
