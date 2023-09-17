package com.loveloveshot.image.command.application.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

@Getter
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
public class AiImageResponse implements Serializable {

    private final MultipartFile aiImage;

}
