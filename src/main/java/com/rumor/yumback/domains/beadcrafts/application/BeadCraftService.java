package com.rumor.yumback.domains.beadcrafts.application;

import com.rumor.yumback.domains.beadcrafts.domain.BeadCraft;
import com.rumor.yumback.domains.beadcrafts.domain.BeadCraftLikes;
import com.rumor.yumback.domains.beadcrafts.infrastructure.BeadCraftJpaRepository;
import com.rumor.yumback.domains.beadcrafts.infrastructure.BeadCraftLikesJpaRepository;
import com.rumor.yumback.domains.beadcrafts.infrastructure.BeadsQueryDslRepository;
import com.rumor.yumback.domains.beadcrafts.presentation.BeadCraftView;
import com.rumor.yumback.domains.files.application.FileSystemStorageService;
import com.rumor.yumback.domains.users.domain.User;
import com.rumor.yumback.domains.users.infrastructure.UserJpaRepository;
import com.rumor.yumback.enumeration.BeadCraftCategory;
import com.rumor.yumback.enumeration.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.nio.file.Path;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BeadCraftService {
    private final UserJpaRepository userJpaRepository;
    private final BeadCraftJpaRepository beadCraftJpaRepository;
    private final BeadsQueryDslRepository beadsQueryDslRepository;
    private final BeadCraftLikesJpaRepository beadCraftLikesJpaRepository;
    private final FileSystemStorageService fileSystemStorageService;

    public BeadCraft addBeadCraft(String username, BeadCraftRegisterDto beadCraftRegisterDto) {
        User foundUser = userJpaRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("not found user"));

        Path savedFile = fileSystemStorageService.store(beadCraftRegisterDto.file());
        String uriString = fileSystemStorageService.loadAsUrl(savedFile);

        BeadCraft beadCraft = new BeadCraft(beadCraftRegisterDto.name(), beadCraftRegisterDto.category(), uriString, foundUser);
        return beadCraftJpaRepository.save(beadCraft);
    }

    public Page<BeadCraftView> beadCrafts(Pageable pageable) {
        return beadsQueryDslRepository.findBeadCrafts(null, pageable, null);
    }

    public Page<BeadCraftView> beadCrafts(String username, BeadCraftCategory category, Pageable pageable) {
        User foundUser = userJpaRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("not found user"));

        return beadsQueryDslRepository.findBeadCrafts(foundUser, pageable, category);
    }

    public String likes(UUID id, String username) {
        User foundUser = userJpaRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("not found user"));

        BeadCraft foundBeadCraft = beadCraftJpaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("not found bead craft"));

        return beadCraftLikesJpaRepository.findByBeadCraftAndUser(foundBeadCraft, foundUser)
                .map(beadCraftLikes -> {
                    unlikes(beadCraftLikes);
                    return "좋아요가 취소되었습니다.";
                })
                .orElseGet(() -> {
                    beadCraftLikesJpaRepository.save(new BeadCraftLikes(foundBeadCraft, foundUser));
                    return "좋아요를 누르셨습니다.";
                });
    }

    private void unlikes(BeadCraftLikes beadCraftLikes) {
        beadCraftLikesJpaRepository.delete(beadCraftLikes);
    }
}
