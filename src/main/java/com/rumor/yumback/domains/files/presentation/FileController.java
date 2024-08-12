package com.rumor.yumback.domains.files.presentation;

import com.rumor.yumback.domains.files.application.FileSystemStorageService;
import com.rumor.yumback.domains.oauth2.dto.CustomOauth2User;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.nio.file.Path;

@RestController
@RequiredArgsConstructor
@RequestMapping("/files")
public class FileController {
    private final FileSystemStorageService fileSystemStorageService;

    @GetMapping("/{folderName}/{filename:.+}")
    public ResponseEntity<Resource> serveFile(@PathVariable String filename, @PathVariable String folderName) throws MalformedURLException {
        Resource file = fileSystemStorageService.loadAsResource(filename, folderName);

        if (file == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }

    @PostMapping(consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<String> store(@AuthenticationPrincipal CustomOauth2User customOauth2User, @RequestPart MultipartFile file) throws URISyntaxException, IOException {
        Path savedFile = fileSystemStorageService.store(file, customOauth2User.getUsername());
        String uriString = fileSystemStorageService.loadAsUrl(savedFile, customOauth2User.getUsername());

        return ResponseEntity.ok().body(uriString);
    }
}
