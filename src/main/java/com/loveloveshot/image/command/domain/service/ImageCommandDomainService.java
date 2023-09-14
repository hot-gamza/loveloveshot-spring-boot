package com.loveloveshot.image.command.domain.service;

import com.loveloveshot.common.annotation.DomainService;
import com.loveloveshot.image.command.application.dto.AIImageResponseDTO;
import com.loveloveshot.image.command.application.dto.ImagesDTO;
import com.loveloveshot.image.command.application.dto.SingleImageRequestDTO;

@DomainService
public interface ImageCommandDomainService {

    AIImageResponseDTO getAISingleImage(SingleImageRequestDTO singleImageDTO, ImagesDTO imagesDTO);

//    void getAIImageList(ImageListRequestDTO imageListDTO);
}
