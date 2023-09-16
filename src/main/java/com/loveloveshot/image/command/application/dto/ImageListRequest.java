package com.loveloveshot.image.command.application.dto;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ImageListRequest {

    private List<MultipartFile> maleImageList;
    private List<MultipartFile> femaleImageList;
}
