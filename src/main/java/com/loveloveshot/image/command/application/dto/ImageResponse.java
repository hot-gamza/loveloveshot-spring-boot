package com.loveloveshot.image.command.application.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;

@Getter
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
public class ImageResponse implements Serializable {
    //    private final File aiImage;
    private final String aiImage;
}
