package com.loveloveshot.image.command.application.dto;

import lombok.*;

import java.io.File;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AiImageRequest {
    private File aiImage;
}
