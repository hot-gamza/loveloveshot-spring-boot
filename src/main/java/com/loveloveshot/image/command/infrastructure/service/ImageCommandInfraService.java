package com.loveloveshot.image.command.infrastructure.service;

import com.loveloveshot.common.annotation.InfraService;
import com.loveloveshot.image.command.application.dto.AIImageResponseDTO;
import com.loveloveshot.image.command.application.dto.ImagesDTO;
import com.loveloveshot.image.command.application.dto.SingleImageRequestDTO;
import com.loveloveshot.image.command.domain.service.ImageCommandDomainService;
import org.json.simple.JSONObject;

import java.util.Map;

@InfraService
public class ImageCommandInfraService implements ImageCommandDomainService {
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
    public AIImageResponseDTO getAISingleImage(SingleImageRequestDTO singleImageDTO, ImagesDTO imagesDTO) {
        System.out.println("imagesDTO1 = " + imagesDTO.getMaleImage());
        System.out.println("imagesDTO2 = " + imagesDTO.getFemaleImage());

        String reqURL = "http://192.168.0.40:5001/"; // AI 단독 이미지 생성 API URL

        // WebClient 방식


//        HttpURLConnection 방식
//
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

        return null;
    }

//    @Override
//    public void getAIImageList(ImageListRequestDTO imageListDTO) {
//        System.out.println(imageListDTO.getMaleImageList().get(0).getOriginalFilename());
//    }

}
