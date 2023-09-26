package com.loveloveshot.image.command.infrastructure.service;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.loveloveshot.common.annotation.InfraService;
import com.loveloveshot.image.command.application.dto.request.ImageRequest;
import com.loveloveshot.image.command.application.dto.response.UploadResponse;
import com.loveloveshot.image.command.domain.service.ImageCommandDomainService;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;

@InfraService
public class ImageCommandInfraService implements ImageCommandDomainService {
    private final String REQUEST_URL = "http://192.168.0.11"; // AI 이미지 생성 API URL
    private final WebClient WEBCLIENT = WebClient.builder()
            .baseUrl(REQUEST_URL)
            .build();

    // 일반 이미지 Ai 서버로 전달
    @Override
    public UploadResponse uploadStandardImage(ImageRequest imageRequest) {
        MultiValueMap<String, Resource> body = new LinkedMultiValueMap<>();
        body.add("male_files", imageRequest.getMaleImageResources().get(0));
        body.add("female_files", imageRequest.getFemaleImageResources().get(0));

        String response = WEBCLIENT.post()
                .uri("/main/standard") // baseUrl 이후 uri
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .bodyValue(body) // 요청 body
                .retrieve() // 디코딩
                .bodyToMono(String.class) // 0~1개의 결과 리턴
                .block(); // blocking
        System.out.println("response = " + response);
        JsonElement element = JsonParser.parseString(response);
        String status = element.getAsJsonObject().get("status").getAsString();
        String taskId = element.getAsJsonObject().get("task_id").getAsString();
        System.out.println("status = " + status);
        System.out.println("taskId = " + taskId);

        return new UploadResponse(status, taskId, 0);
    }

    // 프리미엄 이미지 Ai 서버로 전달
    @Override
    public UploadResponse uploadPremiumImages(ImageRequest imageRequest) {
        System.out.println("imageRequest = " + imageRequest);
        MultiValueMap<String, Resource> body = new LinkedMultiValueMap<>();
        body.put("male_files", imageRequest.getMaleImageResources());
        body.put("female_files", imageRequest.getFemaleImageResources());
        System.out.println("body = " + body);

        String response = WEBCLIENT.post()
                .uri("/main/premium") // baseUrl 이후 uri
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .bodyValue(body) // 요청 body
                .retrieve() // 디코딩
                .bodyToMono(String.class) // 0~1개의 결과 리턴
                .block(); // blocking
        System.out.println("response = " + response);
        JsonElement element = JsonParser.parseString(response);
        String status = element.getAsJsonObject().get("status").getAsString();
        String taskId = element.getAsJsonObject().get("task_id").getAsString();
        System.out.println("status = " + status);
        System.out.println("taskId = " + taskId);


        return new UploadResponse(status, taskId, 0);
    }

//    @Override
//    public void getAIImageList(ImageListRequestDTO imageListDTO) {
//        System.out.println(imageListDTO.getMaleImageList().get(0).getOriginalFilename());
//    }

}
