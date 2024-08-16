package com.rumor.yumback.domains.beadsCrafts.presentation;

import com.rumor.yumback.common.SuccessResponse;
import com.rumor.yumback.domains.beadsCrafts.application.BeadsCraftRegisterDto;
import com.rumor.yumback.domains.beadsCrafts.application.BeadsCraftService;
import com.rumor.yumback.domains.beadsCrafts.domain.BeadsCraft;
import com.rumor.yumback.domains.beadsCrafts.presentation.request.BeadsCraftRequest;
import com.rumor.yumback.domains.oauth2.dto.CustomOauth2User;
import com.rumor.yumback.enumeration.BeadCraftCategory;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
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
@RequestMapping("/beads-crafts")
@RequiredArgsConstructor
public class BeadsCraftController {
    private final BeadsCraftService beadsCraftService;

    @GetMapping
    public Page<BeadsCraftView> beadCrafts(@AuthenticationPrincipal CustomOauth2User customOauth2User, @PageableDefault(size = 80, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable, @RequestParam(required = false) BeadCraftCategory category) {
        if (customOauth2User == null) {
            return beadsCraftService.beadsCrafts(pageable, category);
        }

        return beadsCraftService.beadsCrafts(customOauth2User.getUsername(), pageable, category);
    }

    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<SuccessResponse> addBeadCraft(@AuthenticationPrincipal CustomOauth2User customOauth2User, @Valid @RequestPart BeadsCraftRequest beadsCraftRequest, @RequestPart("picture") MultipartFile file) throws URISyntaxException, IOException {
        beadsCraftService.addBeadsCraft(customOauth2User.getUsername(), new BeadsCraftRegisterDto(beadsCraftRequest.name(), beadsCraftRequest.link(), beadsCraftRequest.description(), beadsCraftRequest.authorName(), beadsCraftRequest.category(), file));
        return ResponseEntity.ok(new SuccessResponse(HttpStatus.CREATED, "등록되었습니다."));
    }

    @PostMapping("/{id}/likes")
    public ResponseEntity<SuccessResponse> likes(@AuthenticationPrincipal CustomOauth2User customOauth2User, @PathVariable UUID id) {
        String likesMessage = beadsCraftService.likes(id, customOauth2User.getUsername());
        return ResponseEntity.ok(new SuccessResponse(HttpStatus.CREATED, likesMessage));
    }
}
