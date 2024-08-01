package com.rumor.yumback.infrastructure;

import com.rumor.yumback.domains.diets.infrastructure.GeminiClient;
import com.rumor.yumback.utils.MarkDownUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.model.Media;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.UrlResource;
import org.springframework.util.MimeTypeUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@SpringBootTest
class GeminiClientTest {
    private static final Logger log = LoggerFactory.getLogger(GeminiClientTest.class);
    @Autowired
    private GeminiClient geminiClient;

    @Test
    void quickTest() {
        String response = geminiClient.chat("spring ai 란?");
        Assertions.assertNotNull(response);
    }

    @Test
    void image() throws IOException {
        String message = "사진의 음식명과 칼로리를 대략 계산해서 음식명은 name으로 칼로리는 calorie로 json 형태로 반환해줘";
        String imagePath = "https://t3.ftcdn.net/jpg/05/53/72/98/360_F_553729892_ZtQPM9zp3Fk7Vzz6p4SytPqPHLjoTFJ2.jpg";

        UrlResource urlResource = new UrlResource(imagePath);
        String mimeType = Files.probeContentType(Path.of(imagePath));
        Media media = new Media(MimeTypeUtils.parseMimeType(mimeType), urlResource);

        ChatResponse chat = geminiClient.chat(message, List.of(media));
        String content = chat.getResult().getOutput().getContent();

        log.info("text : {} ", MarkDownUtils.clean(content));
        Assertions.assertNotNull(chat);
    }
}