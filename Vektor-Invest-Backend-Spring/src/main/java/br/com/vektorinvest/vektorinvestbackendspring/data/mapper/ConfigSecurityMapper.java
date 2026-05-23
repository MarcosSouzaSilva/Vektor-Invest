package br.com.vektorinvest.vektorinvestbackendspring.data.mapper;

import br.com.vektorinvest.vektorinvestbackendspring.data.entity.Users;
import br.com.vektorinvest.vektorinvestbackendspring.infra.security.ConfigSecurity;

public class ConfigSecurityMapper {

    public ConfigSecurity toSecurity(Users user) {

        return ConfigSecurity.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .password(user.getPassword())
                .role(user.getRole())
                .build();
    }

}