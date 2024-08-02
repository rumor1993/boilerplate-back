package com.rumor.yumback.domains.beadcrafts.presentation;

import com.rumor.yumback.common.SuccessResponse;
import com.rumor.yumback.common.errors.CheckAuthentication;
import com.rumor.yumback.common.errors.OauthUserNotFoundException;
import com.rumor.yumback.domains.beadcrafts.application.BeadCraftRegisterDto;
import com.rumor.yumback.domains.beadcrafts.application.BeadCraftService;
import com.rumor.yumback.domains.beadcrafts.domain.BeadCraft;
import com.rumor.yumback.domains.beadcrafts.presentation.request.BeadCraftRequest;
import com.rumor.yumback.domains.oauth2.dto.CustomOauth2User;
import com.rumor.yumback.enumeration.BeadCraftCategory;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("beadcrafts")
@RequiredArgsConstructor
public class BeadCraftController {
    private final BeadCraftService beadCraftService;

    @GetMapping
    public Page<BeadCraftView> beadCrafts(@AuthenticationPrincipal CustomOauth2User customOauth2User, @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable, @RequestParam(required = false) BeadCraftCategory category) {
        if (customOauth2User == null) {
            return beadCraftService.beadCrafts(pageable);
        }

        return beadCraftService.beadCrafts(customOauth2User.getUsername(), category, pageable);
    }

    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    @CheckAuthentication
    public BeadCraftView addBeadCraft(@AuthenticationPrincipal CustomOauth2User customOauth2User, @Valid @RequestPart BeadCraftRequest beadCraftRequest, @RequestPart("file") MultipartFile file) {
        BeadCraft beadCraft = beadCraftService.addBeadCraft(customOauth2User.getUsername(), new BeadCraftRegisterDto(beadCraftRequest.name(), beadCraftRequest.category(), file));
        return BeadCraftView.of(beadCraft);
    }

    @PostMapping("/{id}/likes")
    @CheckAuthentication
    public ResponseEntity<SuccessResponse> likes(@AuthenticationPrincipal CustomOauth2User customOauth2User, @PathVariable UUID id) {
        String likesMessage = beadCraftService.likes(id, customOauth2User.getUsername());
        return ResponseEntity.ok(new SuccessResponse(HttpStatus.CREATED, likesMessage));
    }
}
