package com.loveloveshot.image.command.application.service;

import com.loveloveshot.image.command.application.dto.request.ImageRequest;
import com.loveloveshot.image.command.application.dto.request.SaveRequest;
import com.loveloveshot.image.command.application.dto.response.UploadResponse;
import com.loveloveshot.image.command.domain.aggregate.entity.AiImage;
import com.loveloveshot.image.command.domain.repository.ImageCommandRepository;
import com.loveloveshot.image.command.domain.service.ImageCommandDomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
    public UploadResponse saveStandardImage(SaveRequest saveRequest) {
        AiImage aiImage = AiImage.builder()
                .imageName(saveRequest.getAiImage().get(0).getName())
                .imagePath("/standard/" + saveRequest.getAiImage().get(0).getName())
                .taskId(saveRequest.getTaskId())
                .grade("일반")
                .build();
        imageCommandRepository.save(aiImage);
        return new UploadResponse();
    }

    // 프리미엄 이미지 업로드
    public UploadResponse uploadPremiumImages(ImageRequest imageRequest) {
        return imageCommandDomainService.uploadPremiumImages(imageRequest);
    }

    // 프리미엄 AI 이미지 저장
    public UploadResponse savePremiumImage(SaveRequest saveRequest) {
        for (int i = 0; i < saveRequest.getAiImage().size(); i++) {
            AiImage aiImage = AiImage.builder()
                    .imageName(saveRequest.getAiImage().get(i).getName())
                    .imagePath("/premium/" + saveRequest.getAiImage().get(i).getName())
                    .taskId(saveRequest.getTaskId())
                    .grade("프리미엄")
                    .build();
            imageCommandRepository.save(aiImage);
        }
        return new UploadResponse();
    }

}
