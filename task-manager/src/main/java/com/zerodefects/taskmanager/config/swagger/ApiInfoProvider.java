package com.zerodefects.taskmanager.config.swagger;

import io.swagger.v3.oas.models.info.Info;

public class ApiInfoProvider {

    private final SwaggerProperties properties;

    public ApiInfoProvider(SwaggerProperties properties) {
        this.properties = properties;
    }

    public Info provide() {
        return new Info()
                .title(properties.getProjectTitle())
                .description(properties.getProjectDescription())
                .version(properties.getProjectVersion());
    }
}
