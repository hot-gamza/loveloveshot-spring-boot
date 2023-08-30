package com.loveloveshot.image.command.application.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
public class FindResponse {
    private final String imageName;
    private final String imagePath;
    private final String taskId;
}
