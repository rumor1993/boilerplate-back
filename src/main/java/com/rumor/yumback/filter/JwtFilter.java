package com.rumor.yumback.filter;

import com.rumor.yumback.domains.oauth2.dto.CustomOauth2User;
import com.rumor.yumback.domains.oauth2.dto.Oauth2UserDto;
import com.rumor.yumback.enumeration.UserRole;
import com.rumor.yumback.utils.JwtUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import net.minidev.json.JSONObject;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
    private final JwtUtils jwtUtils;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorization = null;

        if (request.getCookies() == null) {
            filterChain.doFilter(request, response);
            return;
        }

        for (Cookie cookie : request.getCookies()) {
            if (cookie.getName().equals("Authorization")) {
                authorization = cookie.getValue();
            }
        }

        //Authorization 헤더 검증
        if (authorization == null) {
            request.setAttribute("message", "토큰이 없습니다.");
            filterChain.doFilter(request, response);
            return;
        }

        if (jwtUtils.isExpired(authorization)) {
            request.setAttribute("message", "토큰이 만료되었습니다.");
            filterChain.doFilter(request, response);
            return;
        }

        String username = jwtUtils.getUsername(authorization);
        String role = jwtUtils.getRole(authorization);
        String picture = jwtUtils.getPicture(authorization);

        Oauth2UserDto oauth2UserDto = new Oauth2UserDto(UserRole.valueOf(role), null, username, picture);
        CustomOauth2User customOauth2User = new CustomOauth2User(oauth2UserDto);

        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(customOauth2User, null, customOauth2User.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(authToken);
        filterChain.doFilter(request, response);
    }
}
