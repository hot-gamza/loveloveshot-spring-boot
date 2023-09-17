package com.loveloveshot.image.command.domain.service;

import com.loveloveshot.common.annotation.DomainService;
import com.loveloveshot.image.command.application.dto.AiImageResponse;
import com.loveloveshot.image.command.application.dto.SingleImageRequest;
import com.loveloveshot.image.command.application.dto.AIImageResponseDTO;
import com.loveloveshot.image.command.application.dto.ImageListRequestDTO;

import java.util.List;

@DomainService
public interface ImageCommandDomainService {

    AiImageResponse getAISingleImage(SingleImageRequest singleImageDTO);

    List<AIImageResponseDTO> getAIImageList(ImageListRequestDTO imageListDTO);

}
