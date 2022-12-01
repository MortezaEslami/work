package com.zerodefects.usermanager;

import org.keycloak.TokenVerifier;
import org.keycloak.common.VerificationException;
import org.keycloak.representations.AccessToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/users")
public class UserController {

    @Value("${keycloak.realm}")
    private String realm;

    @Value("${keycloak.auth-server-url}")
    private String serviceUrl;

    @Value("${keycloak.resource}")
    private String clientId;

    @Value("${keycloak.credentials.secret}")
    private String secret;

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);


    @PostMapping(value = "/login", produces = "application/json")
    public ResponseEntity<?> login(@RequestBody LoginDTO loginDTO) {
        String url = serviceUrl + "/realms/" + realm + "/protocol/openid-connect/token";

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> mapForm = new LinkedMultiValueMap<>();
        mapForm.add("grant_type", "password");
        mapForm.add("client_id", clientId);
        mapForm.add("username", loginDTO.getUsername());
        mapForm.add("password", loginDTO.getPassword());
        mapForm.add("client_secret", secret);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(mapForm, headers);

        try {
            ResponseEntity<Object> response = restTemplate.exchange(url, HttpMethod.POST, request, Object.class);
            if (response.getStatusCode() == HttpStatus.OK) {
                LinkedHashMap<String, Object> map = (LinkedHashMap<String, Object>) response.getBody();
                TokenInfo tokenInfo = null;
                if (map != null) {
                    AccessToken token = TokenVerifier.create(map.get("access_token").toString(), AccessToken.class).getToken();
                    List<String> roles = new ArrayList<>(token.getRealmAccess().getRoles());
                    tokenInfo = new TokenInfo(map.get("access_token").toString(), map.get("refresh_token").toString(), roles);
                }
                return ResponseEntity.ok(tokenInfo);
            }
        } catch (HttpClientErrorException ex) {
            LOGGER.error(" HttpClientError exception is happened!{}",  ex.getMessage());
            return new ResponseEntity<>("User name or password is not correct", HttpStatus.BAD_REQUEST);
        } catch (VerificationException ex) {
            LOGGER.error(" Verification exception is happened! {}", ex.getMessage());
        }
        return new ResponseEntity<>("Something bad happened. Please come back a few minutes later when we fixed that problem", HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
