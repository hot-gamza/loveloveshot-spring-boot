package com.loveloveshot.image.command.domain.service;

import com.loveloveshot.common.annotation.DomainService;
import com.loveloveshot.image.command.application.dto.ImageRequest;
import com.loveloveshot.image.command.application.dto.ImageResponse;

@DomainService
public interface ImageCommandDomainService {

    ImageResponse uploadStandardImage(ImageRequest imageRequest);

    ImageResponse uploadPremiumImages(ImageRequest imageRequest);

//    void getAIImageList(ImageListRequestDTO imageListDTO);
}
