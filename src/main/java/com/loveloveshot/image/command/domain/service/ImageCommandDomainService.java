package com.loveloveshot.image.command.domain.service;

import com.loveloveshot.image.command.application.dto.RequestImageListDTO;
import com.loveloveshot.image.command.application.dto.RequestSingleImageDTO;
import org.springframework.stereotype.Service;

@Service
public interface ImageCommandDomainService {

    void getAISingleImage(RequestSingleImageDTO singleImageDTO);

    void getAIImageList(RequestImageListDTO imageListDTO);
}
