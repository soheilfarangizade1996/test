package com.example.demo;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.SSLContext;
import java.security.NoSuchAlgorithmException;

@RestController
@RequestMapping(value = "/session")
public class SessionController {

/*
    private final RestTemplate restTemplate;

    public SessionController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }*/

    @GetMapping
    public ResponseEntity<?> createSession() throws Exception {


        System.setProperty("javax.net.ssl.trustStore", null);
        SSLContext context = SSLContext.getInstance("TLSv1.2");
        context.init(null, null, null);

        CloseableHttpClient httpClient = HttpClientBuilder.create().setSSLContext(context)
                .build();
        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory(httpClient);
        RestTemplate restTemplate = new RestTemplate(factory);


        String url = "https://lab.shooka.ir/openvidu/api/sessions";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBasicAuth("OPENVIDUAPP", "MY_SECRET");

        MultiValueMap<String, String> map= new LinkedMultiValueMap<>();
        map.add("customSessionId", "-12");

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);

        ResponseEntity<?> response = restTemplate.postForEntity(url, request, Object.class);

        System.out.println();
        return response;
    }
}
