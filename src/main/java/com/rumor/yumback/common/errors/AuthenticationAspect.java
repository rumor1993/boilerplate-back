package com.rumor.yumback.common.errors;

import com.rumor.yumback.domains.oauth2.dto.CustomOauth2User;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AuthenticationAspect {

    @Before("@annotation(CheckAuthentication)")
    public void checkAuthentication(JoinPoint joinPoint) throws Throwable {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated() || !(authentication.getPrincipal() instanceof CustomOauth2User)) {
            throw new OauthUserNotFoundException("로그인이 필요합니다.");
        }
    }
}
