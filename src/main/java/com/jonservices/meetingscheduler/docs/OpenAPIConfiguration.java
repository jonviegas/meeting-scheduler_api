package com.jonservices.meetingscheduler.docs;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;

@OpenAPIDefinition(info =
@Info(title = "Meeting Scheduler API",
        version = "v1",
        description = "This REST API provides a system to manage meetings, " +
                "being able to register a room by its date, start time, and end time. " +
                "It also updates meeting information after its creation and deletes it if desired."))
public class OpenAPIConfiguration {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .components(new Components())
                .info(new io.swagger.v3.oas.models.info.Info()
                        .title("Meeting Scheduler API")
                        .version("V1")
                        .license(new License().name("Apache 2.0").url("http://springdoc.org")));

    }

}
