package com.rumor.yumback.domains.users.presentation;

import com.rumor.yumback.common.FilesProperties;
import com.rumor.yumback.domains.oauth2.dto.CustomOauth2User;
import com.rumor.yumback.domains.users.application.UserService;
import com.rumor.yumback.domains.users.domain.User;
import com.rumor.yumback.domains.users.presentation.view.UserView;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final FilesProperties filesProperties;

    @GetMapping("/me")
    public UserView me(@AuthenticationPrincipal CustomOauth2User customOauth2User) {
        User foundUser = userService.profile(customOauth2User.getUsername());
        return UserView.of(foundUser);
    }
}
