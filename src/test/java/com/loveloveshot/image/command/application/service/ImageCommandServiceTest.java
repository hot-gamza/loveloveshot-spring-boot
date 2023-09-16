package com.loveloveshot.image.command.application.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class ImageCommandServiceTest {
    private ImageCommandService imageCommandService;

    @Test
    @DisplayName("AI 이미지 생성 테스트 : 1장")
    void createAiSingleImageTest() {
        
    }
}
