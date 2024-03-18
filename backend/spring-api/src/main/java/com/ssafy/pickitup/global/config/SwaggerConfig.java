package com.ssafy.pickitup.global.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebMvc
public class SwaggerConfig {
    @Bean
    public OpenAPI openAPI() {
        Info info = new Info()
            .version("1.0.0")
            .title("API 명세서")
            .description("pick IT up dev.");
        Server server=new Server();
        server.setUrl("https://pickitup.online");
//        server.setUrl("http://localhost:8080");
        return new OpenAPI()
            .info(info)
            .addServersItem(server);
    }
}