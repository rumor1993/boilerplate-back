package com.rumor.yumback.domains.beadsCrafts.application;

import com.rumor.yumback.domains.beadsCrafts.domain.BeadsCraft;
import com.rumor.yumback.domains.beadsCrafts.domain.BeadsCraftLikes;
import com.rumor.yumback.domains.beadsCrafts.infrastructure.BeadsCraftJpaRepository;
import com.rumor.yumback.domains.beadsCrafts.infrastructure.BeadsCraftLikesJpaRepository;
import com.rumor.yumback.domains.beadsCrafts.presentation.BeadsCraftView;
import com.rumor.yumback.domains.files.application.FileSystemStorageService;
import com.rumor.yumback.domains.users.domain.User;
import com.rumor.yumback.domains.users.infrastructure.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BeadsCraftService {
    private final UserJpaRepository userJpaRepository;
    private final BeadsCraftJpaRepository beadsCraftJpaRepository;
    private final BeadsCraftLikesJpaRepository beadsCraftLikesJpaRepository;
    private final FileSystemStorageService fileSystemStorageService;

    public BeadsCraft addBeadsCraft(String username, BeadsCraftRegisterDto beadsCraftRegisterDto) throws URISyntaxException, IOException {
        User foundUser = userJpaRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("not found user"));

        Path savedFile = fileSystemStorageService.store(beadsCraftRegisterDto.file(), username);
        String uriString = fileSystemStorageService.loadAsUrl(savedFile, username);

        BeadsCraft beadsCraft = new BeadsCraft(beadsCraftRegisterDto.name(), beadsCraftRegisterDto.category(), uriString, foundUser);
        return beadsCraftJpaRepository.save(beadsCraft);
    }

    public Page<BeadsCraftView> beadsCrafts(Pageable pageable) {
        return beadsCraftJpaRepository.findAll(pageable)
                .map(BeadsCraftView::from);
    }

    public Page<BeadsCraftView> beadsCrafts(String username, Pageable pageable) {
        User foundUser = userJpaRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("not found user"));

        return beadsCraftJpaRepository.findAll(pageable)
                .map(beadsCraft -> BeadsCraftView.from(beadsCraft, foundUser));
    }

    public String likes(UUID id, String username) {
        User foundUser = userJpaRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("not found user"));

        BeadsCraft foundBeadsCraft = beadsCraftJpaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("not found bead craft"));

        return beadsCraftLikesJpaRepository.findByBeadsCraftAndUser(foundBeadsCraft, foundUser)
                .map(beadsCraftLikes -> {
                    foundBeadsCraft.decreaseLikeCount();
                    beadsCraftLikesJpaRepository.delete(beadsCraftLikes);
                    return "좋아요가 취소되었습니다.";
                })
                .orElseGet(() -> {
                    foundBeadsCraft.increaseLikeCount();
                    beadsCraftLikesJpaRepository.save(new BeadsCraftLikes(foundBeadsCraft, foundUser));
                    return "좋아요를 누르셨습니다.";
                });
    }

}
