package com.rumor.yumback.domains.users.presentation;

import com.rumor.yumback.common.FilesProperties;
import com.rumor.yumback.common.SuccessResponse;
import com.rumor.yumback.common.errors.CheckAuthentication;
import com.rumor.yumback.domains.oauth2.dto.CustomOauth2User;
import com.rumor.yumback.domains.users.application.UserService;
import com.rumor.yumback.domains.users.domain.User;
import com.rumor.yumback.domains.users.presentation.request.UserEditorRequest;
import com.rumor.yumback.domains.users.presentation.view.UserView;
import com.rumor.yumback.utils.ResourcesProperties;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final ResourcesProperties resourcesProperties;

    @GetMapping("/me")
    public UserView me(@AuthenticationPrincipal CustomOauth2User customOauth2User) {
        User foundUser = userService.profile(customOauth2User.getUsername());
        return UserView.from(foundUser);
    }

    @PatchMapping("/{id}")
    public SuccessResponse edit(@PathVariable UUID id, @Valid @RequestBody UserEditorRequest userEditorRequest) {
        User editedUser = userService.edit(id, userEditorRequest.toDto());
        return new SuccessResponse(HttpStatus.CREATED, "User edited successfully.");
    }

    @DeleteMapping("/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                Cookie deleteCookie = new Cookie("Authorization", null);
                deleteCookie.setMaxAge(0); // 쿠키 만료 시간 설정
                deleteCookie.setPath("/"); // 경로 설정 (옵션, 필요에 따라 설정)
                deleteCookie.setDomain(resourcesProperties.getDomain());
                deleteCookie.setHttpOnly(true);

                response.addCookie(deleteCookie);
                response.addHeader("Set-Cookie", deleteCookie.toString());
            }
        }
    }
}
