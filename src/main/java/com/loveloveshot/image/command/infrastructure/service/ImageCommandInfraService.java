package com.loveloveshot.image.command.infrastructure.service;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.loveloveshot.common.annotation.InfraService;
import com.loveloveshot.image.command.application.dto.ImageResponse;
import com.loveloveshot.image.command.application.dto.SingleImageRequest;
import com.loveloveshot.image.command.domain.service.ImageCommandDomainService;
import org.springframework.http.MediaType;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.Base64;
import java.util.UUID;

@InfraService
public class ImageCommandInfraService implements ImageCommandDomainService {
    private final String REQUEST_URL = "http://192.168.0.36"; // AI 이미지 생성 API URL
    private final WebClient WEBCLIENT = WebClient.builder().baseUrl(REQUEST_URL).build();

    @Override
    public ImageResponse getAISingleImage(SingleImageRequest singleImageDTO) throws IOException {

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
        MultipartBodyBuilder formData = new MultipartBodyBuilder();
        formData.part("imgFile", singleImageDTO.getMaleSingleImage().getResource());
        formData.part("imgFile", singleImageDTO.getFemaleSingleImage().getResource());

        String response = WEBCLIENT.post()
                .uri("/main/standard") // baseUrl 이후 uri
                .accept(MediaType.MULTIPART_FORM_DATA)
                .contentType(MediaType.MULTIPART_FORM_DATA) // 보내는 자원의 형식(header에 담김)
                .body(BodyInserters.fromMultipartData(formData.build())) // 요청 body
                .retrieve() // ResponseEntity를 받아 디코딩
                .bodyToMono(String.class) // 0~1개의 결과 리턴
                .block(); // blocking

        JsonElement element = JsonParser.parseString(response);
        String fileData = element.getAsJsonObject().get("file_data").getAsString();
        String filePath = "/AiImages/" + UUID.randomUUID() + ".png"; // Ai 이미지 로컬 저장 경로

        // Base64 문자열을 바이트 배열로 변환
        byte[] imageBytes = Base64.getDecoder().decode(fileData);

        // 바이트 배열을 BufferedImage 객체로 변환
        try (ByteArrayInputStream bis = new ByteArrayInputStream(imageBytes)) {
            BufferedImage image = ImageIO.read(bis);

            // BufferedImage 객체를 이미지 파일로 저장
            ImageIO.write(image, "png", new File("src/main/webapp" + filePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ImageResponse(filePath);
    }

//    @Override
//    public void getAIImageList(ImageListRequestDTO imageListDTO) {
//        System.out.println(imageListDTO.getMaleImageList().get(0).getOriginalFilename());
//    }

}
