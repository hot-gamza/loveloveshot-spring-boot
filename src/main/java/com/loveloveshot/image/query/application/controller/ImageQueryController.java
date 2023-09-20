package com.loveloveshot.image.query.application.controller;

import com.loveloveshot.common.response.ApiResponse;
import com.loveloveshot.image.command.application.dto.request.FindRequest;
import com.loveloveshot.image.query.application.service.ImageQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://loveloveshot.com", "http://192.168.0.12:3000", "*"})
public class ImageQueryController {
    private final ImageQueryService imageQueryService;

    // 일반 이미지 조회
    @GetMapping("/findAiImage")
    public ResponseEntity<ApiResponse> getStandardAiImage(FindRequest findRequest) {
        return ResponseEntity.ok(ApiResponse.success("조회 성공"
                , imageQueryService.findStandardAiImage(findRequest)));
    }
}
