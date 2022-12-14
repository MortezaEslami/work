package com.zerodefects.taskmanager.config.swagger;

import com.zerodefects.taskmanager.config.KeycloakProperties;
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
    private static final String TOKEN_URL_FORMAT = "%s/realms/%s/protocol/openid-connect/token";

    @Bean
    OpenAPI customOpenApi(KeycloakProperties keycloakProperties, ApiInfoProvider infoProvider) {
        return new OpenAPI()
                .info(infoProvider.provide())
                .components(new Components()
                        .addSecuritySchemes(OAUTH_SCHEME_NAME, createOAuthScheme(keycloakProperties)))
                .addSecurityItem(new SecurityRequirement().addList(OAUTH_SCHEME_NAME));
    }

    private SecurityScheme createOAuthScheme(KeycloakProperties properties) {
        OAuthFlows flows = createOAuthFlows(properties);

        return new SecurityScheme()
                .type(SecurityScheme.Type.OAUTH2)
                .flows(flows);
    }

    private OAuthFlows createOAuthFlows(KeycloakProperties properties) {
        OAuthFlow passwordFlow = createPasswordFlow(properties);

        return new OAuthFlows()
                .password(passwordFlow);
    }

    private OAuthFlow createPasswordFlow(KeycloakProperties properties) {
        var tokenUrl = String.format(TOKEN_URL_FORMAT, properties.getAuthServerUrl(), properties.getRealm());

        return new OAuthFlow()
                .tokenUrl(tokenUrl)
                .scopes(new Scopes());
    }

}
