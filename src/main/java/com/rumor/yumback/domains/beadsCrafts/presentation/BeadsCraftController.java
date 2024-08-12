package com.rumor.yumback.domains.beadsCrafts.presentation;

import com.rumor.yumback.common.SuccessResponse;
import com.rumor.yumback.common.errors.CheckAuthentication;
import com.rumor.yumback.domains.beadsCrafts.application.BeadsCraftRegisterDto;
import com.rumor.yumback.domains.beadsCrafts.application.BeadsCraftService;
import com.rumor.yumback.domains.beadsCrafts.domain.BeadsCraft;
import com.rumor.yumback.domains.beadsCrafts.presentation.request.BeadsCraftRequest;
import com.rumor.yumback.domains.oauth2.dto.CustomOauth2User;
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

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.UUID;

@RestController
@RequestMapping("/beadcrafts")
@RequiredArgsConstructor
public class BeadsCraftController {
    private final BeadsCraftService beadsCraftService;

    @GetMapping
    public Page<BeadsCraftView> beadCrafts(@AuthenticationPrincipal CustomOauth2User customOauth2User, @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        if (customOauth2User == null) {
            return beadsCraftService.beadsCrafts(pageable);
        }

        return beadsCraftService.beadsCrafts(customOauth2User.getUsername(), pageable);
    }

    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    @CheckAuthentication
    public BeadsCraftView addBeadCraft(@AuthenticationPrincipal CustomOauth2User customOauth2User, @Valid @RequestPart BeadsCraftRequest beadsCraftRequest, @RequestPart("file") MultipartFile file) throws URISyntaxException, IOException {
        BeadsCraft beadsCraft = beadsCraftService.addBeadCraft(customOauth2User.getUsername(), new BeadsCraftRegisterDto(beadsCraftRequest.name(), beadsCraftRequest.category(), file));
        return BeadsCraftView.from(beadsCraft);
    }

    @PostMapping("/{id}/likes")
    @CheckAuthentication
    public ResponseEntity<SuccessResponse> likes(@AuthenticationPrincipal CustomOauth2User customOauth2User, @PathVariable UUID id) {
        String likesMessage = beadsCraftService.likes(id, customOauth2User.getUsername());
        return ResponseEntity.ok(new SuccessResponse(HttpStatus.CREATED, likesMessage));
    }
}
