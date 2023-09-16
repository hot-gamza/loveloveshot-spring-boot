package com.loveloveshot.image.command.domain.service;

import com.loveloveshot.common.annotation.DomainService;
import com.loveloveshot.image.command.application.dto.AiImageResponse;
import com.loveloveshot.image.command.application.dto.SingleImageRequest;

@DomainService
public interface ImageCommandDomainService {

    AiImageResponse getAISingleImage(SingleImageRequest singleImageDTO);

//    void getAIImageList(ImageListRequestDTO imageListDTO);
}
