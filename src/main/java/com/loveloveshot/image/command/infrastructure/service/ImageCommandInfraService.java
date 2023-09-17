package com.loveloveshot.image.command.infrastructure.service;

import com.loveloveshot.common.annotation.InfraService;
import com.loveloveshot.image.command.application.dto.AiImageResponse;
import com.loveloveshot.image.command.application.dto.SingleImageRequest;
import com.loveloveshot.image.command.application.dto.AIImageResponseDTO;
import com.loveloveshot.image.command.application.dto.ImageListRequestDTO;
import com.loveloveshot.image.command.domain.service.ImageCommandDomainService;
import org.springframework.http.MediaType;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import java.util.List;

@InfraService
public class ImageCommandInfraService implements ImageCommandDomainService {
    final String REQUEST_URL = "http://192.168.0.40:5001"; // AI 이미지 생성 API URL

    @Override
    public AiImageResponse getAISingleImage(SingleImageRequest singleImageDTO) {

//      HttpURLConnection 방식
//        try {
//            // Set header
//            Map<String, String> headers = new HashMap<>();
//            HttpPostMultipart multipart = new HttpPostMultipart(reqURL, "utf-8", headers);
//
//            // Add form field
////            multipart.addFormField("username", "test_name");
////            multipart.addFormField("password", "test_psw");
//
//            // Add file
//            multipart.addFilePart("imgFile", new File(imagesDTO.getMaleImage()));
//            multipart.addFilePart("imgFile", new File(imagesDTO.getFemaleImage()));
//
//            // Print result
//            String response = multipart.finish();
//            System.out.println(response);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        // WebClient 방식
        WebClient webClient = WebClient.builder().baseUrl(REQUEST_URL).build();

        MultipartBodyBuilder formData = new MultipartBodyBuilder();
        formData.part("imgFile", singleImageDTO.getMaleSingleImage());
        formData.part("imgFile", singleImageDTO.getFemaleSingleImage());

        AiImageResponse response = webClient.post()
                .uri("/") // baseUrl 이후 uri
                .contentType(MediaType.MULTIPART_FORM_DATA) // 보내는 자원의 형식(header에 담김)
                .body(BodyInserters.fromMultipartData(formData.build())) // 요청 body
                .retrieve() // ResponseEntity를 받아 디코딩
                .bodyToMono(AiImageResponse.class) // 0~1개의 결과 리턴
                .block(); // 응답 대기(blocking)
        System.out.println("response = " + response);
        return response;
    }

    @Override
    public List<AIImageResponseDTO> getAIImageList(ImageListRequestDTO imageListDTO) {

        System.out.println("imageListDTO = " + imageListDTO.getMaleImageList().get(0).getOriginalFilename());
        return null;
    }


}
