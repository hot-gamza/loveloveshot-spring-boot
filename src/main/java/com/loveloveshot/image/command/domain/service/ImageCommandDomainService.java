package com.loveloveshot.image.command.domain.service;

import com.loveloveshot.common.annotation.DomainService;
import com.loveloveshot.image.command.application.dto.ImageResponse;
import com.loveloveshot.image.command.application.dto.SingleImageRequest;

import java.io.IOException;

@DomainService
public interface ImageCommandDomainService {

    ImageResponse getAISingleImage(SingleImageRequest singleImageDTO) throws IOException;

//    void getAIImageList(ImageListRequestDTO imageListDTO);
}
