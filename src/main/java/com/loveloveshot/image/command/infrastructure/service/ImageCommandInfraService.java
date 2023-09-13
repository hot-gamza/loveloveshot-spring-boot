package com.loveloveshot.image.command.infrastructure.service;

import com.loveloveshot.common.annotation.InfraService;
import com.loveloveshot.image.command.application.dto.ImageListRequestDTO;
import com.loveloveshot.image.command.application.dto.SingleImageRequestDTO;
import com.loveloveshot.image.command.application.dto.AIImageResponseDTO;
import com.loveloveshot.image.command.domain.service.ImageCommandDomainService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@InfraService
public class ImageCommandInfraService implements ImageCommandDomainService {

    private MultipartFile aiImage;

    @Override
    public AIImageResponseDTO getAISingleImage(SingleImageRequestDTO singleImageDTO) {

        System.out.println(singleImageDTO.getMaleSingleImage().getOriginalFilename());

        AIImageResponseDTO AIImageResponseDTO = new AIImageResponseDTO();

        AIImageResponseDTO.setAiImage(aiImage);

        return AIImageResponseDTO;
    }

//    @Override
//    public void getAIImageList(ImageListRequestDTO imageListDTO) {
//        System.out.println(imageListDTO.getMaleImageList().get(0).getOriginalFilename());
//    }
}
