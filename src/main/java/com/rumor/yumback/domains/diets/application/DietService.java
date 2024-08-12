package com.rumor.yumback.domains.diets.application;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rumor.yumback.domains.diets.domain.Diet;
import com.rumor.yumback.domains.diets.infrastructure.DietJpaRepository;
import com.rumor.yumback.domains.diets.infrastructure.GeminiClient;
import com.rumor.yumback.domains.diets.presentation.request.DietRegisterRequest;
import com.rumor.yumback.domains.files.application.FileSystemStorageService;
import com.rumor.yumback.domains.users.domain.User;
import com.rumor.yumback.domains.users.infrastructure.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.model.Media;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DietService {
    private final DietJpaRepository dietJpaRepository;
    private final UserJpaRepository userJpaRepository;
    private final FileSystemStorageService fileSystemStorageService;
    private final GeminiClient geminiClient;

    public List<Diet> diets(String username) {
        User foundUser = userJpaRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("not found user"));

        return dietJpaRepository.findAllByCreator(foundUser);
    }

    public Diet register(String username, DietRegisterRequest dietRegisterRequest, MultipartFile file) throws IOException {
        User foundUser = userJpaRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("not found user"));

        Path filePath = fileSystemStorageService.store(file, username);

        FoodDto foodDto = analyzingImages(filePath);
        return dietJpaRepository.save(new Diet(foundUser, foodDto.name(), file.getOriginalFilename(), foodDto.calorie()));
    }

    private FoodDto analyzingImages(Path filePath) throws IOException {
        String message = "사진의 음식명과 칼로리를 대략 계산해서 음식명은 name으로 칼로리는 calorie로 json 형태로 반환해줘 (마크다운 코드 제외하고 문자열만)";

        UrlResource urlResource = new UrlResource(filePath.toUri());
        String mimeType = Files.probeContentType(filePath);
        Media media = new Media(MimeTypeUtils.parseMimeType(mimeType), urlResource);

        ChatResponse chat = geminiClient.chat(message, List.of(media));
        String content = chat.getResult().getOutput().getContent();

        String cleanedTest = content.replace("```json\n", "").replace("\n```", "");


        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(cleanedTest, FoodDto.class);
    }
}
