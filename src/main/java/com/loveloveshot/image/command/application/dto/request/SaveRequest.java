package com.loveloveshot.image.command.application.dto.request;

import lombok.*;

import java.io.File;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SaveRequest {
    private List<File> aiImage;
    private String taskId;
    private Long userNo;
}
