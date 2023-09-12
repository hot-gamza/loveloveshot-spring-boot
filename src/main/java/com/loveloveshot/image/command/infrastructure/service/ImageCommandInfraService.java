package com.loveloveshot.image.command.infrastructure.service;

import com.loveloveshot.image.command.application.dto.RequestImageListDTO;
import com.loveloveshot.image.command.application.dto.RequestSingleImageDTO;
import com.loveloveshot.image.command.domain.service.ImageCommandDomainService;
import org.springframework.stereotype.Service;

@Service
public class ImageCommandInfraService implements ImageCommandDomainService {

    @Override
    public void getAISingleImage(RequestSingleImageDTO singleImageDTO) {
//        System.out.println(singleImageDTO.getMaleSingleImage().getOriginalFilename());
    }

    @Override
    public void getAIImageList(RequestImageListDTO imageListDTO) {
//        System.out.println(imageListDTO.getMaleImageList().get(0).getOriginalFilename());
    }
}
