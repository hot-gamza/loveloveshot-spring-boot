package com.loveloveshot.image.command.application.service;

import com.loveloveshot.image.command.application.dto.RequestImageListDTO;
import com.loveloveshot.image.command.application.dto.RequestSingleImageDTO;
import com.loveloveshot.image.command.domain.service.ImageCommandDomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ImageCommandService {

    private final ImageCommandDomainService imageCommandDomainService;

    public void getSingleImage(RequestSingleImageDTO singleImageDTO) {

        imageCommandDomainService.createAISingleImage(singleImageDTO);
    }

    public void getImageList(RequestImageListDTO imageListDTO) {

        imageCommandDomainService.createAIImageList(imageListDTO);
    }
}
