package com.loveloveshot.image.command.application.service;

import com.loveloveshot.image.command.application.dto.request.ImageRequest;
import com.loveloveshot.image.command.application.dto.request.SaveRequest;
import com.loveloveshot.image.command.application.dto.response.UploadResponse;
import com.loveloveshot.image.command.domain.aggregate.entity.AiImage;
import com.loveloveshot.image.command.domain.repository.ImageCommandRepository;
import com.loveloveshot.image.command.domain.service.ImageCommandDomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class ImageCommandService {

    private final ImageCommandDomainService imageCommandDomainService;
    private final ImageCommandRepository imageCommandRepository;

    // 일반 이미지 업로드
    public UploadResponse uploadStandardImage(ImageRequest imageRequest) {
        return imageCommandDomainService.uploadStandardImage(imageRequest);
    }

    // 일반 AI 이미지 저장
    public UploadResponse saveStandardImage(SaveRequest saveRequest) throws IOException {
        AiImage aiImage = AiImage.builder()
                .imageName(saveRequest.getAiImage().getName())
                .imagePath(saveRequest.getAiImage().getPath())
                .taskId(saveRequest.getTaskId())
                .build();
        imageCommandRepository.save(aiImage);
        return new UploadResponse();
    }

    public UploadResponse uploadPremiumImages(ImageRequest imageRequest) {

        return imageCommandDomainService.uploadPremiumImages(imageRequest);
    }

}
