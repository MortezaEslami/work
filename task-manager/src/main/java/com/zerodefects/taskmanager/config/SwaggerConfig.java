package com.zerodefects.taskmanager.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
 
@Configuration
@OpenAPIDefinition
public class SwaggerConfig {
 
    private static final String OAUTH_SCHEME_NAME = "oAuth";
    private static final String PROTOCOL_URL_FORMAT = "%s/realms/%s/protocol/openid-connect";
 
    @Bean
    public OpenAPI customOpenApi(KeycloakProperties keycloakProperties) {
        return new OpenAPI()
                .components(new Components() //1
                        .addSecuritySchemes(OAUTH_SCHEME_NAME, createOAuthScheme(keycloakProperties)))
                .addSecurityItem(new SecurityRequirement().addList(OAUTH_SCHEME_NAME));
    }
 
    private SecurityScheme createOAuthScheme(KeycloakProperties properties) {
        OAuthFlows flows = createOAuthFlows(properties); //3a
 
        return new SecurityScheme() //2
                .type(SecurityScheme.Type.OAUTH2)
                .flows(flows);
    }
 
    private OAuthFlows createOAuthFlows(KeycloakProperties properties) {
        OAuthFlow flow = createAuthorizationCodeFlow(properties);
 
        return new OAuthFlows()
                .authorizationCode(flow);
    }
 
    private OAuthFlow createAuthorizationCodeFlow(KeycloakProperties properties) {
        var protocolUrl = String.format(PROTOCOL_URL_FORMAT, properties.authServerUrl(), properties.realm());
 
        return new OAuthFlow()//3b
                .authorizationUrl(protocolUrl + "/auth")
                .tokenUrl(protocolUrl + "/token")
                .scopes(new Scopes().addString("openid", ""));
    }
}
