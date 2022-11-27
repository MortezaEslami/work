package com.zerodefects.usermanager;

import org.keycloak.TokenVerifier;
import org.keycloak.common.VerificationException;
import org.keycloak.representations.AccessToken;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/users")
public class UserController {

    @PostMapping(value = "/login", produces = "application/json")
    public ResponseEntity<?> login(@RequestBody LoginDTO loginDTO) {
        String url = "http://localhost:8180/realms/work/protocol/openid-connect/token";

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> mapForm = new LinkedMultiValueMap<>();
        mapForm.add("grant_type", "password");
        mapForm.add("client_id", "app-work");
        mapForm.add("username", loginDTO.getUsername());
        mapForm.add("password", loginDTO.getPassword());
        mapForm.add("client_secret", "secret");

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(mapForm, headers);
        ResponseEntity<Object> response = restTemplate.exchange(url, HttpMethod.POST, request, Object.class);
        LinkedHashMap<String, Object> map = (LinkedHashMap<String, Object>) response.getBody();
        TokenInfo tokenInfo = null;
        if (map != null) {
            try {
                AccessToken token = TokenVerifier.create(map.get("access_token").toString(), AccessToken.class).getToken();
                List<String> roles = new ArrayList<>(token.getRealmAccess().getRoles());
                tokenInfo = new TokenInfo(map.get("access_token").toString(), map.get("refresh_token").toString(), roles);
            } catch (VerificationException e) {
                e.printStackTrace();
            }
        }
        return ResponseEntity.ok(tokenInfo);
    }

}
