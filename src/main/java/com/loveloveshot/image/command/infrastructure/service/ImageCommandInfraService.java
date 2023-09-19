package com.loveloveshot.image.command.infrastructure.service;

import com.loveloveshot.common.annotation.InfraService;
import com.loveloveshot.image.command.application.dto.ImageRequest;
import com.loveloveshot.image.command.application.dto.ImageResponse;
import com.loveloveshot.image.command.domain.service.ImageCommandDomainService;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;

@InfraService
public class ImageCommandInfraService implements ImageCommandDomainService {
    private final String REQUEST_URL = "http://192.168.0.198:5001"; // AI 이미지 생성 API URL
    private final WebClient WEBCLIENT = WebClient.builder()
            .baseUrl(REQUEST_URL)
            .build();
    private final int UPLOAD_COUNT = 0;

    @Override
    public ImageResponse uploadStandardImage(ImageRequest imageRequest) {
        Resource maleImages = imageRequest.getMaleImages().get(0);
        Resource femaleImages = imageRequest.getFemaleImages().get(0);
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("male_files", maleImages);
        body.add("female_files", femaleImages);

        String response = WEBCLIENT.post()
                .uri("/main/standard") // baseUrl 이후 uri
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .bodyValue(body) // 요청 body
                .retrieve() // 디코딩
                .bodyToMono(String.class) // 0~1개의 결과 리턴
                .block(); // blocking
//        // LinkedMultiValueMap에 List 담기
//        LinkedMultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
//        body.add("files", imageRequest.getMaleImages());
//        body.add("files", imageRequest.getFemaleImages());
//
//// HttpHeaders 설정
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
//
//// HttpEntity 생성
//        HttpEntity<LinkedMultiValueMap<String, Object>> httpEntity = new HttpEntity<>(body, headers);
//
//// WebClient로 POST 요청 보내기
//        byte[] response = WEBCLIENT.post()
//                .uri("/main/standard") // baseUrl 이후 uri
////                .contentType(MediaType.MULTIPART_FORM_DATA)
//                .bodyValue(BodyInserters.fromValue(httpEntity)) // 요청 body
//                .retrieve() // 디코딩
//                .bodyToMono(byte[].class) // 0~1개의 결과 리턴
//                .block(); // blocking

        System.out.println("response = " + response);
//        List<String> files = new ArrayList<>();
//        String filePath = "src/main/webapp/AiImages/" + UUID.randomUUID() + ".png"; // Ai 이미지 로컬 저장 경로
//        try {
//            // 바이트 배열을 파일로 저장
//            FileOutputStream fos = new FileOutputStream(filePath);
//            fos.write(response);
//            fos.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        files.add(0, filePath);
//        System.out.println("files = " + files);

        // 이미지 AI 서버에서 JSON으로 받는 법
//        JsonElement element = JsonParser.parseString(response);
//        String fileData = element.getAsJsonObject().get("file_data").getAsString();
//
//        // Base64 문자열을 바이트 배열로 변환
//        byte[] imageBytes = Base64.getDecoder().decode(fileData);
//
//        // 바이트 배열을 BufferedImage 객체로 변환
//        try (ByteArrayInputStream bis = new ByteArrayInputStream(imageBytes)) {
//            BufferedImage image = ImageIO.read(bis);
//
//            // BufferedImage 객체를 이미지 파일로 저장
//            ImageIO.write(image, "png", new File(filePath));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        return new ImageResponse(UPLOAD_COUNT + 1);
    }

    @Override
    public ImageResponse uploadPremiumImages(ImageRequest imageRequest) {
        return null;
    }

//    @Override
//    public void getAIImageList(ImageListRequestDTO imageListDTO) {
//        System.out.println(imageListDTO.getMaleImageList().get(0).getOriginalFilename());
//    }

}
