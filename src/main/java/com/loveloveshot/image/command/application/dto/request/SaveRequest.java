package com.loveloveshot.image.command.application.dto.request;

import lombok.*;

import java.io.File;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SaveRequest {
    private File aiImage;
    private String taskId;
}
