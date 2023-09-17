package com.loveloveshot.image.command.infrastructure.service;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.loveloveshot.common.annotation.InfraService;
import com.loveloveshot.image.command.application.dto.SingleImageRequest;
import com.loveloveshot.image.command.domain.service.ImageCommandDomainService;
import org.springframework.http.MediaType;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.Base64;

@InfraService
public class ImageCommandInfraService implements ImageCommandDomainService {
    final String REQUEST_URL = "http://192.168.0.241:5001"; // AI 이미지 생성 API URL

    @Override
    public File getAISingleImage(SingleImageRequest singleImageDTO) throws IOException {

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
        formData.part("imgFile", singleImageDTO.getMaleSingleImage().getResource());
        formData.part("imgFile", singleImageDTO.getFemaleSingleImage().getResource());

        MultiValueMap<String, MultipartFile> form = new LinkedMultiValueMap<>();
        form.add("maleImage", singleImageDTO.getMaleSingleImage());
        form.add("femaleImage", singleImageDTO.getFemaleSingleImage());

        String response = webClient.post()
                .uri("/main/standard") // baseUrl 이후 uri
                .accept(MediaType.MULTIPART_FORM_DATA)
                .contentType(MediaType.MULTIPART_FORM_DATA) // 보내는 자원의 형식(header에 담김)
//                .body(BodyInserters.fromMultipartData(form))
                .body(BodyInserters.fromMultipartData(formData.build())) // 요청 body
                .retrieve() // ResponseEntity를 받아 디코딩
                .bodyToMono(String.class).block(); // 0~1개의 결과 리턴
//        System.out.println("response = " + response);

        JsonElement element = JsonParser.parseString(response);
        String fileData = element.getAsJsonObject().get("file_data").getAsString();
//        System.out.println("fileData = " + fileData);

        // 이것은 예시로 사용한 Base64 인코딩된 이미지 문자열입니다.
        // 실제 애플리케이션에서는 서버 또는 다른 소스로부터 이 값을 받아옵니다.

        // Base64 문자열을 바이트 배열로 변환
        byte[] imageBytes = Base64.getDecoder().decode(fileData);

        // 바이트 배열을 BufferedImage 객체로 변환
        try (ByteArrayInputStream bis = new ByteArrayInputStream(imageBytes)) {
            BufferedImage image = ImageIO.read(bis);

            // BufferedImage 객체를 이미지 파일로 저장
            ImageIO.write(image, "png", new File("output.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("imageBytes = " + imageBytes);

//        MultipartFile multipartFile =
//        System.out.println("response.block() = " + response.block().getAiImage());
//        System.out.println("response.subscribe() = " + response.collectList().subscribe());
//        System.out.println("response = " + response.block().getAiImage());
//        System.out.println("response222 = " + response.block().getAiImage().get(0));
        return new File("C:\\Lecture\\01_java\\local-repo\\loveloveshot\\output.png");
    }

//    @Override
//    public void getAIImageList(ImageListRequestDTO imageListDTO) {
//        System.out.println(imageListDTO.getMaleImageList().get(0).getOriginalFilename());
//    }

}
