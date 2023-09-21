package com.loveloveshot.image.query.application.service;

import com.loveloveshot.image.command.application.dto.request.FindRequest;
import com.loveloveshot.image.command.application.dto.response.FindResponse;
import com.loveloveshot.image.command.domain.aggregate.entity.AiImage;
import com.loveloveshot.image.query.domain.repository.ImageQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ImageQueryService {
    private final ImageQueryRepository imageQueryRepository;

    // 일반 Ai 이미지 조회
    public FindResponse findStandardAiImage(FindRequest findRequest) {
        AiImage aiImage = imageQueryRepository.findAiImageByTaskId(findRequest.getTaskId());
        return new FindResponse(aiImage.getImageName()
                , aiImage.getImagePath()
                , aiImage.getTaskId());
    }
}
