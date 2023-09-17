package com.loveloveshot.image.command.domain.service;

import com.loveloveshot.common.annotation.DomainService;
import com.loveloveshot.image.command.application.dto.AiImageResponse;
import com.loveloveshot.image.command.application.dto.ImageListRequest;
import com.loveloveshot.image.command.application.dto.SingleImageRequest;

import java.util.List;

@DomainService
public interface ImageCommandDomainService {

    AiImageResponse getAiSingleImage(SingleImageRequest singleImage);

    List<AiImageResponse> getAiImageList(ImageListRequest imageList);

}
