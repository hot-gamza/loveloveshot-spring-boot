package com.loveloveshot.image.command.infrastructure.service;

import com.loveloveshot.common.annotation.InfraService;
import com.loveloveshot.image.command.application.dto.AIImageResponseDTO;
import com.loveloveshot.image.command.application.dto.ImagesDTO;
import com.loveloveshot.image.command.application.dto.SingleImageRequestDTO;
import com.loveloveshot.image.command.domain.service.ImageCommandDomainService;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

@InfraService
@RequiredArgsConstructor
public class ImageCommandInfraService implements ImageCommandDomainService {

    private final WebClient.Builder webClientBuilder;

    /**
     * Map을 jsonString으로 변환
     */
    @SuppressWarnings("unchecked")
    public static String getJsonStringFromMap(Map<String, Object> map) {

        JSONObject json = new JSONObject();

        for (Map.Entry<String, Object> entry : map.entrySet()) {

            String key = entry.getKey();
            Object value = entry.getValue();

            json.put(key, value);
        }

        return json.toJSONString();
    }

    @Override
    public AIImageResponseDTO getAISingleImage(SingleImageRequestDTO singleImageDTO ) {

        String reqURL = "http://192.168.0.40:5001/"; // AI 단독 이미지 생성 API URL

        final WebClient webClient = webClientBuilder.baseUrl(reqURL).build();

        MultipartBodyBuilder formData = new MultipartBodyBuilder();
        formData.part("imgFile", singleImageDTO.getMaleSingleImage());
        formData.part("imgFile", singleImageDTO.getFemaleSingleImage());

        Mono<AIImageResponseDTO> response = webClient.post()
                .uri("/")
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .body(BodyInserters.fromMultipartData(formData.build()))
                .retrieve()
                .bodyToMono(AIImageResponseDTO.class);
        System.out.println("response = " + response);
        return response.block();
    }

//        try {
//            // Set header
//            Map<String, String> headers = new HashMap<>();
//            HttpPostMultipart multipart = new HttpPostMultipart(reqURL, "utf-8", headers);
//            // Add form field
////            multipart.addFormField("username", "test_name");
////            multipart.addFormField("password", "test_psw");
//            // Add file
//            multipart.addFilePart("imgFile", new File(imagesDTO.getMaleImage()));
//            multipart.addFilePart("imgFile", new File(imagesDTO.getFemaleImage()));
//            // Print result
//            String response = multipart.finish();
//            System.out.println(response);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }

//    @Override
//    public void getAIImageList(ImageListRequestDTO imageListDTO) {
//        System.out.println(imageListDTO.getMaleImageList().get(0).getOriginalFilename());
//    }

}
