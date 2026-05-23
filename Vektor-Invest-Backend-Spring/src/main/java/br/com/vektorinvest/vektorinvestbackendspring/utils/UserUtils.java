package br.com.vektorinvest.vektorinvestbackendspring.utils;

import br.com.vektorinvest.vektorinvestbackendspring.data.entity.Users;
import br.com.vektorinvest.vektorinvestbackendspring.data.repository.UsersRepository;
import br.com.vektorinvest.vektorinvestbackendspring.infra.security.ConfigSecurity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

public class UserUtils {

    public static Users getUserOrThrow(UsersRepository usersRepository) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null  || auth instanceof AnonymousAuthenticationToken) {

            return null;
        }

        Object principal = auth.getPrincipal();

        // 🔐 Login normal + remember-me
        if (principal instanceof UserDetails userDetails) {

            return usersRepository.findByEmail(userDetails.getUsername()).orElseThrow(() -> new RuntimeException("User not found"));
        }

        // Login Google
        if (principal instanceof OAuth2User oauthUser) {

            String email = oauthUser.getAttribute("email");

            return usersRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
        }

        return null;
    }

}