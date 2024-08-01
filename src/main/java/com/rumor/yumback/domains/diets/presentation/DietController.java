package com.rumor.yumback.domains.diets.presentation;

import com.rumor.yumback.domains.diets.application.DietService;
import com.rumor.yumback.domains.diets.domain.Diet;
import com.rumor.yumback.domains.diets.presentation.request.DietRegisterRequest;
import com.rumor.yumback.domains.diets.presentation.view.DietView;
import com.rumor.yumback.domains.oauth2.dto.CustomOauth2User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/diets")
@RequiredArgsConstructor
public class DietController {
    private final DietService dietService;

    @GetMapping
    public List<DietView> diets(@AuthenticationPrincipal CustomOauth2User customOauth2User) {
        return dietService.diets(customOauth2User.getUsername()).stream()
                .map(DietView::of)
                .toList();
    }

    @PostMapping(consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public DietView register(@AuthenticationPrincipal CustomOauth2User customOauth2User, @RequestPart DietRegisterRequest dietRegisterRequest, @RequestPart MultipartFile file) throws IOException {
        Diet diet = dietService.register(customOauth2User.getUsername(), dietRegisterRequest, file);
        return DietView.of(diet);
    }
}
