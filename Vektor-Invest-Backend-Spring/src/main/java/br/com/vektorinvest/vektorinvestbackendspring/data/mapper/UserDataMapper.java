package br.com.vektorinvest.vektorinvestbackendspring.data.mapper;

import br.com.vektorinvest.vektorinvestbackendspring.data.entity.Users;
import br.com.vektorinvest.vektorinvestbackendspring.usecases.domains.UsersInfoDomain;
import br.com.vektorinvest.vektorinvestbackendspring.usecases.domains.UsersSignUpDomain;
import br.com.vektorinvest.vektorinvestbackendspring.usecases.enums.ActivityStatus;
import br.com.vektorinvest.vektorinvestbackendspring.usecases.enums.AuthProvider;
import br.com.vektorinvest.vektorinvestbackendspring.usecases.enums.Role;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class UserDataMapper {

    public static LocalDate getAnoAtualComoLocalDate(int year, int month, int day) {
        return LocalDate.of(year, month, day);
    }

    public static Users convert(UsersSignUpDomain domain) {

        return Users.builder()
                .name(domain.getName())
                .email(domain.getEmail())
                .password(domain.getPassword())
                .enabled(ActivityStatus.ACTIVE)
                .birthDate(domain.getBirthDate())
                .investorType(domain.getInvestorType())
                .updatedAt(getAnoAtualComoLocalDate(LocalDate.now().getYear(), LocalDate.now().getMonthValue(), LocalDate.now().getDayOfMonth()))
                .provider(AuthProvider.LOCAL)
                .role(Role.USER)
                .build();
    }

    public static Users convertGoogle(UsersSignUpDomain domain) {

        return Users.builder()
                .name(domain.getName())
                .email(domain.getEmail())
                .password(domain.getPassword())
                .enabled(ActivityStatus.ACTIVE)
                .birthDate(domain.getBirthDate())
                .investorType(domain.getInvestorType())
                .updatedAt(getAnoAtualComoLocalDate(LocalDate.now().getYear(), LocalDate.now().getMonthValue(), LocalDate.now().getDayOfMonth()))
                .provider(AuthProvider.GOOGLE)
                .role(Role.USER)
                .build();
    }



}