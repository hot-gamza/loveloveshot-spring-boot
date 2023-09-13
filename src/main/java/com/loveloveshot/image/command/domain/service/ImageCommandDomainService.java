package com.loveloveshot.image.command.domain.service;

import com.loveloveshot.image.command.application.dto.ImageListRequestDTO;
import com.loveloveshot.image.command.application.dto.SingleImageRequestDTO;
import com.loveloveshot.image.command.application.dto.AIImageResponseDTO;
import org.springframework.stereotype.Service;

@Service
public interface ImageCommandDomainService {

    AIImageResponseDTO getAISingleImage(SingleImageRequestDTO singleImageDTO);

    void getAIImageList(ImageListRequestDTO imageListDTO);
}
