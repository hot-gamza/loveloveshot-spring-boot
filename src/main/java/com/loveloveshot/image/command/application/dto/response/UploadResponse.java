package com.loveloveshot.image.command.application.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
public class UploadResponse {
    private final String status;
    private final String taskId;
}
