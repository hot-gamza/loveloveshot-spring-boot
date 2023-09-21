package com.loveloveshot.image.command.domain.service;

import com.loveloveshot.common.annotation.DomainService;
import com.loveloveshot.image.command.application.dto.request.ImageRequest;
import com.loveloveshot.image.command.application.dto.response.UploadResponse;

@DomainService
public interface ImageCommandDomainService {

    UploadResponse uploadStandardImage(ImageRequest imageRequest);

    UploadResponse uploadPremiumImages(ImageRequest imageRequest);

//    void getAIImageList(ImageListRequestDTO imageListDTO);
}
