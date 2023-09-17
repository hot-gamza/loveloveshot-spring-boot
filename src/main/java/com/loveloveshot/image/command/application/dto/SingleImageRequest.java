package com.loveloveshot.image.command.application.dto;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SingleImageRequest {

    private MultipartFile maleSingleImage;
    private MultipartFile femaleSingleImage;
}
