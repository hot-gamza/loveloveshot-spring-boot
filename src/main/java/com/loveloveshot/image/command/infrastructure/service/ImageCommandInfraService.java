package com.loveloveshot.image.command.infrastructure.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.loveloveshot.common.annotation.InfraService;
import com.loveloveshot.image.command.application.dto.AIImageResponseDTO;
import com.loveloveshot.image.command.application.dto.SingleImageRequestDTO;
import com.loveloveshot.image.command.domain.service.ImageCommandDomainService;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.*;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.io.InputStream;

@InfraService
public class ImageCommandInfraService implements ImageCommandDomainService {
    private static final RestTemplate REST_TEMPLATE;

    static {
        // RestTemplate 기본 설정을 위한 Factory 생성
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(3000);
        factory.setReadTimeout(3000);
        factory.setBufferRequestBody(false); // 파일 전송은 이 설정을 꼭 해주자.
        REST_TEMPLATE = new RestTemplate(factory);
    }

    @Override
    public AIImageResponseDTO getAISingleImage(SingleImageRequestDTO singleImageDTO) {
        String reqURL = "http://192.168.0.198:5001/"; // AI 단독 이미지 생성 API URL
        LinkedMultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
        JsonNode response;
        HttpStatus httpStatus = HttpStatus.CREATED;

        try {
            map.add("files",
                    new MultipartInputStreamFileResource(singleImageDTO.getMaleSingleImage().getInputStream()
                            , singleImageDTO.getMaleSingleImage().getOriginalFilename()));
            // 최근 Spring 버전을 쓴다면 map.add("files", file.getResource()); 로 변경

            map.add("files",
                    new MultipartInputStreamFileResource(singleImageDTO.getFemaleSingleImage().getInputStream()
                            , singleImageDTO.getFemaleSingleImage().getOriginalFilename()));

            //            URL url = new URL(reqURL);
//            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//            conn.setRequestMethod("POST");
////            conn.setRequestProperty("Content-Type", "application/json; charset=utf-8");
//            conn.setRequestProperty("Content-Type", "multipart/form-data");
//            conn.setDoOutput(true);
//
//            Map<String, Object> requestMap = new HashMap<String, Object>();
//            System.out.println(singleImageDTO.getMaleSingleImage());
//            System.out.println(singleImageDTO.getFemaleSingleImage());
//
//            requestMap.put("male", singleImageDTO.getMaleSingleImage());
//            requestMap.put("female", singleImageDTO.getFemaleSingleImage());
//
////            String requestBody = getJsonStringFromMap(requestMap);
//            System.out.println("requestBody:" + requestBody);
//
//            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
//            bw.write(requestBody);
//            bw.flush();
//            bw.close();
//
//            System.out.println("getResponseCode():" + conn.getResponseCode());
//            System.out.println("getResponseMessage():" + conn.getResponseMessage());
//
//            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
//
//            StringBuilder result = new StringBuilder();
//            String line = "";
//
//            while ((line = br.readLine()) != null) {
//                result.append(line);
//            }
//            System.out.println("response body : " + result); // 응답
//
//            // Gson 라이브러리로 JSON 파싱
//            JsonElement element = JsonParser.parseString(result.toString());
//
//            MultipartFile aiImage = (MultipartFile) element;
//
//            return new AIImageResponseDTO(aiImage);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);

            HttpEntity<LinkedMultiValueMap<String, Object>> requestEntity
                    = new HttpEntity<>(map, headers);
            response = REST_TEMPLATE.postForObject(reqURL, requestEntity, JsonNode.class);

            System.out.println(response);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return new ResponseEntity<>(response, httpStatus);
    }

//    /**
//     * Map을 jsonString으로 변환
//     */
//    @SuppressWarnings("unchecked")
//    public static String getJsonStringFromMap(Map<String, Object> map) {
//
//        JSONObject json = new JSONObject();
//
//        for (Map.Entry<String, Object> entry : map.entrySet()) {
//
//            String key = entry.getKey();
//            Object value = entry.getValue();
//
//            json.put(key, value);
//        }
//
//        return json.toJSONString();
//    }

    class MultipartInputStreamFileResource extends InputStreamResource {

        private final String filename;

        MultipartInputStreamFileResource(InputStream inputStream, String filename) {
            super(inputStream);
            this.filename = filename;
        }

        @Override
        public String getFilename() {
            return this.filename;
        }

        @Override
        public long contentLength() throws IOException {
            return -1; // we do not want to generally read the whole stream into memory ...
        }
    }

//    @Override
//    public void getAIImageList(ImageListRequestDTO imageListDTO) {
//        System.out.println(imageListDTO.getMaleImageList().get(0).getOriginalFilename());
//    }

}
