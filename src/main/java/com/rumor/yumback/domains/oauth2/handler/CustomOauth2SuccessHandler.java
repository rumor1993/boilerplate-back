package com.rumor.yumback.domains.oauth2.handler;

import com.rumor.yumback.domains.oauth2.dto.CustomOauth2User;
import com.rumor.yumback.enumeration.UserRole;
import com.rumor.yumback.utils.JwtUtils;
import com.rumor.yumback.utils.ResourcesProperties;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;

@Component
@RequiredArgsConstructor
public class CustomOauth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final JwtUtils jwtUtils;
    private final ResourcesProperties resourcesProperties;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        CustomOauth2User customOauth2User = (CustomOauth2User) authentication.getPrincipal();
        String username = customOauth2User.getUsername();

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
        GrantedAuthority auth = iterator.next();
        String role = auth.getAuthority();

        String token = jwtUtils.generateToken(username, UserRole.valueOf(role), customOauth2User.getPicture(), 60 * 60 * 1000L);


        response.addCookie(createCookie("Authorization", token));
        response.sendRedirect(resourcesProperties.getPath());
    }

    private Cookie createCookie(String authorization, String token) {
        Cookie cookie = new Cookie(authorization, token);
        cookie.setMaxAge(60*60*60);
        //cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setDomain("localhost");
        cookie.setHttpOnly(true);
        return cookie;
    }
}
