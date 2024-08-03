package com.rumor.yumback.domains.files.application;

import com.rumor.yumback.common.FilesProperties;
import com.rumor.yumback.common.errors.StorageException;
import com.rumor.yumback.common.errors.StorageFileNotFoundException;
import com.rumor.yumback.domains.users.infrastructure.UserJpaRepository;
import com.rumor.yumback.utils.ResourcesProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileSystemStorageService {
    private final FilesProperties filesProperties;
    private final UserJpaRepository userJpaRepository;
    private final ResourcesProperties resourcesProperties;

    public Path store(MultipartFile file) {
        if (file.isEmpty()) {
            throw new RuntimeException("Failed to store empty file.");
        }

        String fileExtension = com.google.common.io.Files.getFileExtension(Objects.requireNonNull(file.getOriginalFilename()));
        Path rootLocation = filesProperties.getRootLocation();
        Path destinationFile = rootLocation.resolve(UUID.randomUUID() + "." + fileExtension)
                .normalize()
                .toAbsolutePath();

        if (!destinationFile.getParent().equals(rootLocation.toAbsolutePath())) {
            throw new StorageException("Cannot store file outside current directory.");
        }

        try (InputStream inputStream = file.getInputStream()) {
            Files.copy(inputStream, destinationFile,
                    StandardCopyOption.REPLACE_EXISTING);

            return destinationFile;
        } catch (IOException e) {
            throw new StorageException("Failed to store file.", e);
        }
    }

    public Resource loadAsResource(String filename) throws MalformedURLException {
        try {
            Path file = this.load(filename);
            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() || resource.isReadable()) {
                return resource;
            }

            throw new StorageFileNotFoundException("Could not read file: " + filename);

        } catch (MalformedURLException e) {
            throw new StorageFileNotFoundException("Could not read file: " + filename, e);
        }
    }

    public Path load(String filename) {
        return filesProperties.getRootLocation().resolve(filename);
    }

    // properties 에서 받아서 요청주소 처리 필요
    public String loadAsUrl(Path savedFile) {
        return Paths.get(resourcesProperties.getUrl())
                .resolve("files/")
                .resolve(savedFile.getFileName().toString())
                .toString();

    }
}
