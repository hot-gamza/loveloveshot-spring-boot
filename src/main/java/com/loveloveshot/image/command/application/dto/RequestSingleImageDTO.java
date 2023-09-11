package com.loveloveshot.image.command.application.dto;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RequestSingleImageDTO {

    private MultipartFile maleSingleImage;
    private MultipartFile femaleSingleImage;
}
